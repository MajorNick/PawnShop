package majornick.pawnshop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import majornick.pawnshop.dto.BranchDTO;
import majornick.pawnshop.dto.ItemDTO;
import majornick.pawnshop.service.BranchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController()
@RequestMapping("/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;


    @GetMapping
    public ResponseEntity<?> getAllBranch() {
        return ResponseEntity.ok(branchService.getAll());
    }

    @Operation(summary = "add branch to the database",
            responses = {
                    @ApiResponse(responseCode = "201", description = "branch added successfully"),
                    @ApiResponse(responseCode = "400", description = "not valid parameters")
            })
    @PostMapping
    public ResponseEntity<?> postBranch(@RequestBody @NotNull BranchDTO branchDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.postBranch(branchDTO));
    }

    @Operation(summary = "get branch",
            responses = {
                    @ApiResponse(responseCode = "200", description = "successfully fetched"),
                    @ApiResponse(responseCode = "404", description = "not valid id")
            })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable @Min(1) long id) {
        return ResponseEntity.ok(branchService.getById(id));
    }


    @Operation(summary = "add pawned Item ",
            responses = {
                    @ApiResponse(responseCode = "201", description = "pawned  item registered successfully"),
                    @ApiResponse(responseCode = "400", description = "not valid Parameters"),
                    @ApiResponse(responseCode = "404", description = "customer or branch not found")
            })

    @PostMapping("/{branchId}/customers/{customerId}")
    public ResponseEntity<?> pawnItem(@PathVariable @Min(1) Long branchId,
                                      @PathVariable @Min(1) Long customerId,
                                      @RequestBody ItemDTO itemDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(branchService.pawnItem(branchId, customerId, itemDTO));

    }


}
