package majornick.pawnshop.domain;


import lombok.Getter;
import lombok.Setter;
import majornick.pawnshop.domain.enums.TechnicType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "tech_id")
public class Technic extends Item {

    @Column(name = "brand",nullable = false)
    private String brand;


    @Enumerated(EnumType.STRING)
    @Column(name = "tech_type")
    private TechnicType techType;

    @Column(name = "defects")
    private String defects;

    @Column(name = "license")
    private boolean license;
}
