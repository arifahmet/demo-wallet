package com.arif.demo.kafka;

import com.arif.demo.kafka.consumer.KafkaTransactionConsumer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationRunnerConfigKafka {

    private final KafkaTransactionConsumer kafkaConsumer;


    @Bean
    public ApplicationRunner kafkaTestApplicationRunner() {
        return arguments -> {
            log.info("kafkaApplicationRunner started");
            kafkaConsumer.init();
        };
    }

}
