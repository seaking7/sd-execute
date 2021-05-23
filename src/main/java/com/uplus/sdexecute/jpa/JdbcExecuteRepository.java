package com.uplus.sdexecute.jpa;

import com.uplus.sdexecute.dto.ExecuteDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

public class JdbcExecuteRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcExecuteRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Optional<ExecuteDto> findByContentId(String contentId) {
        List<ExecuteDto> result
                = jdbcTemplate.query("select content_id, content_name, url from contents where content_id = ?",
                executeDtoRowMapper(),
                contentId);
        return result.stream().findAny();
    }

    private RowMapper<ExecuteDto> executeDtoRowMapper() {
        return (rs, rowNum) -> {
            ExecuteDto executeDto = new ExecuteDto();
            executeDto.setContentId(rs.getString("content_id"));
            executeDto.setContentName(rs.getString("content_name"));
            executeDto.setUrl(rs.getString("url"));

            return executeDto;
        };
    }

}
