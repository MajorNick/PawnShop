package majornick.pawnshop.service;

import lombok.RequiredArgsConstructor;
import majornick.pawnshop.domain.Customer;
import majornick.pawnshop.domain.Item;
import majornick.pawnshop.domain.PaymentHistory;
import majornick.pawnshop.domain.enums.Status;
import majornick.pawnshop.dto.CustomerDTO;
import majornick.pawnshop.dto.CustomerPawnedItemDTO;
import majornick.pawnshop.dto.ItemDTO;
import majornick.pawnshop.exceptions.CustomerNotFoundException;
import majornick.pawnshop.exceptions.ItemNotFoundException;
import majornick.pawnshop.repository.CustomerRepo;
import majornick.pawnshop.repository.ItemRepo;
import majornick.pawnshop.repository.PaymentHistoryRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final ItemRepo itemRepo;
    private final PaymentHistoryRepo paymentHistoryRepo;

    public Customer getCustomer(long id) {
        return customerRepo
                .findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("customer with id: %d not exists",id)));
    }

    public List<CustomerDTO> getAllCustomer() {
        return customerRepo
                .findAll()
                .stream()
                .map(CustomerDTO::new)
                .collect(Collectors.toList());
    }

    public CustomerPawnedItemDTO getCustomerById(long id) {
        Customer customer = getCustomer(id);
        return CustomerPawnedItemDTO.builder()
                .fullName(customer.getFullName())
                .pawnedItems(customer.getPawnItemHistory().stream()
                        .map(ItemDTO::new)
                        .collect(Collectors.toList()))
                .build();

    }

    @Transactional
    public CustomerPawnedItemDTO getCustomerPawnedItemsByStatus(long id, Status status) {
        Customer customer = getCustomer(id);
        return CustomerPawnedItemDTO.builder()
                .fullName(customer.getFullName())
                .pawnedItems(customer
                        .getPawnItemHistory()
                        .stream()
                        .filter(item -> item.getStatus().equals(status))
                        .map(ItemDTO::new)
                        .collect(Collectors.toList())
                ).build();
    }


    @Transactional
    public CustomerDTO postCustomer(CustomerDTO customerDTO) {
        return new CustomerDTO(customerRepo.save(customerDTO.toCustomer()));
    }

    @Scheduled(cron = "1 0 * * * *")
    @Transactional
    public void dailyCheckout() {
        LocalDate yesterday = getCurrentDate().minusDays(1);
        List<Item> items;
        if (yesterday.getDayOfMonth() < 28) {
            items = itemRepo.findAllByStatusAndDayBefore28(Status.ACTIVE, yesterday);
        } else {
            if (yesterday.getDayOfMonth() == 28) {
                items = itemRepo.findAllByStatusAndDayAfter28(Status.ACTIVE);
            } else {
                return;
            }
        }
        makeTransactions(items);
    }
    @Transactional
    public void makeTransactions(List<Item> items) {

        items.forEach(item -> {

            if (item.getMonthlyFee() > item.getBalance()) {
                item.setStatus(Status.CONFISCATED);
            } else {
                var paymentBuilder = PaymentHistory.builder()
                        .paymentDate(LocalDate.now())
                        .customer(item.getCustomer())
                        .item(item);
                if (item.getBalance() >= item.getRemainingFee()) {
                    paymentBuilder.amount(item.getRemainingFee());
                    item.setBalance(item.getBalance() - item.getRemainingFee());
                    item.setRemainingFee(0);

                    item.setStatus(Status.RETURNED);
                } else {
                    paymentBuilder.amount(item.getBalance());
                    item.setRemainingFee(item.getRemainingFee() - item.getBalance());
                    item.setBalance(0);
                }

                paymentHistoryRepo.save(paymentBuilder.build());
            }
        });
    }

    @Transactional
    public ItemDTO addFundsOnItem(long itemId, double amount) {
        Item item = itemRepo
                .findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException( String.format("item with id: %d not exists",itemId)));
        item.setBalance(item.getBalance() + amount);
        return new ItemDTO(itemRepo.save(item));
    }

    //wrapping local date in majornick.pawnshop.service, for testing
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
