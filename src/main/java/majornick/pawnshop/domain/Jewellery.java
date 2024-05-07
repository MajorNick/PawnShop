package majornick.pawnshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "jw_id")

public class Jewellery extends Item {

    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "jewellery_to_component",
            joinColumns = @JoinColumn(name = "jewellery_id"),
            inverseJoinColumns = @JoinColumn(name = "jewellery_component_id"))
    private List<JewelleryComponent> components;
}
