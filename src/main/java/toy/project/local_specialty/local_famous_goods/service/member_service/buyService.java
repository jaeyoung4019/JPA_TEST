package toy.project.local_specialty.local_famous_goods.service.member_service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import toy.project.local_specialty.local_famous_goods.dto.member.list.BuyerListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.list.MemberListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyResponse;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;
import toy.project.local_specialty.local_famous_goods.dto.search.SearchRequest;
import toy.project.local_specialty.local_famous_goods.exception.RestException;

@Service
public class buyService implements MemberService{

    @Override
    public Response<String> saveMember(SellerSaveRequest param) {
        return null;
    }

    @Override
    public Response<MemberPasswordModifyResponse> memberSelfModifyByPassword(MemberPasswordModifyRequest memberPasswordModifyRequest) throws RestException {
        return null;
    }

    @Override
    public Page<BuyerListResponse> memberList(SearchRequest searchRequest, Pageable pageable) {
        return null;
    }
}
