package majornick.pawnshop.dto;

import majornick.pawnshop.domain.Branch;

public class BranchDTO {
    private final Branch branch;

    public BranchDTO(Branch branch) {
        this.branch = branch;
    }

    public BranchDTO() {
        this(new Branch());
    }

    public long getId() {
        return branch.getId();
    }

    public String getAddress() {
        return branch.getAddress();
    }

    public void setAddress(String address) {
        branch.setAddress(address);
    }

    public Branch toBranch() {
        return branch;
    }

    public static BranchDTO toDto(Branch branch) {
        return new BranchDTO(branch);
    }
}
