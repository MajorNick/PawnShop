package majornick.pawnshop.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "branch_seq", sequenceName = "branch_seq", allocationSize = 1)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_seq")
    private long id;


    @Column(name = "address",nullable = false)
    private String address;

    @OneToMany(mappedBy = "branch")
    private List<Item> itemList;
}
