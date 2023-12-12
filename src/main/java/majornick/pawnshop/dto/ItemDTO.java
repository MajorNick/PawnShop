package majornick.pawnshop.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import majornick.pawnshop.domain.Item;
import majornick.pawnshop.domain.enums.Status;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = CarDTO.class, name = "car"),
        @JsonSubTypes.Type(value = JewelleryDTO.class, name = "jewellery"),
        @JsonSubTypes.Type(value = TechnicDTO.class, name = "technic")
})
// aq ubralod sachevenblad entity rogori sheidzleba yofiliyo
@Schema(example = "[ " +
        "{ \"type\": \"car\", \"pawnDate\": \"2023-01-01\", \"monthlyFee\": 100.0, " +
        "\"remainingFee\": 1200.0, \"status\": \"ACTIVE\", " +
        "\"brand\": \"Toyota\", \"manufactureYear\": 2022, \"mileage\": 5000 }, " +
        "{ \"type\": \"jewellery\", \"pawnDate\": \"2023-02-01\", \"monthlyFee\": 80.0, " +
        "\"remainingFee\": 960.0, \"status\": \"ACTIVE\", " +
        "\"description\": \"Diamond Ring\" }, " +
        "{ \"type\": \"technic\", \"pawnDate\": \"2023-03-01\", \"monthlyFee\": 120.0, " +
        "\"remainingFee\": 1440.0, \"status\": \"ACTIVE\", " +
        "\"brand\": \"Apple\",\"techType\": \"FINE\",  \"defects\": \"None\", \"license\": true }" +
        "]")
public class ItemDTO {
    private final Item item;






    public ItemDTO(Item item) {
        this.item = item;
    }

    public ItemDTO() {
        this(new Item());
    }

    public Item toItem() {
        return item;
    }

    public Status getStatus() {
        return item.getStatus();
    }
    public double getBalance(){
        return item.getBalance();
    }

    public void setStatus(Status s) {
        item.setStatus(s);
    }

    public double getMonthlyFee() {
        return item.getMonthlyFee();
    }

    public void setMonthlyFee(double d) {
        item.setMonthlyFee(d);
    }

    public void setPawnDate(LocalDate d) {
        item.setPawnDate(d);
    }
    public double getRemainingFee(){
        return item.getRemainingFee();
    }
    public void setRemainingFee(double s){
        item.setRemainingFee(s);
    }
    public long getId() {
        return item.getId();
    }
    public void setId(long id) {
       item.setId(id);
    }

}
