package majornick.pawnshop.domain;


import lombok.*;
import majornick.pawnshop.domain.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@SequenceGenerator(name = "item_seq", sequenceName = "item_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_seq")
    private long id;

    @Column(name = "pawn_date")
    private LocalDate pawnDate;

    @Column(name = "monthly_fee")
    private double monthlyFee;

    @Column(name = "balance")
    private double balance;

    @Column(name = "remaining_fee")
    private double remainingFee;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "item")
    private List<PaymentHistory> paymentHistories;
}
