package com.iMbank.iMbank.domain.department.repository;

import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.department.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {
    @Query("SELECT w.work_dvcd FROM Work w WHERE w.dept_nm = :deptNm")
    List<String> findWorkDvcdByDeptNm(String deptNm);

    @Query("SELECT w.work_dvcd_nm FROM Work w WHERE w.dept_nm = :dept AND w.work_dvcd = :workDvcd")
    String findWorkDvcdNmByDepartmentAndWorkDvcd(String dept, String workDvcd);

    @Query("SELECT w.work_dvcd FROM Work w WHERE w.work_dvcd_nm = :workDvcdNm AND w.department = :deptId")
    String findWorkDvcdByWorkDvcdNm(String workDvcdNm, Department deptId );

    @Query("SELECT w FROM Work w WHERE w.dept_nm = :deptNm AND w.work_dvcd_nm = :workDvcdNm")
    Work findWorkByWorkDvcdNm(String deptNm, String workDvcdNm);

    @Modifying(clearAutomatically = true)
    @Query("DELETE FROM Work w WHERE w.dept_nm = :deptNm AND w.work_dvcd_nm = :workNm")
    void deleteByDeptNmAndWorkNm(String deptNm, String workNm);

    @Query("SELECT w FROM Work w WHERE w.dept_nm = :deptNm ORDER BY w.work_dvcd")
    List<Work> findAllByDeptNm(String deptNm);
}
