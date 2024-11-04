package com.iMbank.iMbank.domain.kiosk.repository;

import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.kiosk.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KioskRepository extends JpaRepository<Kiosk, Integer> {
    @Query("SELECT k FROM Kiosk k WHERE k.kiosk_id = :id")
    Optional<Kiosk> findByKioskId(int id);

    @Query("SELECT k FROM Kiosk k WHERE k.department = :dept")
    Optional<Kiosk> findByDepartment(Department dept);
}
