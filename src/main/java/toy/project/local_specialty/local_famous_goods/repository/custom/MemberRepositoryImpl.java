package toy.project.local_specialty.local_famous_goods.repository.custom;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import toy.project.local_specialty.local_famous_goods.domain.auth.Member;
import toy.project.local_specialty.local_famous_goods.domain.auth.QMember;
import toy.project.local_specialty.local_famous_goods.domain.auth.QSeller;
import toy.project.local_specialty.local_famous_goods.dto.member.list.SellerListResponse;
import toy.project.local_specialty.local_famous_goods.dto.search.SearchRequest;
import toy.project.local_specialty.local_famous_goods.exception.RestException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.function.Function;

import static toy.project.local_specialty.local_famous_goods.domain.auth.QMember.member;
import static toy.project.local_specialty.local_famous_goods.domain.auth.QSeller.seller;


@RequiredArgsConstructor
public class MemberRepositoryImpl implements CustomMemberRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SellerListResponse> findSellerAllBySearch(SearchRequest searchRequest, Pageable pageable) {
        return queryFactory
                .select(Projections.bean(SellerListResponse.class, seller.name , seller.userId , seller.address.city , seller.address.street , seller.address.zipCode))
                .from(seller)
                .where(conditionCategory( searchRequest.getCategory() , searchRequest.getKeyword()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public JPAQuery<Long> findSellerAllBySearchCount(SearchRequest searchRequest, Pageable pageable) {
        return queryFactory
                .select(seller.count())
                .from(seller)
                .where(conditionCategory( searchRequest.getCategory() , searchRequest.getKeyword()));
    }

    @Override
    public Page<SellerListResponse> findSellerAllBySearchAndCount(SearchRequest searchRequest, Pageable pageable){
        List<SellerListResponse> sellerAllBySearch = findSellerAllBySearch(searchRequest, pageable);
        JPAQuery<Long> sellerAllBySearchCount = findSellerAllBySearchCount(searchRequest, pageable);
        return PageableExecutionUtils.getPage(sellerAllBySearch , pageable , sellerAllBySearchCount::fetchOne);
    }


    private <T> BooleanExpression condition(T value, Function<T, BooleanExpression> function) {
        return Optional.ofNullable(value).map(function).orElse(null);
    }


    private BooleanExpression conditionCategory(String category , String keyword  ) {
        return Optional.ofNullable(category).map(cate-> {
            if (cate.equals("name")) return Optional.ofNullable(keyword).map(seller.name::eq).orElse(null);
            if (cate.equals("userId")) return Optional.ofNullable(keyword).map(seller.userId::eq).orElse(null);
            if (cate.equals("brandName")) return Optional.ofNullable(keyword).map(val -> seller.brandName.eq(val)).orElse(null);
            return null;
        }).orElse(null);
    }
}
