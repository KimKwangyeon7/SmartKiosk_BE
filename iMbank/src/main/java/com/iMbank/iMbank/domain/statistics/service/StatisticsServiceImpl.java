package com.iMbank.iMbank.domain.statistics.service;

import com.iMbank.iMbank.domain.counsel.repository.CounselRepository;
import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.department.repository.DepartmentRepository;
import com.iMbank.iMbank.domain.department.repository.WorkRepository;
import com.iMbank.iMbank.domain.statistics.dto.*;
import com.iMbank.iMbank.domain.wicket.repository.WicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final CounselRepository counselRepository;
    private final DepartmentRepository departmentRepository;
    private final WorkRepository workRepository;
    private final WicketRepository wicketRepository;

    @Override
    @Cacheable(value = "avgCsnlTime", key = "'avgCsnlTime:' + #deptNm", unless = "#result == null")
    public AvgCsnlTimeResponse getAvgCsnlTime(String deptNm) {
        // 특정 영업점의 업무 관련 테이블 존재 -> 추후 고치기
        List<String> list = getAllWorks(deptNm);

        Map<String, Double> map = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        for (String code: list){
            if (dept != null) {
                Double avg = counselRepository.getAvgCsnlTime(dept, code);
                avg = avg == null ? 0 : avg;
                String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
                map.put(codeName, avg);
            }
        }
        Map<String, Double> other = new HashMap<>();

        for (String code: list){
            Double avg = counselRepository.getOtherAvgCsnlTime(code);
            avg = avg == null ? 0 : avg;
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            other.put(codeName, avg);
        }

        return new AvgCsnlTimeResponse(map, other);
    }

    @Override
    @Cacheable(value = "avgWaitTime", key = "'avgWaitTime:' + #deptNm", unless = "#result == null")
    public AvgWaitTimeResponse getAvgWaitTime(String deptNm) {
        // 특정 영업점의 업무 관련 테이블 존재 -> 추후 고치기
        List<String> list = getAllWorks(deptNm);

        Map<String, Double> map = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        for (String code: list){
            if (dept != null) {
                Double avg = counselRepository.getAvgWaitTime(dept, code);
                avg = avg == null ? 0 : avg;
                String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
                map.put(codeName, avg);
            }
        }
        Map<String, Double> other = new HashMap<>();
        //Set<String> keySet = map.keySet();

        for (String code: list){
            Double avg = counselRepository.getOtherAvgWaitTime(code);
            avg = avg == null ? 0 : avg;
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            other.put(codeName, avg);
        }

        return new AvgWaitTimeResponse(map, other);

    }



    @Override
    @Cacheable(value = "dailyCnt", key = "'dailyCnt:' + #deptNm", unless = "#result == null")
    public DailyCsnlCntResponse getDailyCnt(String deptNm) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        Map<String, Double> map = new HashMap<>();
        Map<String, Double> other = new HashMap<>();
        for (String day: days){
            Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
            if (dept != null) {
                double tmp = counselRepository.getAvgCnt(dept, day);
                double ttmp = counselRepository.getOtherAvgCnt(dept, day);

                String a;
                switch (day){
                    case "Monday": a = "월"; break;
                    case "Tuesday": a = "화"; break;
                    case "Wednesday": a = "수"; break;
                    case "Thursday": a = "목"; break;
                    case "Friday": a = "금"; break;
                    case "Saturday": a = "토"; break;
                    default: a = "일"; break;
                }
                map.put(a, tmp);
                other.put(a, ttmp);
            }
        }
        return new DailyCsnlCntResponse(map, other);
    }


    @Override
    @Cacheable(value = "yearCnt", key = "'yearCnt:' + #deptNm", unless = "#result == null")
    public YearCntResponse getYearCnt(String deptNm) {
        List<String> list = getAllWorks(deptNm);
        Map<String, Map<String, Long>> my = new HashMap<>();
        Map<String, Map<String, Long>> other = new HashMap<>();
        LocalDateTime now = LocalDateTime.now();
        int year = now.getYear();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        for (String code: list){
            YearCntInfo res = counselRepository.getCntByDeptAndYear(dept, code, year);
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            my.put(codeName, res.my());
            other.put(codeName, res.other());
        }
        return new YearCntResponse(my, other);
    }

    // 분기별 고객수 (업무별)
    @Override
    @Cacheable(value = "periodCnt", key = "'periodCNt:' + #deptNm + #year", unless = "#result == null")
    public PeriodCntResponse getPeriodCnt(String deptNm, String year){
        // 특정 영업점의 업무 관련 테이블 존재 -> 추후 고치기
        List<String> list = getAllWorks(deptNm);

        Map<String, Map<Integer, Long>> my = new HashMap<>();
        Map<String, Map<Integer, Long>> other = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);

        for (String code: list){
            Map<Integer, Long> myPeriod = new HashMap<>();
            Map<Integer, Long> otherPeriod = new HashMap<>();
            for (int i = 1; i < 5; i++){
                if (dept != null) {
                    PeriodCntInfo pci = counselRepository.getPeriodCnt(dept, code, year, i);
                    myPeriod.put(i, pci.my());
                    otherPeriod.put(i, pci.other());
                }
            }
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            my.put(codeName, myPeriod);
            other.put(codeName, otherPeriod);
        }
        return new PeriodCntResponse(my, other);
    }

    // 업무 비율 O
    @Override
    @Cacheable(value = "workPercentage", key = "'workPercentage:' + #deptNm", unless = "#result == null")
    public Map<String, Long> getWorkPercentage(String deptNm) {
        List<String> list = getAllWorks(deptNm);
        Map<String, Long> map = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        for (String code: list){
            if (dept != null) {
                Long sum = counselRepository.getTotalCntByWork(dept, code);
                sum = sum == null ? 0 : sum;
                String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
                map.put(codeName, sum);
            }
        }
        return map;
    }

    // 년, 월에 맞는 고객수(업무 유형별)
    @Override
    @Cacheable(value = "avgCntByMonth", key = "'avgCntByMonth:' + #deptNm + #year + #month", unless = "#result == null")
    public Map<String, List<Long>> getAvgCntByMonth(String deptNm, String year, String month) {
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        List<String> list = getAllWorks(deptNm);
        Map<String, List<Long>> map = new HashMap<>();
        for (String code: list){
            if (dept != null) {
                String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
                List<Long> tmp = counselRepository.getCntByMonth(dept, year, month, code);
                map.put(codeName, tmp);
            }
        }
        return map;
    }

    @Override
    public DailyTalkResponse getAITalk(String deptNm) {

        // 일주일 중 가장 바쁜 요일
        Map<String, Double> map = getDailyCnt(deptNm).myAvg();
        int max = 0;
        String res = "";
        for (String day: map.keySet()){
            if (map.get(day) > max){
                res = day;
            }
        }
        if (res == null){
            res = "수";
        }

        // 오늘 방문자 수는 ~명입니다.
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = now.format(formatter);

        Integer todayVisit = counselRepository.getTotalCountByDeptNm(dept, today);
        if (todayVisit == null){
            todayVisit = 0;
        }

        // 오늘 현재 평균 대기시간은 ~분이빈다.
        Integer todayWatiAvg = counselRepository.getTodayWatiAvg(dept, today);
        if(todayWatiAvg == null){
            todayWatiAvg = 0;
        }

        int time = 0;
        int maxTime = 0;
        //시간대별
        Map<Integer, Double> timeMap = getAvgCntByTime(deptNm);
        for (int cnt: timeMap.keySet()) {
            if (timeMap.get(cnt) > maxTime) {
                time = timeMap.get(cnt).intValue();
            }
        }
        if (time == 0){
            time = 15;
        }
        String etime = departmentRepository.getCloseTime(deptNm);

        return new DailyTalkResponse(res, todayVisit, todayWatiAvg, time, etime);
    }

    // 지난 4달 동안의 시간대별 고객 수
    @Override
    @Cacheable(value = "avgCntByTime", key = "'avgCntByTime:' + #deptNm", unless = "#result == null")
    public Map<Integer, Double> getAvgCntByTime(String deptNm) {
        int stime = Integer.parseInt(departmentRepository.findStimeByDeptNm(deptNm).substring(0, 2));
        int etime = Integer.parseInt(departmentRepository.findEtimeByDeptNm(deptNm).substring(0, 2));
        Map<Integer, Double> map = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue()-1;
        int year = now.getYear();
        for (int j = 0; j < 4; j++) {
            month -= j;
            if (month <= 0){
                month = 12 + month;
                year -= 1;
            }
            String str = "" + month;
            if (month < 10) {
                str = "0" + str;
            }
            String tmp = year + str;
            System.out.println(tmp);
            for (int i = stime; i < etime; i++) {
                int cnt = counselRepository.getCntByTime(dept, i, tmp);
                map.put(i, map.getOrDefault(i, 0.0) + cnt);
            }
        }
        map.replaceAll((k, v) -> map.get(k)/4);
        return map;
    }

    // 해당 지점 창구 비율 O
    @Override
    @Cacheable(value = "wicketPercentage", key = "'wicketPercentage:' + #deptNm", unless = "#result == null")
    public Map<String, Long> getWicketPercentage(String deptNm) {
        List<String> codes = getAllWorks(deptNm);
        Map<String, Long> map = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        for (String code: codes){
            long cnt = wicketRepository.getWicketCntByDept(dept, code);
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            map.put(codeName, cnt);
        }
        return map;
    }

    public List<String> getAllWorks(String deptNm){
        return workRepository.findWorkDvcdByDeptNm(deptNm);
    }
}
