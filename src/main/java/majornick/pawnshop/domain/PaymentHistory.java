package majornick.pawnshop.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "payment_seq", sequenceName = "payment_seq", allocationSize = 1)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq")
    private Long id;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(name = "amount")
    private double amount;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}