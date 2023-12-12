package majornick.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import majornick.pawnshop.domain.enums.Status;
import majornick.pawnshop.dto.CustomerDTO;
import majornick.pawnshop.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @Operation(summary = "get customer and every pawned Item ",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully fetched"),
                    @ApiResponse(responseCode = "404", description = "not valid id")
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable @Min(1) long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/{id}/{status}")
    public ResponseEntity<?> getPawnedItemsForCustomer(@PathVariable long id,
                                                       @PathVariable Status status) {
        return ResponseEntity.ok(customerService.getCustomerPawnedItemsByStatus(id, status));
    }

    @Operation(summary = "add funds on  item's balance",
            responses = {
                    @ApiResponse(responseCode = "201", description = "funds added successfully"),
                    @ApiResponse(responseCode = "400", description = "not valid parameters")
            })
    @PutMapping("/items/{itemId}")
    public ResponseEntity<?> addFundsOnItem(@PathVariable long itemId,
                                            @RequestParam("amount") @Min(1) double amount) {
        return ResponseEntity.ok(customerService.addFundsOnItem(itemId, amount));
    }

    @Operation(summary = "add customer to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "customer added successfully"),
                    @ApiResponse(responseCode = "400", description = "not valid parameters")
            })
    @PostMapping
    public ResponseEntity<?> postCustomer(@RequestBody @NotNull CustomerDTO customerDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customerService.postCustomer(customerDTO));
    }




}
