package com.jpmc.midascore;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
class TaskTwoTests {
    static final Logger logger = LoggerFactory.getLogger(TaskTwoTests.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private FileLoader fileLoader;

    @Test
    @Timeout(value = 120, unit = TimeUnit.SECONDS) // 2 minute timeout for manual debugging
    void task_two_verifier() throws InterruptedException {
        // Load and send test transactions
        String[] transactionLines = fileLoader.loadStrings("/test_data/poiuytrewq.uiop");
        for (String transactionLine : transactionLines) {
            kafkaProducer.send(transactionLine);
            logger.info("Sent transaction: {}", transactionLine);
        }

        // Wait for messages to be processed
        Thread.sleep(2000);

        logger.info("----------------------------------------------------------");
        logger.info("TEST SETUP COMPLETE");
        logger.info("----------------------------------------------------------");
        logger.info("Embedded Kafka is running on localhost:9092");
        logger.info("Sent {} transactions to 'transaction' topic", transactionLines.length);
        logger.info("");
        logger.info("DEBUGGER INSTRUCTIONS:");
        logger.info("1. Set breakpoints in TransactionListener.receive() or KafkaHandler.handleTransaction()");
        logger.info("2. Use debug mode to watch incoming transactions");
        logger.info("3. Test will auto-timeout after 2 minutes");
        logger.info("----------------------------------------------------------");

        // Keep test alive for debugging, but with a reasonable timeout
        Thread.sleep(118000); // 118 seconds (total 2 minutes with initial 2-second sleep)

        logger.info("Test timeout reached - stopping test");
    }
}