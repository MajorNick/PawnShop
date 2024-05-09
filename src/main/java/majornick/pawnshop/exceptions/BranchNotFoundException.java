package majornick.pawnshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "branch not found")
public class BranchNotFoundException extends EntityNotFoundException {
    public BranchNotFoundException(String message) {
        super(message);
    }
}
