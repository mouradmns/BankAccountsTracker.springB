package org.sid.banking_prj.Web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.banking_prj.Dtos.CustomerDTO;
import org.sid.banking_prj.Entities.Customer;
import org.sid.banking_prj.Services.BankAccountService;
import org.sid.banking_prj.exceptions.CustomerNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

        private BankAccountService bankAccountService;

        @GetMapping("/customers")
        public List<CustomerDTO> customers() {
            return bankAccountService.listCustomers();
        }

        @GetMapping("/customers/{id}")
        public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {

                return bankAccountService.getCustomer(customerId);
        }

        @PostMapping("/customers")
        public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {

                bankAccountService.saveCustomer(customerDTO);

                return customerDTO;
        }


        @PutMapping ("/customers/{customerId}")
        public CustomerDTO updateCustomer(@PathVariable(name="customerId") Long customerId,@RequestBody  CustomerDTO customerDTO) throws CustomerNotFoundException {

                bankAccountService.updateCustomer(customerDTO);

                return customerDTO;
        }


        @DeleteMapping("/customers/{customerId}")
        public void deleteCustomer(@PathVariable(name = "customerId") Long id){

                bankAccountService.deleteCustomer(id);
        }



}

