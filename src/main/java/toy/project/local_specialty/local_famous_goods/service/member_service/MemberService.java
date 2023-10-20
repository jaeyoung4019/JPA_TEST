package toy.project.local_specialty.local_famous_goods.service.member_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import toy.project.local_specialty.local_famous_goods.dto.member.list.MemberListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.list.SellerListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyResponse;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;
import toy.project.local_specialty.local_famous_goods.dto.search.SearchRequest;
import toy.project.local_specialty.local_famous_goods.exception.RestException;

public interface MemberService{

    Response<String> saveMember(SellerSaveRequest param) throws RestException;
    Response<MemberPasswordModifyResponse> memberSelfModifyByPassword(MemberPasswordModifyRequest memberPasswordModifyRequest) throws RestException;
    Page<? extends MemberListResponse> memberList(SearchRequest searchRequest, Pageable pageable);
}
