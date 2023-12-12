package majornick.pawnshop.dto;

import majornick.pawnshop.domain.Customer;

public class CustomerDTO {
    private final Customer customer;

    public CustomerDTO(Customer customer) {
        this.customer = customer;
    }

    public CustomerDTO() {
        this(new Customer());
    }

    public String getFullName() {
        return customer.getFullName();
    }

    public void setFullName(String fn) {
        customer.setFullName(fn);
    }

    public long getId() {
        return customer.getId();
    }

    public Customer toCustomer() {
        return customer;
    }

}
