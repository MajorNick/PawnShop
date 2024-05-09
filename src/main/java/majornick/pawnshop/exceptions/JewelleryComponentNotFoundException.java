package majornick.pawnshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Jewellery component not found")
public class JewelleryComponentNotFoundException extends EntityNotFoundException {
    public JewelleryComponentNotFoundException(String message) {
        super(message);
    }
}
