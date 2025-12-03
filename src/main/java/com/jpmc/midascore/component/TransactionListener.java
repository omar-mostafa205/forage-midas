package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final KafkaHandler kafkaHandler;

    public TransactionListener(KafkaHandler kafkaHandler) {
        this.kafkaHandler = kafkaHandler;
    }

    @KafkaListener(topics = "${general.kafka-topic}")
    public void receive(Transaction transaction) {
        kafkaHandler.handleTransaction(transaction);
    }
}
