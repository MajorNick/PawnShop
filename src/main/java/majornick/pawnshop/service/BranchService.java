package majornick.pawnshop.service;

import lombok.RequiredArgsConstructor;
import majornick.pawnshop.domain.*;
import majornick.pawnshop.dto.*;
import majornick.pawnshop.exceptions.BranchNotFoundException;
import majornick.pawnshop.exceptions.JewelleryComponentNotFoundException;
import majornick.pawnshop.repository.BranchRepo;
import majornick.pawnshop.repository.CustomerRepo;
import majornick.pawnshop.repository.ItemRepo;
import majornick.pawnshop.repository.JewelleryComponentRepo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BranchService {
    private final BranchRepo branchRepo;
    private final ItemRepo itemRepo;
    private final CustomerRepo customerRepo;
    private final JewelleryComponentRepo jewelleryComponentRepo;

    public List<BranchDTO> getAll() {
        return branchRepo.findAll().stream().map(BranchDTO::new).collect(Collectors.toList());
    }


    public BranchDTO getById(long id) {
        return new BranchDTO(branchRepo.findById(id).orElseThrow(() -> new BranchNotFoundException(String.format("branch with id: %d not found", id))));
    }


    public JewelleryComponent getJewById(long id) {
        return jewelleryComponentRepo.findById(id).orElseThrow(() -> new JewelleryComponentNotFoundException(String.format("Jewellery component with id: %d not found ", id)));
    }

    public BranchDTO postBranch(BranchDTO branchDTO) {
        return BranchDTO.toDto(branchRepo.save(branchDTO.toBranch()));
    }

    @Transactional
    public ItemDTO pawnItem(long branchId, long customerId, ItemDTO itemDTO) {
        Branch branch = branchRepo.findById(branchId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " branch  not exists"));

        Customer customer = customerRepo.findById(customerId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, " customer  not exists"));

        ItemDTO responseItemDTO;
        if (itemDTO instanceof CarDTO) {
            Car car = ((CarDTO) itemDTO).toCar();
            car.setBranch(branch);
            car.setCustomer(customer);
            responseItemDTO = CarDTO.giveCarDTO(itemRepo.save(car));
        } else {
            if (itemDTO instanceof JewelleryDTO) {

                Jewellery jewellery = ((JewelleryDTO) itemDTO).toJewellery();

                jewellery.setBranch(branch);
                jewellery.setCustomer(customer);
                responseItemDTO = JewelleryDTO.giveJewelleryDTO(itemRepo.save(jewellery));
            } else {
                if (itemDTO instanceof TechnicDTO) {
                    Technic technic = ((TechnicDTO) itemDTO).toTechnic();
                    technic.setBranch(branch);
                    technic.setCustomer(customer);
                    responseItemDTO = TechnicDTO.giveTechnicDTO(itemRepo.save(technic));
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong type of item");
                }
            }
        }
        return responseItemDTO;
    }


    @CacheEvict(value = {"branches", "jew_components"}, allEntries = true)
    @Scheduled(cron = "0 0 */8 * * *")
    public void evictBranchCache() {

    }

}
