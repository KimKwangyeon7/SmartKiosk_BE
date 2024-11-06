package com.iMbank.iMbank.domain.counsel.repository;

import com.iMbank.iMbank.domain.counsel.entity.QCounsel;
import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.statistics.dto.PeriodCntInfo;
import com.iMbank.iMbank.domain.statistics.dto.YearCntInfo;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CounselCustomImpl implements CounselCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public double getAvgCnt(Department deptId, String day) {
        QCounsel counsel = QCounsel.counsel;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        List<Tuple> list = queryFactory
                .select(counsel.counsel_id.count(), counsel.crdt)
                .from(counsel)
                .where(counsel.department.eq(deptId)
                    .and(counsel.csnl_cd.eq("02")))
                .groupBy(counsel.crdt)
                .fetch();

        double cnt = 0;
        double sum = 0;

        for (Tuple tuple : list) {
            String date = tuple.get(counsel.crdt);
            LocalDate localDate = LocalDate.parse(date, formatter);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();

            if (dayOfWeek.equals(DayOfWeek.valueOf(day.toUpperCase()))) {
                Long count = tuple.get(counsel.counsel_id.count()); // 상담 건수
                sum += (count != null ? count : 0);
                cnt++;
            }
        }

        // cnt가 0인 경우 0을 반환하여 나누기 오류 방지
        return cnt > 0 ? sum / cnt : 0;
    }

    @Override
    public double getOtherAvgCnt(Department deptId, String day) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        QCounsel sub = new QCounsel("sub");


        List<Tuple> sq = queryFactory
                .select(sub.counsel_id.count(), sub.department, sub.crdt)
                .from(sub)
                .where(sub.department.notIn(deptId)
                        .and(sub.csnl_cd.eq("02")))
                .groupBy(sub.department, sub.crdt)
                .fetch();

        Map<String, Integer> c = new HashMap<>();
        Map<String, Long> s = new HashMap<>();
        for (Tuple tuple: sq){
            String date = tuple.get(sub.crdt);
            c.put(date, c.getOrDefault(date, 0) + 1);
            s.put(date, s.getOrDefault(date, 0L) + tuple.get(sub.counsel_id.count()));
        }

        Map<String, Double> map = new HashMap<>();
        for (String key: c.keySet()) {
            double avg = (double) s.get(key) / c.get(key);
            map.put(key, avg);
        }

        double cnt = 2.0;
        double sum = 0;

        for (String date: map.keySet()) {
            LocalDate localDate = LocalDate.parse(date, formatter);
            DayOfWeek dayOfWeek = localDate.getDayOfWeek();

            if (dayOfWeek.equals(DayOfWeek.valueOf(day.toUpperCase()))) {
                Double count = map.get(date); // 상담 건수
                sum += (count != null ? count : 0);
                //cnt++;
            }
        }
        //System.out.println(cnt + " " + sum);
        // cnt가 0인 경우 0을 반환하여 나누기 오류 방지
        return sum / cnt;
    }

    @Override
    public PeriodCntInfo getPeriodCnt(Department deptId, String code, String year, int period) {
        QCounsel c = QCounsel.counsel;

        // 시작 및 종료 범위 계산
        int start = Integer.parseInt(year) * 10000 + (1 + 3 * (period - 1)) * 100;
        int end = Integer.parseInt(year) * 10000 + (3 + 3 * (period - 1)) * 100 + 32;

        // 쿼리 결과를 Long으로 받아온 후 null 처리 및 double로 변환
        Long myCount = queryFactory
                .select(c.counsel_id.count())
                .from(c)
                .where(c.department.eq(deptId)
                        .and(c.user_dvcd.eq(code))
                        .and(c.crdt.between("" + start, "" + end))
                        .and(c.csnl_cd.eq("02")))
                .fetchOne();

        Long otherCount = queryFactory
                .select(c.counsel_id.count())
                .from(c)
                .where(c.user_dvcd.eq(code)
                        .and(c.crdt.between("" + start, "" + end))
                        .and(c.csnl_cd.eq("02")))
                .fetchOne();

        // null일 경우 0.0으로 반환하고 아닐 경우 double로 변환
        myCount = myCount != null ? myCount : 0;
        otherCount = otherCount != null ? otherCount : 0;
        return new PeriodCntInfo(myCount, otherCount/6);
    }

    @Override
    public YearCntInfo getCntByDeptAndYear(Department dept, String code, int yyear) {
        QCounsel c = QCounsel.counsel;
        int year =  yyear - 4;
        Map<String, Long> my = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            String syear = "" + year;
            Long count = queryFactory
                    .select(c.counsel_id.count())
                    .from(c)
                    .where(c.department.eq(dept)
                            .and(c.user_dvcd.eq(code))
                            .and(c.crdt.substring(0, 4).eq(syear))
                            .and(c.csnl_cd.eq("02")))
                    .fetchOne();
            count = count != null ? count : 0;
            my.put(syear, count);
            year++;
        }

        year = yyear - 4;
        Map<String, Long> other = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            String syear = "" + year;
            Long count = queryFactory
                    .select(c.counsel_id.count())
                    .from(c)
                    .where(c.user_dvcd.eq(code)
                            .and(c.crdt.substring(0, 4).eq(syear))
                            .and(c.csnl_cd.eq("02")))
                    .fetchOne();
            count = count != null ? count : 0;
            count /= 6;
            other.put(syear, count);
            year++;
        }

        return new YearCntInfo(my, other);
    }

    @Override
    public List<Long> getCntByMonth(Department dept, String year, String month, String code) {
        QCounsel c = QCounsel.counsel;
        LocalDate day = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), 1);
        LocalDate end = day.withDayOfMonth(day.lengthOfMonth());
        int endDay = end.getDayOfMonth();
        List<Long> list = new ArrayList<>();
        for (int i = 1; i <= endDay; i++){
            String str = "";
            if (i < 10){
                str = "0" + i;
            } else {
                str = "" + i;
            }
            Long count = queryFactory
                    .select(c.counsel_id.count())
                    .from(c)
                    .where(c.department.eq(dept)
                            .and(c.user_dvcd.eq(code))
                            .and(c.crdt.eq(year+month+str))
                            .and(c.csnl_cd.eq("02")))
                    .fetchOne();
            count = count != null ? count : 0;
            list.add(count);
        }
        return list;
    }
}
