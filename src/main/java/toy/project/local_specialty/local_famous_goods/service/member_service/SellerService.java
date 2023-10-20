package toy.project.local_specialty.local_famous_goods.service.member_service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toy.project.local_specialty.local_famous_goods.domain.auth.Authority;
import toy.project.local_specialty.local_famous_goods.domain.auth.Member;
import toy.project.local_specialty.local_famous_goods.domain.auth.Seller;
import toy.project.local_specialty.local_famous_goods.domain.embedded.Address;
import toy.project.local_specialty.local_famous_goods.dto.enums.AuthoritiesEnum;
import toy.project.local_specialty.local_famous_goods.dto.member.list.MemberListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.list.SellerListResponse;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyResponse;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;
import toy.project.local_specialty.local_famous_goods.dto.search.SearchRequest;
import toy.project.local_specialty.local_famous_goods.exception.RestException;
import toy.project.local_specialty.local_famous_goods.repository.AuthorityRepository;
import toy.project.local_specialty.local_famous_goods.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerService implements MemberService{

    private final MemberRepository memberRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     *  멤버 저장 메소드
     * @param param
     * @return
     * @throws RestException
     */
    @Override
    @Transactional
    public Response<String> saveMember(SellerSaveRequest param) throws RestException {

        Optional<Authority> byMember_userId = authorityRepository.findByMember_UserId(param.getUserId());

        if(byMember_userId.isPresent()){
            throw new RestException(403 , "이미 존재하는 회원 입니다.");
        }

        Seller member = memberRepository
                .save(new Seller.
                        Builder(
                                param.getBrandName(),
                        param.getAcountNumber(),
                        param.getBankName(),
                        Address.builder()
                                .city(param.getCompanyCity())
                                .street(param.getCompanyStreet())
                                .zipCode(param.getCompanyZipCode())
                                .build(),
                        param.getCategory())
                        .name(param.getName())
                        .password( bCryptPasswordEncoder.encode(param.getPassword()))
                        .userId(param.getUserId())
                        .address(
                                Address.builder()
                                .city(param.getCity())
                                .street(param.getStreet())
                                .zipCode(param.getZipCode())
                                .build() )
                        .build()
                );

        authorityRepository.save(Authority.createAuthorityMember(member, AuthoritiesEnum.NORMAL_SELLER));
        return new Response.
                 ResponseBuilder<String>("아이디 :" + member.getUserId() + " 님의 회원가입에 성공하였습니다.", 200)
                .resultResponse(member.getUserId())
                .total(1)
                .build();
    }


    @Override
    @Transactional
    public Response<MemberPasswordModifyResponse> memberSelfModifyByPassword(MemberPasswordModifyRequest memberPasswordModifyRequest) throws RestException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null){
            throw new RestException(403 , "로그인하지 않은 사용자 입니다.");
        }
        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        String contextId = userDetails.getUsername();

        Optional<Member> findMember = memberRepository.findByUserId(memberPasswordModifyRequest.getUserId());

        if(findMember.isPresent()){
            Seller member = (Seller) findMember.get();
            if( !contextId.equals(memberPasswordModifyRequest.getUserId()) )
                throw new RestException(403 , "비 정상적인 접근입니다.");
            if( !bCryptPasswordEncoder.matches(memberPasswordModifyRequest.getOldPassword(), member.getPassword()))
                throw  new RestException(403 , "비밀번호가 올바르지 않습니다.");

            member.memberSecuritySetPassword(bCryptPasswordEncoder.encode(memberPasswordModifyRequest.getPassword()));
            memberRepository.save(member);
            return new Response.ResponseBuilder<MemberPasswordModifyResponse>( "비밀번호 변경에 성공하였습니다." , 200)
                    .resultResponse( new MemberPasswordModifyResponse(memberPasswordModifyRequest.getUserId()))
                    .total(1)
                    .build();
        } else {
            throw new RestException(403 , "알맞지 않은 회원 입니다.");
        }


        //  변경 감지 미사용
        // int successCheckNumber = memberRepository.memberSelfModifyByPassword(param.getUserId(), bCryptPasswordEncoder.encode(param.getPassword()));
        //        if (successCheckNumber == 1){
        //            return new Response.ResponseBuilder<MemberPasswordModifyResponse>( "비밀번호 변경에 성공하였습니다." , 200)
        //                    .resultResponse( new MemberPasswordModifyResponse(param.getUserId()))
        //                    .total(1)
        //                    .build();
        //        } else {
        //            throw new RestException(403 , "존재하지 않는 회원 입니다.");
        //        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SellerListResponse> memberList(SearchRequest searchRequest, Pageable pageable) {
        return memberRepository.findSellerAllBySearchAndCount(searchRequest, pageable);
    }
}
