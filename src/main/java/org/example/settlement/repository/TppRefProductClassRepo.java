package org.example.settlement.repository;

import org.example.settlement.dbentity.TppRefProductClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TppRefProductClassRepo extends JpaRepository<TppRefProductClass, Long> {
}
