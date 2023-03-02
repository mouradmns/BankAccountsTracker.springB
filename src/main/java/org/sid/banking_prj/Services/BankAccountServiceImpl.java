package org.sid.banking_prj.Services;

import lombok.AllArgsConstructor;
import org.sid.banking_prj.Entities.BankAccount;
import org.sid.banking_prj.Entities.Customer;
import org.sid.banking_prj.Repositories.AccountOperationRepository;
import org.sid.banking_prj.Repositories.BankAccountRepository;
import org.sid.banking_prj.Repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BankAccountServiceImpl implements BankAccountService{


        private CustomerRepository customerRepository;
        private BankAccountRepository bankAccountRepository;
        private AccountOperationRepository accountOperationRepository;


    @Override
    public Customer saveCustomer(Customer customer) {
        return null;
    }

    @Override
    public BankAccount saveBankAccount(double initialBalance, String type, long customerId) {
        return null;
    }

    @Override
    public List<Customer> listCustomers() {
        return null;
    }

    @Override
    public BankAccount getBankAccount(String accountId) {
        return null;
    }

    @Override
    public void debit(String accountId, double amount, String description) {

    }

    @Override
    public void credit(String accountId, double amount, String description) {

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) {

    }
}
