package com.iMbank.iMbank.domain.statistics.service;

import com.iMbank.iMbank.domain.counsel.repository.CounselRepository;
import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.department.repository.DepartmentRepository;
import com.iMbank.iMbank.domain.department.repository.WorkRepository;
import com.iMbank.iMbank.domain.statistics.dto.AvgCsnlTimeResponse;
import com.iMbank.iMbank.domain.statistics.dto.AvgWaitTimeResponse;
import com.iMbank.iMbank.domain.statistics.dto.DailyCsnlCntResponse;
import com.iMbank.iMbank.domain.wicket.repository.WicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //Set<String> keySet = map.keySet();

        for (String code: list){
            Double avg = counselRepository.getOtherAvgCsnlTime(dept, code);
            avg = avg == null ? 0 : avg;
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            other.put(codeName, avg);
        }

        return new AvgCsnlTimeResponse(map, other);
    }

    @Override
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
            Double avg = counselRepository.getOtherAvgWaitTime(dept, code);
            avg = avg == null ? 0 : avg;
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            other.put(codeName, avg);
        }

        return new AvgWaitTimeResponse(map, other);

    }

    @Override
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
    public Map<String, Map<Integer, Long>> getPeriodCnt(String deptNm, String year){
        // 특정 영업점의 업무 관련 테이블 존재 -> 추후 고치기
        List<String> list = getAllWorks(deptNm);

        Map<String, Map<Integer, Long>> map = new HashMap<>();
        for (String code: list){
            Map<Integer, Long> period = new HashMap<>();
            for (int i = 1; i < 5; i++){
                Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
                if (dept != null) {
                    long sum = counselRepository.getPeriodCnt(dept, code, year, i);
                    period.put(i, sum);
                }
            }
            String codeName = workRepository.findWorkDvcdNmByDepartmentAndWorkDvcd(deptNm, code);
            map.put(codeName, period);
        }
        return map;
    }

    @Override
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

    @Override
    public Map<Integer, Integer> getAvgCntByTime(String deptNm) {
        int stime = Integer.parseInt(departmentRepository.findStimeByDeptNm(deptNm).substring(0, 2));
        int etime = Integer.parseInt(departmentRepository.findEtimeByDeptNm(deptNm).substring(0, 2));
        Map<Integer, Integer> map = new HashMap<>();
        Department dept = departmentRepository.findByDeptNM(deptNm).orElse(null);
        String month = "11";
        for (int i = stime; i < etime; i++){
            int cnt = counselRepository.getCntByTime(dept, i, month);
            map.put(i, cnt);
        }
        return map;
    }

    @Override
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
