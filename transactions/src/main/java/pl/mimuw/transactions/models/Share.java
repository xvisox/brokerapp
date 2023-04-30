package pl.mimuw.transactions.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Share {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long shareholderId;
    private String ticker;
    private Integer amount;
}
