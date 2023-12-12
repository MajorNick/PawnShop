package majornick.pawnshop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerPawnedItemDTO {
    private final String fullName;
    private final List<ItemDTO> pawnedItems;
}
