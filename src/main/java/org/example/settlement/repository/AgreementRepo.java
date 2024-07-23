package org.example.settlement.repository;

import org.example.settlement.dbentity.Agreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementRepo extends JpaRepository<Agreement,Long> {

    @Query(value = "select * from agreement where product_id = ?1", nativeQuery = true)
    List<Agreement> findAllByproductId(Long product_id);
    List<Agreement> findByNumber(String number);
}
