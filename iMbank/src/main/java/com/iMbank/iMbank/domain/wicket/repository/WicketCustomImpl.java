package com.iMbank.iMbank.domain.wicket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WicketCustomImpl implements WicketCustom {

    private final JPAQueryFactory queryFactory;



}
