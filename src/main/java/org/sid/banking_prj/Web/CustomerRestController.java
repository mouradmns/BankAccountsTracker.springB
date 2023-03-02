package org.sid.banking_prj.Web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.banking_prj.Dtos.CustomerDTO;
import org.sid.banking_prj.Entities.Customer;
import org.sid.banking_prj.Services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
