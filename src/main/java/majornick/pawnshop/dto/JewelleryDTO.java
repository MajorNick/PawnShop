package majornick.pawnshop.dto;

import majornick.pawnshop.domain.Jewellery;
import majornick.pawnshop.domain.JewelleryComponent;

import java.util.List;


public class JewelleryDTO extends ItemDTO {
    private final Jewellery jewellery;

    public JewelleryDTO(Jewellery jew) {
        super(jew);
        this.jewellery = jew;
    }

    public JewelleryDTO() {
        this(new Jewellery());
    }

    public Jewellery toJewellery() {
        Jewellery supered = (Jewellery) super.toItem();
        supered.setDescription(getDescription());
        return supered;
    }

    public static JewelleryDTO giveJewelleryDTO(Jewellery jew) {
        return new JewelleryDTO(jew);
    }

    public String getDescription() {
        return jewellery.getDescription();
    }

    public void setDescription(String description) {
        jewellery.setDescription(description);
    }
    public void setComponents(List<JewelleryComponent> comp){
        jewellery.setComponents(comp);
    }
    public List<JewelleryComponent> getComponents() {
        return jewellery.getComponents();
    }
}
