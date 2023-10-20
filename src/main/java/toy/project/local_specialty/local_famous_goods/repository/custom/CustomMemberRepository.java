package toy.project.local_specialty.local_famous_goods.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.project.local_specialty.local_famous_goods.domain.auth.Member;
import toy.project.local_specialty.local_famous_goods.dto.member.list.SellerListResponse;
import toy.project.local_specialty.local_famous_goods.dto.search.SearchRequest;


import java.util.List;

public interface CustomMemberRepository {
    List<SellerListResponse> findSellerAllBySearch(SearchRequest searchRequest , Pageable pageable);
    JPAQuery<Long> findSellerAllBySearchCount(SearchRequest searchRequest , Pageable pageable);
    Page<SellerListResponse> findSellerAllBySearchAndCount(SearchRequest searchRequest, Pageable pageable);
}
