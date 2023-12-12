package majornick.pawnshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@SequenceGenerator(name = "jw_seq", sequenceName = "jw_seq", allocationSize = 1)
public class JewelleryComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jw_seq")
    private long id;

    private String component;
    @ManyToMany(mappedBy = "components")
    private List<Jewellery> jewellery;
}
