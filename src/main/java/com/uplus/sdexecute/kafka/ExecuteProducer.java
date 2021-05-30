package com.uplus.sdexecute.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uplus.sdexecute.dto.ExecuteDto;
import com.uplus.sdexecute.kafka.dto.Field;
import com.uplus.sdexecute.kafka.dto.KafkaExecuteDto;
import com.uplus.sdexecute.kafka.dto.Payload;
import com.uplus.sdexecute.kafka.dto.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExecuteProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    List<Field> fields = Arrays.asList(
            new Field("string", true, "content_id"),
            new Field("string", true, "content_name"),
            new Field("string", true, "url"),
            new Field("string", true, "watching_time"));

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("executeLog")
            .build();


    @Autowired
    public ExecuteProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ExecuteDto send(String topic, ExecuteDto executeDto){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date time = new Date();
        Payload payload = Payload.builder()
                .content_id(executeDto.getContentId())
                .content_name(executeDto.getContentName())
                .url(executeDto.getUrl())
                .watching_time(format.format(time))
                .build();

        KafkaExecuteDto kafkaExecuteDto = new KafkaExecuteDto(schema, payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try{
            jsonInString = mapper.writeValueAsString(kafkaExecuteDto);

        } catch(JsonProcessingException ex){
            ex.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Content Producer sent data from the Content microservice:" + kafkaExecuteDto);
        return executeDto;
    }

}
