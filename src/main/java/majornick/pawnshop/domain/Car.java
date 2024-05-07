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

    @Column(name = "brand",nullable = false)
    private String brand;

    @Column(name = "manufacturer_year",nullable = false)
    private long manufactureYear;
    @Column(name = "mileage")
    private long mileage;
}
