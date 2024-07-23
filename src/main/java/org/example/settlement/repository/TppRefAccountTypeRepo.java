package org.example.settlement.repository;

import org.example.settlement.dbentity.TppRefAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TppRefAccountTypeRepo extends JpaRepository<TppRefAccountType, Long> {
}
