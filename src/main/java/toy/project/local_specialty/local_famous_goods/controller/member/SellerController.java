package toy.project.local_specialty.local_famous_goods.controller.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.project.local_specialty.local_famous_goods.dto.member.list.MemberListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.list.SellerListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyResponse;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;
import toy.project.local_specialty.local_famous_goods.dto.search.SearchRequest;
import toy.project.local_specialty.local_famous_goods.exception.RestException;
import toy.project.local_specialty.local_famous_goods.service.member_service.MemberService;
import toy.project.local_specialty.local_famous_goods.utils.error.BindingErrorCacheKey;
import toy.project.local_specialty.local_famous_goods.utils.error.BindingResultCache;

import javax.validation.Valid;

@RestController
public class SellerController {

    @Autowired
    public SellerController(@Qualifier(value = "sellerService") MemberService memberService) {
        this.memberService = memberService;
    }
    private final MemberService memberService;

    @PostMapping("/save/sale_member")
    public Response<String> sellerSave(@RequestBody @Valid SellerSaveRequest param , BindingResult bindingResult) throws RestException {
        if(bindingResult.hasErrors()) {
            throw new RestException(403 , "알맞은 값을 입력해주세요.");
        }
        return memberService.saveMember(param);
    }


    @PostMapping("/sale_member/modify_pwd")
    public Response<MemberPasswordModifyResponse> sellerModifyByPassword(@RequestBody @Valid MemberPasswordModifyRequest memberPasswordModifyRequest, BindingResult bindingResult) throws RestException {
        if(bindingResult.hasErrors()) {
            throw new RestException(403 , "알맞은 값을 입력해주세요.");
        }
        return memberService.memberSelfModifyByPassword(memberPasswordModifyRequest);
    }

    @GetMapping("/sale_member/list")
    public Page<? extends MemberListResponse> sellerList(@RequestBody SearchRequest searchRequest , Pageable pageable){
        return memberService.memberList(searchRequest , pageable);
    }

//    @GetMapping("/sale_member/list")
//    public HashMap<Key, Object> sellerList(@RequestBody SearchRequest searchRequest , Pageable pageable){
//        return memberService.memberList(searchRequest , pageable);
//    }

}
