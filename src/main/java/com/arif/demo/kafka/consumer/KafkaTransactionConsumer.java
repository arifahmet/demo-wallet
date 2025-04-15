package com.arif.demo.kafka.consumer;

import com.arif.demo.model.kafka.TransactionEvent;
import com.arif.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationNotAllowedException;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.receiver.ReceiverRecord;

import java.time.Duration;
import java.time.LocalDate;

@RequiredArgsConstructor
@Slf4j
@Component
public class KafkaTransactionConsumer {
    private final ReactiveKafkaConsumerTemplate<String, TransactionEvent> transactionTemplate;
    private final TransactionService transactionService;
    private static final String KAFKA_LOG_CHECK_MSG = "kafka log check failed!";
    private int subscribeCount = 0;
    private LocalDate subscribeCountDate = LocalDate.now();
    private boolean consumerSubscribed = false;

    public void init() {
        initConsumerTemplate(0L);
    }

    public void initConsumerTemplate(Long waitDuration) {
        Mono.delay(Duration.ofSeconds(waitDuration))
                .then(Mono.defer(() -> {
                    consumerSubscribed = true;
                    return Mono.empty();
                }))
                .then(transactionTemplate
                        .receive()
                        .groupBy(this::groupBy)
                        .flatMap(groupedFlux ->
                                groupedFlux.publishOn(Schedulers.parallel())
                                        .concatMap(receiverRecord -> Mono.just(receiverRecord)
                                                .flatMap(transactionService::consumeTransactionEvent)
                                                .then(Mono.defer(() -> {
                                                    receiverRecord.receiverOffset().acknowledge();
                                                    return Mono.empty();
                                                }))
                                                .onErrorResume(e -> {
                                                    log.error("KAFKA_CONSUMER_ERROR error happened! record: {}", receiverRecord, e);
                                                    if (KAFKA_LOG_CHECK_MSG.equals(e.getMessage())) {
                                                        return Mono.error(e);
                                                    }
                                                    if (e instanceof BeanCreationNotAllowedException) {
                                                        return Mono.empty();
                                                    }
                                                    receiverRecord.receiverOffset().acknowledge();
                                                    return Mono.empty();
                                                })))
                        .doFinally(signalType -> {
                            log.error("KAFKA_CONSUMER_ERROR kafka died! Signal Type: {}", signalType);
                            if (!subscribeCountDate.equals(LocalDate.now())) {
                                subscribeCount = 0;
                                subscribeCountDate = LocalDate.now();
                            }
                            subscribeCount++;
                            consumerSubscribed = false;
                            initConsumerTemplate(subscribeCount * 15L);
                        }).then()).subscribe();
    }

    public Integer groupBy(ReceiverRecord<String, TransactionEvent> receiverRecord) {
        return receiverRecord.receiverOffset().topicPartition().partition();
    }
}
