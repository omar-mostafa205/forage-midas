package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.stereotype.Component;

@Component
public class KafkaHandler {
    public  void handleTransaction(Transaction transaction) {
        System.out.println("Received transaction: " + transaction.getAmount());
    }
}
