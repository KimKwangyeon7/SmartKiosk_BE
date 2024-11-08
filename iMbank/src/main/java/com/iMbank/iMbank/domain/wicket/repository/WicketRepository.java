package com.iMbank.iMbank.domain.wicket.repository;

import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.wicket.entity.Wicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WicketRepository extends JpaRepository<Wicket, Integer>, WicketCustom {
    @Query("SELECT w.wd_dvcd FROM Wicket w WHERE w.department = :deptId AND w.wd_num = :wdNum")
    String findWdDvcdByDeptIdAndWdNum(Department deptId, int wdNum);

    @Query("SELECT w.user_id FROM Wicket w WHERE w.department = :deptId AND w.wd_num = :wdNum")
    int findUserIdByDeptIdAndWdNum(Department deptId, int wdNum);

    @Query("SELECT w FROM Wicket w WHERE w.department = :deptId AND w.wd_num = :wdNum")
    Wicket findByDeptIdAndWdNum(Department deptId, int wdNum);

    @Query("SELECT COUNT(w.wd_id) FROM Wicket w WHERE w.department = :deptId AND w.wd_dvcd = :code")
    Long getWicketCntByDept(Department deptId, String code);

    @Query("SELECT w FROM Wicket w WHERE w.department = :deptNm")
    List<Wicket> findByDeptNm(Department deptNm);

    @Query("SELECT w FROM Wicket w WHERE w.wd_id = :counterId")
    Wicket findByWd_id(int counterId);

    @Query("DELETE FROM Wicket w WHERE w.wd_id = :wdId AND w.department = :dept")
    void deleteByWdId(int wdId, Department dept);

    @Transactional
    @Modifying
    @Query("DELETE FROM Wicket w where w.wd_floor = :floor AND w.department = :dept")
    void deleteByFloor(int floor, Department dept);

    @Transactional
    @Modifying
    @Query("UPDATE Wicket w SET w.wd_floor = w.wd_floor-1 WHERE w.wd_floor > :floor AND w.department = :dept")
    void updateFloor(int floor, Department dept);
}
