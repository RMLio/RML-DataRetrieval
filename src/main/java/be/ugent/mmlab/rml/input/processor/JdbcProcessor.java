package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.LogicalSource;
import be.ugent.mmlab.rml.model.Source;
import be.ugent.mmlab.rml.model.source.std.StdJdbcSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * RML - Data Retrieval Handler : DbProcessor
 *
 * @author andimou
 */
public class JdbcProcessor extends AbstractInputProcessor{
    // Log
    private static final Logger log = 
            LoggerFactory.getLogger(
            JdbcProcessor.class.getSimpleName());
    private ResultSet rs = null;
    
    public JdbcTemplate getJdbcConnection(StdJdbcSource mapSource) {   
        JdbcTemplate jdbcTemplate = null;

        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(mapSource.getJdbcDriver());
            dataSource.setUrl(mapSource.getJdbcDSN());
            dataSource.setUsername(mapSource.getUsername());
            dataSource.setPassword(mapSource.getPassword());
            dataSource.getConnection();
            jdbcTemplate = new JdbcTemplate(dataSource);
            log.debug("JDBC Template was generated");
            return jdbcTemplate;
        } catch (SQLException ex) {
            log.error("SQL Exception " + ex);
        }
        
        return jdbcTemplate;
    }
    
    @Override
    public InputStream getInputStream(
            LogicalSource logicalSource, Map<String, String> parameters){
        InputStream input = null;
        Source source = logicalSource.getSource();
        JdbcTemplate jdcTemplate = 
                        getJdbcConnection((StdJdbcSource) source);
        try {
            Connection conn = jdcTemplate.getDataSource().getConnection();
            String table = logicalSource.getTableName();
            //TODO: Make it decide between tables and sql queries
            String sql = "SELECT * FROM " + table + " ;";
            if (rs == null) {
               PreparedStatement ps =
                        conn.prepareStatement(sql, Statement.KEEP_CURRENT_RESULT);
                rs = ps.executeQuery();
            }
            
            Map<String, String> resultsMap = new HashMap<String, String>();
            
            if (rs.next()) {
                for(int i=1; i<rs.getMetaData().getColumnCount();i++){
                    resultsMap.put(
                            rs.getMetaData().getColumnLabel(i), 
                            rs.getString(i));
                }
            }
            else
                rs.close();
            //SOLUTION 1
            /*ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.print(resultsMap);
            writer.flush();
            out.flush();
            input = new ByteArrayInputStream(out.toByteArray());
            out.close();*/
            
            //SOLUTION 2
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();
            ObjectOutputStream out2 = new ObjectOutputStream(out1);
            out2.writeObject(resultsMap);
            input = new ByteArrayInputStream(out1.toByteArray());
            out2.close();
            out1.close();
            
            conn.close();
            
        } catch (SQLException ex) {
            log.error("SQLException " + ex);
        } catch (IOException ex) {
            log.error("IOException " + ex);
        }        
        
        return input;
    }
    
    @Override
    public boolean hasNextInputStream() {
        try {
            if (!rs.isClosed()) {
                return true;
            } 
            else
                rs.close();
        } catch (SQLException ex) {
            log.error("SQLException " + ex);
        }
        return false;
    }
}
