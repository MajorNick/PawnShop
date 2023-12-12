package majornick.pawnshop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "c_id")
public class Car extends Item {

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private long manufactureYear;
    private long mileage;
}
