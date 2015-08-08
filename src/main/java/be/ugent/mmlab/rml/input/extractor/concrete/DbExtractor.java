package be.ugent.mmlab.rml.input.extractor.concrete;

import be.ugent.mmlab.rml.model.InputSource;
import be.ugent.mmlab.rml.input.extractor.AbstractInputExtractor;
import java.util.Set;
import org.openrdf.model.Resource;
import org.openrdf.repository.Repository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.vendor.Database;

/**
 * RML - Data Retrieval Handler : DbExtractor
 *
 * @author andimou
 */
public class DbExtractor extends AbstractInputExtractor {
    
    private JdbcOperations jdbcOperations;

        @Override
        public Set<String> extractStringTemplate(String source) {
            
            this.jdbcOperations = new JdbcTemplate();
            
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
            Database db = (Database) context.getBean("db");
            return null;
    }
        
    @Override
    public Set<InputSource> extractInput(Repository repository, Resource resource) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/test_db");
        dataSource.setUsername("nata");
        dataSource.setPassword("nata");
        
        return null;
    }
    
}
