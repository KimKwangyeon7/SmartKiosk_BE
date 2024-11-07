package com.iMbank.iMbank.domain.department.repository;

import com.iMbank.iMbank.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query("SELECT d.dept_nm from Department d WHERE d.dept_id = :id")
    String findDeptNameByDeptId(String id);

    @Query("SELECT d.dept_id from Department d WHERE d.dept_nm = :deptNm")
    String findDeptIdByDeptNm(String deptNm);

    @Query("SELECT d from Department d WHERE d.dept_id = :id")
    Optional<Department> findByDeptId(String id);

    @Query("SELECT d from Department d WHERE d.dept_nm = :deptNm")
    Optional<Department> findByDeptNM(String deptNm);

    @Query("SELECT d.stime from Department d WHERE d.dept_nm = :deptNm")
    String findStimeByDeptNm(String deptNm);

    @Query("SELECT d.etime from Department d WHERE d.dept_nm = :deptNm")
    String findEtimeByDeptNm(String deptNm);
}
