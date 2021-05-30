package com.uplus.sdexecute.kafka.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Payload {
    private String content_id;
    private String content_name;
    private String url;
    private String watching_time;
}
