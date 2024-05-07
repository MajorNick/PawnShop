package majornick.pawnshop.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "customer_seq", sequenceName = "customer_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq")
    private long id;

    @Column(name = "full_name",nullable = false)
    private String fullName;



    @OneToMany(mappedBy = "customer")
    private List<Item> pawnItemHistory;

    @OneToMany(mappedBy = "customer")
    private List<PaymentHistory> paymentHistories;

}
