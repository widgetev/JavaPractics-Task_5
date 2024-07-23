package org.example.settlement.repository;

import org.example.settlement.dbentity.AccountPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountPoolRepo extends JpaRepository<AccountPool,Long> {

    @Query(nativeQuery = true,
    value = "select * from account_pool where " +
            "branch_code = ?1 and currency_code= ?2 and mdm_code = ?3 and registry_type_code = ?4 and priority_code = ?5")
    List<AccountPool> findAccountPools(
            String branchCode, String currencyCode, String mdmCode, String priorityCode, String registerTypeCode);
    //наверно иожно и так - но это хрень какая-то
    List<AccountPool> findAccountPoolsByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
            String branchCode, String currencyCode, String mdmCode, String priorityCode, String registerTypeCode);

}
