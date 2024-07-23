package org.example.settlement.repository;

import org.example.settlement.dbentity.TppProductRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TppProductRegisterRepo extends JpaRepository<TppProductRegister,Long> {
    List<TppProductRegister> findAllByProductId(Long product_id);
    @Query(nativeQuery = true,
    value="select * from tpp_product_register where " +
            "product_id = ?1 " +
            "and type = ?2")
    List<TppProductRegister> findAllByProductIdAndType(Integer prod_id, String type);
}
