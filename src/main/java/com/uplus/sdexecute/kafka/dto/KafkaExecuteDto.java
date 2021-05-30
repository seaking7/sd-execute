package com.uplus.sdexecute.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaExecuteDto implements Serializable {
    private Schema schema;
    private Payload payload;
}
