package com.arif.demo.kafka;

import com.arif.demo.model.kafka.enums.Topics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic transactionTopic() {
        return new NewTopic(Topics.TRANSACTION_EVENT.name(), 1, (short) 3);
    }
}
