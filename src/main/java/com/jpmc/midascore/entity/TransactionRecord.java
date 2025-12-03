package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import org.apache.catalina.User;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    private Double amount;

    private Long timestamp;

    public TransactionRecord() {}

    public TransactionRecord(UserRecord sender, UserRecord recipient, Double amount, Long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }
    
}
