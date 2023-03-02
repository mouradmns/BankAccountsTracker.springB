package org.sid.banking_prj.Repositories;

import org.sid.banking_prj.Entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    Optional<BankAccount> findById(String accountId);
}
