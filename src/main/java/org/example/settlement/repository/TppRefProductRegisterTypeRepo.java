package org.example.settlement.repository;

import org.example.settlement.dbentity.TppRefProductRegisterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TppRefProductRegisterTypeRepo extends JpaRepository<TppRefProductRegisterType, Long> {

    //По КодуПродукта найти связные записи в Каталоге Типа регистра
    //Request.Body.ProductCode == tpp_ref_product_class.value <-> tpp_ref_product_register_type.product_class_code
    // И (среди найденных записей отобрать те, у которых)
    //tpp_ref_product_register_type.account_type имеет значение  “Клиентский”

    //Судя по схеме БД идентфикатор - Value  -его буду использовать дальше
    //@Query(value = "select value from tpp_ref_product_register_type where account_type = ?1 and product_class_code = ?2 ",nativeQuery = true)
    List<TppRefProductRegisterType> findByAccountType_ValueIsAndProductClassCode_Value(String accType, String productCode); //я удивлен!

    TppRefProductRegisterType findTppRefProductRegisterTypeByInternalId(Long id);

    TppRefProductRegisterType findByValue(String registerValue);

    TppRefProductRegisterType findFirstByValue(String registerValue);

}
