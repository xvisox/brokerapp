package pl.mimuw.history.kafka;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import pl.mimuw.history.TransactionRepository;
import pl.mimuw.history.models.Transaction;
import pl.mimuw.history.payload.TransactionDto;
import pl.mimuw.history.payload.TransactionDtoMapper;

@Service
@RequiredArgsConstructor
public class KafkaHistoryConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaHistoryConsumer.class);

    private final TransactionRepository transactionRepo;

    @KafkaListener(topics = "transactionsHistory", groupId = "historyConsumers")
    public void consumeTransactionData(TransactionDto transactionDto) {
        LOGGER.info(String.format("#### -> Consumed transaction data -> %s", transactionDto));
        Transaction transaction = TransactionDtoMapper.mapTransactionDtoToTransaction(transactionDto);
        transactionRepo.save(transaction);
    }
}
