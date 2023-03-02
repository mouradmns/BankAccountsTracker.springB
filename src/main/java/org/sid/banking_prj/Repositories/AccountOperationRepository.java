package org.sid.banking_prj.Repositories;

import org.sid.banking_prj.Entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
