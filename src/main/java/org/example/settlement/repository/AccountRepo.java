package org.example.settlement.repository;

import org.example.settlement.dbentity.Account;
import org.example.settlement.dbentity.AccountPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {

//    @Transactional //а надо??
//    List<Account> findByAccountPoolId(Long id);
    List<Account> findAccountsByAccountPoolId(AccountPool accountPoolId);
}
