package majornick.pawnshop.service;

import majornick.pawnshop.domain.Branch;
import majornick.pawnshop.domain.Customer;
import majornick.pawnshop.domain.Item;
import majornick.pawnshop.domain.PaymentHistory;
import majornick.pawnshop.domain.enums.Status;
import majornick.pawnshop.repository.ItemRepo;
import majornick.pawnshop.repository.PaymentHistoryRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private ItemRepo itemRepo;
    @Mock
    private PaymentHistoryRepo paymentHistoryRepo;

    @InjectMocks
    @Spy
    private CustomerService customerService;


    @Test
    void dailyCheckout_makeActiveTransactionBefore28() {
        Customer customer = Customer.builder().fullName("tester").build();
        Branch branch = Branch.builder().address("tbilisi").build();
        LocalDate yesterday = LocalDate.of(2023, 11, 5);
        Item item = Item.builder()
                .balance(160)
                .monthlyFee(150)
                .remainingFee(1000)
                .pawnDate(yesterday)
                .status(Status.ACTIVE)
                .branch(branch)
                .customer(customer)
                .build();

        List<Item> items = List.of(item);
        when(customerService.getCurrentDate()).thenReturn(yesterday.plusDays(1));
        when(itemRepo.findAllByStatusAndDayBefore28(Status.ACTIVE, yesterday)).thenReturn(items);
        when(paymentHistoryRepo.save(any())).thenReturn(PaymentHistory.builder().build());
        paymentHistoryRepo.save(new PaymentHistory());
        customerService.dailyCheckout();


        Assertions.assertEquals(item.getBalance(), 0);
        Assertions.assertEquals(item.getRemainingFee(), 1000 - 160);
    }

    @Test
    void dailyCheckout_MakeTransactionsForActiveAt28() {
        Customer customer = Customer.builder().fullName("tester").build();
        Branch branch = Branch.builder().address("tbilisi").build();
        Item item = Item.builder()
                .balance(140)
                .monthlyFee(150)
                .remainingFee(1000)
                .pawnDate(LocalDate.of(2023, 11, 29))
                .status(Status.ACTIVE)
                .branch(branch)
                .customer(customer)
                .build();

        when(customerService.getCurrentDate())
                .thenReturn(LocalDate.of(2023, 11, 29))
                .thenReturn(LocalDate.of(2023, 3, 1))
                .thenReturn(LocalDate.of(2023, 3, 1));

        List<Item> items = List.of(item);
        when(itemRepo.findAllByStatusAndDayAfter28(Status.ACTIVE)).thenReturn(items);

        customerService.dailyCheckout();

        Assertions.assertEquals(item.getBalance(), 140);
        Assertions.assertEquals(item.getStatus(), Status.CONFISCATED);

        //check that after 28 february it will make checkout
        item.setStatus(Status.ACTIVE);
        customerService.dailyCheckout();
        Assertions.assertEquals(item.getBalance(), 140);
        Assertions.assertEquals(item.getStatus(), Status.CONFISCATED);

        item.setStatus(Status.ACTIVE);
        item.setPawnDate(LocalDate.of(2022, 1, 31));
        customerService.dailyCheckout();
        Assertions.assertEquals(item.getBalance(), 140);
        Assertions.assertEquals(item.getStatus(), Status.CONFISCATED);
    }

    @Test
    void dailyCheckout_MakeTransactionsAndReturn() {
        Customer customer = Customer.builder().fullName("tester").build();
        Branch branch = Branch.builder().address("tbilisi").build();
        Item item = Item.builder()
                .balance(100)
                .monthlyFee(100)
                .remainingFee(200)
                .pawnDate(LocalDate.of(2023, 11, 29))
                .status(Status.ACTIVE)
                .branch(branch)
                .customer(customer)
                .build();

        List<Item> items = List.of(item);

        when(customerService.getCurrentDate()).thenReturn(LocalDate.of(2023, 11, 29));
        when(itemRepo.findAllByStatusAndDayAfter28(Status.ACTIVE)).thenReturn(items);
        customerService.dailyCheckout();
        Assertions.assertEquals(0, item.getBalance());
        Assertions.assertEquals(Status.ACTIVE, item.getStatus());
        Assertions.assertEquals(100, item.getRemainingFee());

        item.setBalance(100);

        when(customerService.getCurrentDate()).thenReturn(LocalDate.of(2023, 12, 29));
        when(itemRepo.findAllByStatusAndDayAfter28(Status.ACTIVE)).thenReturn(items);
        customerService.dailyCheckout();
        Assertions.assertEquals(0, item.getBalance());
        Assertions.assertEquals(Status.RETURNED, item.getStatus());
        Assertions.assertEquals(0, item.getRemainingFee());
    }


}
