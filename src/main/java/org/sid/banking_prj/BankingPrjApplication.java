package org.sid.banking_prj;

import org.sid.banking_prj.Dtos.CustomerDTO;
import org.sid.banking_prj.Entities.*;
import org.sid.banking_prj.Enums.AccountStatus;
import org.sid.banking_prj.Enums.OperationType;
import org.sid.banking_prj.Repositories.AccountOperationRepository;
import org.sid.banking_prj.Repositories.BankAccountRepository;
import org.sid.banking_prj.Repositories.CustomerRepository;
import org.sid.banking_prj.Services.BankAccountService;
import org.sid.banking_prj.exceptions.BalanceNotSufficientException;
import org.sid.banking_prj.exceptions.BankAccountNotFoundException;
import org.sid.banking_prj.exceptions.CustomerNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class BankingPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingPrjApplication.class, args);
    }


//    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {

                Stream.of("hasan","mourad","adnane").forEach(name -> {

                    CustomerDTO customerDTO =new CustomerDTO();
                    customerDTO.setName(name);
                    customerDTO.setEmail(name + "@gmail.com");
                    bankAccountService.saveCustomer(customerDTO);
                });

                bankAccountService.listCustomers().forEach(customer -> {

                    try {
                        bankAccountService.saveCurrentBankAccount(Math.random() * 1000000000, 9000,
                                            customer.getId());

                        bankAccountService.saveSavingBankAccount(Math.random() * 1000000000,5.5,
                                customer.getId());


                        List<BankAccount> bankAccounts =bankAccountService.bankAccountList();

                                for(BankAccount bankAccount:bankAccounts){

                                    for (int i = 0; i < 10; i++) {
                                        bankAccountService.credit(bankAccount.getId(),1000+Math.random() * 1000,"credit");
                                        bankAccountService.debit(bankAccount.getId(),1000+Math.random() * 1000,"debit");
                                    }
                                }




                    } catch (CustomerNotFoundException | BankAccountNotFoundException | BalanceNotSufficientException e) {
                        throw new RuntimeException(e);
                    }
                });
        };
    }










//    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository) {
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                    Customer customer = new Customer();
                    customer.setName(name);
                    customer.setEmail(name + "@gmail.com");
                    customerRepository.save(customer);
            } );

            customerRepository.findAll().forEach(cust->{

                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setCustomer(cust);
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);



                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setCustomer(cust);
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });

            bankAccountRepository.findAll().forEach(acc->{
                for(int i=0;i<10;i++){
                    AccountOperation accountOperation = new AccountOperation();

                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(Math.random()*10000);
                    accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
                    accountOperation.setBankAccount(acc);
                    accountOperationRepository.save(accountOperation);
                }
            });

        };
    }
}

