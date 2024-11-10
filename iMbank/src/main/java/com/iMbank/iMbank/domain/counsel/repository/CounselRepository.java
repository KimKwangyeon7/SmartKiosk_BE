package com.iMbank.iMbank.domain.counsel.repository;

import com.iMbank.iMbank.domain.counsel.entity.Counsel;
import com.iMbank.iMbank.domain.department.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CounselRepository extends JpaRepository<Counsel, Integer>, CounselCustom {
    @Query("SELECT c FROM Counsel c WHERE c.department = :dept AND c.user_dvcd = :wdDvcd AND c.csnl_cd = :csnlCd AND c.crdt = :todayDate ORDER BY c.ticket_stime")
    Optional<List<Counsel>> findFirstByDepartmentAndUserDvcdAndCsnlCdOrderByTicketStime(Department dept, String wdDvcd, String csnlCd, String todayDate);

    @Query("SELECT COALESCE(AVG(c.csnl_time), 0) FROM Counsel c WHERE c.department = :deptId " +
            "AND c.csnl_cd = '02' AND c.user_dvcd = :code GROUP BY c.user_dvcd")
    Double getAvgCsnlTime(Department deptId, String code);

    @Query("SELECT COALESCE(AVG(c.csnl_time), 0) FROM Counsel c WHERE " +
            "c.csnl_cd = '02' AND c.user_dvcd = :code GROUP BY c.user_dvcd")
    Double getOtherAvgCsnlTime(String code);

    @Query("SELECT COALESCE(AVG(c.wait_time), 0) FROM Counsel c WHERE c.department = :deptId " +
            "AND c.csnl_cd = '02' AND c.user_dvcd = :code GROUP BY c.user_dvcd")
    Double getAvgWaitTime(Department deptId, String code);

    @Query("SELECT COALESCE(AVG(c.wait_time), 0) FROM Counsel c WHERE " +
            "c.csnl_cd = '02' AND c.user_dvcd = :code GROUP BY c.user_dvcd")
    Double getOtherAvgWaitTime(String code);

    @Query("SELECT COUNT(c.counsel_id) FROM Counsel c WHERE c.kiosk_id = :kioskId " +
            "AND c.user_dvcd = :userDvcd AND c.csnl_cd = :code AND c.crdt = :todayDate")
    int getTotalWaitCnt(int kioskId, String userDvcd, String code, String todayDate);

    @Query("SELECT COALESCE(COUNT(c.counsel_id), 0) FROM Counsel c WHERE c.department = :deptId " +
            "AND c.csnl_cd = '02' AND c.user_dvcd = :code")
    Long getTotalCntByWork(Department deptId, String code);

    @Query("SELECT COUNT(c.counsel_id) FROM Counsel c WHERE c.department = :deptNm AND HOUR(c.csnl_start_dt) = :time AND SUBSTRING(c.crdt, 1, 6) = :month")
    int getCntByTime(Department deptNm, int time, String month);

    @Query("SELECT c FROM Counsel c WHERE c.csnl_cd = '01' AND  c.crdt = :today AND c.department = :dept")
    List<Counsel> findWicketCounseling(String today, Department dept);

    @Query(value = "SELECT MAX(TIMESTAMPDIFF(MINUTE, csnl_start_dt, :currentTime)) FROM Counsel " +
            "WHERE dept_id = :deptId AND csnl_cd = '01' AND user_dvcd = :serviceCode", nativeQuery = true)
    Integer findMaxWaitTime(@Param("currentTime") LocalDateTime currentTime, @Param("dept") String deptId, @Param("serviceCode") String serviceCode);

    @Query("SELECT COUNT(c.counsel_id) FROM Counsel c WHERE c.department = :dept AND c.user_dvcd = :serviceCode")
    int getCnslCounterCnt(Department dept, String serviceCode);

    @Query("SELECT c.csnl_start_dt FROM Counsel c WHERE c.department = :dept AND c.user_dvcd = :serviceCode ORDER BY c.csnl_start_dt")
    List<LocalDateTime> findByTimeDesc(Department dept, String serviceCode);
}
