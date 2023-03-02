package org.sid.banking_prj.Services;

import org.sid.banking_prj.Entities.BankAccount;
import org.sid.banking_prj.Entities.Customer;

import java.util.List;

public interface BankAccountService {
    Customer saveCustomer(Customer customer);
    BankAccount saveBankAccount(double initialBalance, String type,long customerId);
    List<Customer> listCustomers();
    BankAccount getBankAccount(String accountId);
    void debit(String accountId,double amount,String description);
    void credit(String accountId,double amount,String description);
    void transfer(String accountIdSource,String accountIdDestination,double amount);


}
