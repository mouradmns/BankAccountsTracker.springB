package org.sid.banking_prj.Services;

import org.sid.banking_prj.Dtos.CustomerDTO;
import org.sid.banking_prj.Entities.BankAccount;
import org.sid.banking_prj.Entities.CurrentAccount;
import org.sid.banking_prj.Entities.SavingAccount;
import org.sid.banking_prj.exceptions.BalanceNotSufficientException;
import org.sid.banking_prj.exceptions.BankAccountNotFoundException;
import org.sid.banking_prj.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customer);

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, long customerId) throws CustomerNotFoundException;
    SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccount> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
}
