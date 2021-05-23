package com.uplus.sdexecute;

import com.uplus.sdexecute.jpa.JdbcExecuteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcExecuteRepository getJdbcExecuteRepository(){
        return new JdbcExecuteRepository(dataSource);
    }

}
