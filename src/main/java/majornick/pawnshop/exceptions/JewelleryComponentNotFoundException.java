package majornick.pawnshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Jewellery component not found")
public class JewelleryComponentNotFoundException extends RuntimeException{
    public JewelleryComponentNotFoundException(String message) {
        super(message);
    }
}
