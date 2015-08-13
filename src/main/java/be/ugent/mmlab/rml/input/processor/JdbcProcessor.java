package be.ugent.mmlab.rml.input.processor;

import be.ugent.mmlab.rml.model.source.std.StdJdbcSource;
import java.sql.SQLException;
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
    private static final Logger log = LoggerFactory.getLogger(JdbcProcessor.class);
    
    public JdbcTemplate getJdbcConnection(StdJdbcSource mapSource) {   
        JdbcTemplate jdbcTemplate = null;
        log.info("mapSource " + mapSource);
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName(mapSource.getJdbcDriver());
            dataSource.setUrl(mapSource.getJdbcDSN());
            dataSource.setUsername(mapSource.getUsername());
            dataSource.setPassword(mapSource.getPassword());
            log.debug("Data source was generated");
            dataSource.getConnection();
            log.debug("Data source was connected");
            jdbcTemplate = new JdbcTemplate(dataSource);
            log.debug("JDBC Template was generated");
            return jdbcTemplate;
        } catch (SQLException ex) {
            log.error("SQL Exception " + ex);
        }
        
        return jdbcTemplate;
    }
}
