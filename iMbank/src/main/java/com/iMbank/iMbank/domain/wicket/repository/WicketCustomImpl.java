package com.iMbank.iMbank.domain.wicket.repository;

import com.iMbank.iMbank.domain.department.entity.Department;
import com.iMbank.iMbank.domain.wicket.entity.QWicket;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WicketCustomImpl implements WicketCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public void deleteByWdId(int wdId) {
        QWicket wicket = QWicket.wicket;

        queryFactory.delete(wicket)
                .where(wicket.wd_id.eq(wdId))
                .execute();

    }
}
