package pl.mimuw.transactions.kafka;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import pl.mimuw.transactions.payload.TransactionDto;

@Service
@AllArgsConstructor
public class KafkaHistoryProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaHistoryProducer.class);

    private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

    public void sendTransactionData(TransactionDto transactionDto) {
        LOGGER.info(String.format("#### -> Producing transaction data -> %s", transactionDto));

        Message<TransactionDto> message = MessageBuilder
                .withPayload(transactionDto)
                .setHeader(KafkaHeaders.TOPIC, "transactionsHistory")
                .build();

        kafkaTemplate.send(message);
    }
}
