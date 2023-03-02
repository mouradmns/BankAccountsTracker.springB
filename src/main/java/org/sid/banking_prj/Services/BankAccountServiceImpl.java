package org.sid.banking_prj.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.banking_prj.Dtos.CustomerDTO;
import org.sid.banking_prj.Entities.*;
import org.sid.banking_prj.Enums.OperationType;
import org.sid.banking_prj.Mappers.BankAccountMapperImpl;
import org.sid.banking_prj.Repositories.AccountOperationRepository;
import org.sid.banking_prj.Repositories.BankAccountRepository;
import org.sid.banking_prj.Repositories.CustomerRepository;
import org.sid.banking_prj.exceptions.BalanceNotSufficientException;
import org.sid.banking_prj.exceptions.BankAccountNotFoundException;
import org.sid.banking_prj.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;
        private BankAccountRepository bankAccountRepository;
        private AccountOperationRepository accountOperationRepository;

        private BankAccountMapperImpl dtoMapper;



    @Override
    public Customer saveCustomer(Customer customer) {

        log.info("saving a new Customer");
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, long customerId) throws CustomerNotFoundException {
                Customer customer= customerRepository.findById(customerId).orElse(null);

                if(customer==null)
                    throw new CustomerNotFoundException("customer not found");

        CurrentAccount currentAccount =new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setBalance(initialBalance);
        currentAccount.setCreatedAt(new Date());
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);

        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, long customerId) throws CustomerNotFoundException {

        Customer customer= customerRepository.findById(customerId).orElse(null);

        if(customer==null)
            throw new CustomerNotFoundException("customer not found");

        CurrentAccount savingBankAccount =new CurrentAccount();
        savingBankAccount.setId(UUID.randomUUID().toString());
        savingBankAccount.setBalance(initialBalance);
        savingBankAccount.setCreatedAt(new Date());
        savingBankAccount.setOverDraft(interestRate);
        savingBankAccount.setCustomer(customer);
        CurrentAccount savedBankAccount = bankAccountRepository.save(savingBankAccount);


        return null;
    }


    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {

        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(
                ()->new BankAccountNotFoundException("account not found"));


        return bankAccount;
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {

            BankAccount bankAccount=getBankAccount(accountId);
            if (bankAccount.getBalance()<amount)
                    throw new BalanceNotSufficientException("balance not sufficient");
            AccountOperation   accountOperation = new AccountOperation();

            accountOperation.setType(OperationType.DEBIT);
            accountOperation.setAmount(amount);
            accountOperation.setDescription(description);
            accountOperation.setOperationDate(new Date());
            accountOperation.setBankAccount(bankAccount);
            accountOperationRepository.save(accountOperation);

            bankAccount.setBalance(bankAccount.getBalance()-amount);
            bankAccountRepository.save(bankAccount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {


        BankAccount bankAccount=getBankAccount(accountId);
        if (bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("balance not sufficient");
        AccountOperation   accountOperation = new AccountOperation();

        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {

        debit(accountIdSource,amount,"transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from "+accountIdDestination);
    }

    @Override
    public List<BankAccount> bankAccountList(){
        return bankAccountRepository.findAll();
    }

    @Override
    public List<CustomerDTO> listCustomers() {

        List<Customer> customers = customerRepository.findAll();

        List<CustomerDTO>  customerDTO=customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).
                collect(Collectors.toList());

        return customerDTO;
    }

}
