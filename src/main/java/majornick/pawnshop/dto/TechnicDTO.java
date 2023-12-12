package majornick.pawnshop.dto;

import majornick.pawnshop.domain.Technic;
import majornick.pawnshop.domain.enums.TechnicType;

public class TechnicDTO extends ItemDTO {
    private final Technic technic;

    public TechnicDTO(Technic technic) {
        super(technic);
        this.technic = technic;
    }

    public TechnicDTO() {
        this(new Technic());
    }

    public String getBrand() {
        return technic.getBrand();
    }

    public TechnicType getTechType() {
        return technic.getTechType();
    }

    public String getDefects() {
        return technic.getDefects();
    }

    public boolean isLicense() {
        return technic.isLicense();
    }

    public void setBrand(String brand) {
        technic.setBrand(brand);
    }

    public void setTechType(TechnicType type) {
        technic.setTechType(type);
    }

    public void setDefects(String defects) {
        technic.setDefects(defects);
    }

    public void setLicense(boolean license) {
        technic.setLicense(license);
    }

    public Technic toTechnic() {
        Technic technic = (Technic) super.toItem();
        technic.setBrand(getBrand());
        technic.setTechType(getTechType());
        technic.setDefects(getDefects());
        technic.setLicense(isLicense());
        return technic;
    }

    public static TechnicDTO giveTechnicDTO(Technic tech) {
        return new TechnicDTO(tech);
    }
}
