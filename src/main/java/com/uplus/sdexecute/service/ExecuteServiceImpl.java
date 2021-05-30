package com.uplus.sdexecute.service;

import com.uplus.sdexecute.dto.ExecuteDto;
import com.uplus.sdexecute.jpa.JdbcExecuteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecuteServiceImpl implements ExecuteService{


    JdbcExecuteRepository jdbcExecuteRepository;

    @Autowired
    public ExecuteServiceImpl(JdbcExecuteRepository jdbcExecuteRepository) {
        this.jdbcExecuteRepository = jdbcExecuteRepository;
    }

    @Override
    public ExecuteDto getExecuteByContentId(String contentId) {
        ExecuteDto executeDto = jdbcExecuteRepository.findByContentId(contentId).get();
        return executeDto;
    }

    @Override
    public Iterable<ExecuteDto> getContentsAll() {
        return jdbcExecuteRepository.findContentsAll();
    }

    @Override
    public void deleteByContentId(String contentId) {
        jdbcExecuteRepository.deleteByContentId(contentId);
    }
}
