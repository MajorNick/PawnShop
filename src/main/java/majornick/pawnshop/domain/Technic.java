package majornick.pawnshop.domain;

import lombok.Getter;
import lombok.Setter;
import majornick.pawnshop.domain.enums.TechnicType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "tech_id")
public class Technic extends Item {

    @Column(nullable = false)
    private String brand;

    @Enumerated()
    private TechnicType techType;

    private String defects;

    private boolean license;
}
