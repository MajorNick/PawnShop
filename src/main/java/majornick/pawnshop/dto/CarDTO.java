package majornick.pawnshop.dto;

import majornick.pawnshop.domain.Car;

public class CarDTO extends ItemDTO {
    private final Car car;

    public CarDTO(Car car) {
        super(car);
        this.car = car;
    }

    public CarDTO() {
        this(new Car());
    }

    public String getBrand() {
        return car.getBrand();
    }

    public void setBrand(String s) {
        car.setBrand(s);
    }

    public long getManufactureYear() {
        return car.getManufactureYear();
    }

    public void setManufactureYear(long y) {
        car.setManufactureYear(y);
    }

    public void setMileage(long m) {
        car.setMileage(m);
    }

    public long getMileage() {
        return car.getMileage();
    }

    public Car toCar() {
        Car car = (Car) super.toItem();
        car.setBrand(getBrand());
        car.setManufactureYear(getManufactureYear());
        car.setMileage(getMileage());
        return car;
    }

    public static CarDTO giveCarDTO(Car car) {
        return new CarDTO(car);
    }
}
