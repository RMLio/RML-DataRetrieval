package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.LogicalSource;
import be.ugent.mmlab.rml.model.Source;
import be.ugent.mmlab.rml.model.source.std.StdJdbcSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

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
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        log.debug("mysql drive " + mapSource.getJdbcDriver());
        dataSource.setDriverClassName(mapSource.getJdbcDriver());

        dataSource.setUrl(mapSource.getJdbcDSN());
        dataSource.setUsername(mapSource.getUsername());
        dataSource.setPassword(mapSource.getPassword());
        jdbcTemplate = new JdbcTemplate(dataSource);
        log.debug("JDBC Template was generated");
        return jdbcTemplate;
    }
    
    @Override
    public InputStream getInputStream(
            LogicalSource logicalSource, Map<String, String> parameters){
        Connection conn = null;
        InputStream input = null;
        JdbcTemplate jdcTemplate = null;
        Source source = logicalSource.getSource();
        jdcTemplate = getJdbcConnection((StdJdbcSource) source);
        log.debug("Getting the input stream...");
        try {
            String table = logicalSource.getTableName();
            //TODO: Make it decide between tables and sql queries
            String sql = "SELECT * FROM " + table + " ;";
            DataSource dataSource = jdcTemplate.getDataSource();
            conn = dataSource.getConnection();
            if (rs == null) {
                PreparedStatement ps =
                        conn.prepareStatement(sql, Statement.KEEP_CURRENT_RESULT);
                log.debug("Query is being prepared... " + sql);
                rs = ps.executeQuery();
                log.debug("Query should be executed..." );
            }
            log.debug("Query executed.");
            Map<String, String> resultsMap = new HashMap<String, String>();
            
            if (rs.next()) {
                log.debug("Result set is not empty.");
                for(int i=1; i<rs.getMetaData().getColumnCount();i++){
                    resultsMap.put(
                            rs.getMetaData().getColumnLabel(i), 
                            rs.getString(i));
                }
            }
            else{
                log.debug("Result set is empty.");
                rs.close();}
            //SOLUTION 1
            /*ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.print(resultsMap);
            writer.flush();
            out.flush();
            input = new ByteArrayInputStream(out.toByteArray());
            out.close();*/

            log.debug("Process returned input stream");
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

    public ResultSet getResultSet(LogicalSource logicalSource, Map<String, String> parameters){
        Connection conn = null;
        JdbcTemplate jdcTemplate = null;
        Source source = logicalSource.getSource();
        jdcTemplate = getJdbcConnection((StdJdbcSource) source);
        log.debug("Getting the result map...");
        try {
            String table = logicalSource.getTableName();
            //TODO: Make it decide between tables and sql queries
            String sql = "SELECT * FROM " + table + " ;";
            DataSource dataSource = jdcTemplate.getDataSource();
            conn = dataSource.getConnection();
            if (rs == null) {
                PreparedStatement ps =
                        conn.prepareStatement(sql, Statement.KEEP_CURRENT_RESULT);
                rs = ps.executeQuery();
            }

            if (!rs.next()) {
                log.debug("Result set is empty.");
            }
            else{
                log.debug("there are results");
                return rs;
            }

        } catch (SQLException ex) {
            log.error("SQLException " + ex);
        }
        return  rs;
    }
    
    @Override
    public boolean hasNextInputStream() {
        try {
            if (!rs.isClosed()) {
                return true;
            } 
            else{
                rs.close();
            }
        } catch (SQLException ex) {
            log.error("SQLException " + ex);
        }
        return false;
    }
}
