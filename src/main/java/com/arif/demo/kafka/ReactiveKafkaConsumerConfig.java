package com.arif.demo.kafka;

import com.arif.demo.model.kafka.TransactionEvent;
import com.arif.demo.model.kafka.enums.Topics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.List;

@Configuration
public class ReactiveKafkaConsumerConfig {

    @Bean
    public ReactiveKafkaConsumerTemplate<String, TransactionEvent> reactiveKafkaTransactionTemplate(KafkaAdmin kafkaAdmin) {
        ReceiverOptions<String, TransactionEvent> basicReceiverOptions = ReceiverOptions.create(kafkaAdmin.getConfigurationProperties());
        basicReceiverOptions = basicReceiverOptions.subscription(List.of(Topics.TRANSACTION_EVENT.name()));
        return new ReactiveKafkaConsumerTemplate<>(basicReceiverOptions);
    }
}
