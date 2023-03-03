package org.sid.banking_prj.Mappers;

import org.sid.banking_prj.Dtos.CustomerDTO;
import org.sid.banking_prj.Entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer) {

        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO) {

        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;
    }
}
