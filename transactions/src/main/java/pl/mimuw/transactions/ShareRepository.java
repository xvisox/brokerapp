package pl.mimuw.transactions;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mimuw.transactions.models.Share;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {

    Share findByShareholderNameAndTicker(String shareholderName, String ticker);

    List<Share> findByShareholderName(String shareholderName);
}
