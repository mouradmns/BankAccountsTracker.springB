package org.sid.banking_prj.Repositories;

import org.sid.banking_prj.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
