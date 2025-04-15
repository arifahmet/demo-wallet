package com.arif.demo.kafka;

import com.arif.demo.model.kafka.TransactionEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class ReactiveKafkaProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, TransactionEvent> transactionProducerTemplate(KafkaAdmin kafkaAdmin) {
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(kafkaAdmin.getConfigurationProperties()));
    }
}
