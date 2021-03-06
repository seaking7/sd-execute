package com.uplus.sdexecute.service;

import com.uplus.sdexecute.dto.ExecuteDto;

public interface ExecuteService {

    ExecuteDto getExecuteByContentId(String contentId);

    Iterable<ExecuteDto> getContentsAll();

    void deleteByContentId(String contentId);

}
