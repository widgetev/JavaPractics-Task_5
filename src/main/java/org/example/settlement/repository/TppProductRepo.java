package org.example.settlement.repository;

import org.example.settlement.dbentity.TppProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TppProductRepo extends JpaRepository<TppProduct,Long> {
    List<TppProduct> findTppProductsByNumber(String number); //findAllByNumber(String number);
    TppProduct findTppProductById(Long id);
}
