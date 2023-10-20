package toy.project.local_specialty.local_famous_goods.service;


import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toy.project.local_specialty.local_famous_goods.domain.auth.*;
import toy.project.local_specialty.local_famous_goods.dto.member.modify_password.MemberPasswordModifyRequest;
import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;
import toy.project.local_specialty.local_famous_goods.dto.response.Response;
import toy.project.local_specialty.local_famous_goods.exception.RestException;
import toy.project.local_specialty.local_famous_goods.repository.AuthorityRepository;
import toy.project.local_specialty.local_famous_goods.repository.MemberRepository;
import toy.project.local_specialty.local_famous_goods.service.auth_service.AuthService;
import toy.project.local_specialty.local_famous_goods.service.member_service.MemberService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static toy.project.local_specialty.local_famous_goods.domain.auth.QSeller.*;
import static toy.project.local_specialty.local_famous_goods.domain.auth.QMember.*;
import static toy.project.local_specialty.local_famous_goods.domain.auth.QAuthority.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SaleMemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Qualifier(value = "sellerService")
    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthService authService;
    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;

    @Test
    @DisplayName(value = "회원 가입 테스트")
    @Transactional
    @Rollback(value = false)
    public void saveMemberTest() throws RestException {
        //give
        SellerSaveRequest sellerSaveRequest =
                SellerSaveRequest.builder()
                        .userId("TEST")
                        .city("TEST_CITY")
                        .companyCity("TEST_COMPANY_CITY")
                        .acountNumber("TEST_ACCOUNT")
                        .bankName("TEST_BANK_NAME")
                        .street("TEST_STREET")
                        .zipCode("TEST_ZIP_CODE")
                        .name("TEST_NAME")
                        .category("TEST_CATEGORY")
                        .companyStreet("TEST_STREET")
                        .companyZipCode("TEST_ZIP")
                        .password("TEST_123123")
                        .brandName("TEST_BRAND")
                        .build();
        //when
        Response<String> stringResponse = memberService.saveMember(sellerSaveRequest);
        Optional<Member> byUserId = memberRepository.findByUserId(stringResponse.getResponse());
        Optional<Authority> byMember_userId = authorityRepository.findByMember_UserId(stringResponse.getResponse());

        byUserId.ifPresent(member -> {
            Seller seller = (Seller) member;
            assertThat(seller.getBankName()).isEqualTo("TEST_BANK_NAME");
        });

        //then
        byUserId.ifPresent(member -> assertThat(member.getUserId()).isEqualTo("TEST"));
        byMember_userId.ifPresent(authority -> assertThat(authority.getMember()).isEqualTo(byUserId.get()));
        byMember_userId.ifPresent(authority -> assertThat(authority.getMember().getName()).isEqualTo(byUserId.get().getName()));
    }

    /**
     * 로그인 후에만 변경 가능하도록 설계
     *
     * @throws RestException
     */
    @Test
    @DisplayName(value = "비밀번호 변경 테스트")
    @Transactional
    public void memberModifyPassword() throws RestException {

        //give
        SellerSaveRequest sellerSaveRequest =
                SellerSaveRequest.builder()
                        .userId("TEST")
                        .city("TEST_CITY")
                        .companyCity("TEST_COMPANY_CITY")
                        .acountNumber("TEST_ACCOUNT")
                        .bankName("TEST_BANK_NAME")
                        .street("TEST_STREET")
                        .zipCode("TEST_ZIP_CODE")
                        .name("TEST_NAME")
                        .category("TEST_CATEGORY")
                        .companyStreet("TEST_STREET")
                        .companyZipCode("TEST_ZIP")
                        .password("TEST_123123")
                        .brandName("TEST_BRAND")
                        .build();
        Response<String> stringResponse = memberService.saveMember(sellerSaveRequest);
//        MemberPasswordModifyRequest param = new MemberPasswordModifyRequest("TEST" , "TEST_123123" , "TEST_12341234");
//
//        // when then
//        assertThrows(RestException.class , ()-> memberService.memberSelfModifyByPassword(param) );
    }


    @Test
    @DisplayName(value = "Query DSL 작동확인")
    public void QueryDslExecuteCheck() throws RestException {
        SellerSaveRequest sellerSaveRequest =
                SellerSaveRequest.builder()
                        .userId("TEST")
                        .city("TEST_CITY")
                        .companyCity("TEST_COMPANY_CITY")
                        .acountNumber("TEST_ACCOUNT")
                        .bankName("TEST_BANK_NAME")
                        .street("TEST_STREET")
                        .zipCode("TEST_ZIP_CODE")
                        .name("TEST_NAME")
                        .category("TEST_CATEGORY")
                        .companyStreet("TEST_STREET")
                        .companyZipCode("TEST_ZIP")
                        .password("TEST_123123")
                        .brandName("TEST_BRAND")
                        .build();
        Response<String> stringResponse = memberService.saveMember(sellerSaveRequest);
        String response = stringResponse.getResponse();

        queryFactory = new JPAQueryFactory(entityManager);

        Seller findSeller = queryFactory.select(seller)
                .from(seller)
                .where(seller.userId.eq(response))
                .fetchOne();

        assertThat(response).isEqualTo(findSeller.getUserId());
    }

    @Test
    @DisplayName(value = "멤버 리스트 조회")
    @Transactional
    public void memberList() throws RestException {
        queryFactory = new JPAQueryFactory(entityManager);
        /**
         * fetch() => 리스트 조회 없으면 빈 리스트
         * fetchOne() => 결과 없으면 널 , 둘 이상이면 널포인트 익셉션
         * fetchFirst() => 단건 조회 리밋 1
         * fetchResults() => 페이징 정보 , 카운트 쿼리 추가
         * fetchCount() =>  카운트 쿼리
         */
        for(int i = 0; i < 5 ; i++) {
            SellerSaveRequest sellerSaveRequest =
                    SellerSaveRequest.builder()
                            .userId("TEST" + i)
                            .city("TEST_CITY"  + i)
                            .companyCity("TEST_COMPANY_CITY"  + i)
                            .acountNumber("TEST_ACCOUNT"  + i)
                            .bankName("TEST_BANK_NAME"  + i)
                            .street("TEST_STREET"  + i)
                            .zipCode("TEST_ZIP_CODE"  + i)
                            .name("TEST_NAME"  + i)
                            .category("TEST_CATEGORY"  + i)
                            .companyStreet("TEST_STREET"  + i)
                            .companyZipCode("TEST_ZIP"  + i)
                            .password("TEST_123123"  + i)
                            .brandName("TEST_BRAND"  + i)
                            .build();
            Response<String> stringResponse = memberService.saveMember(sellerSaveRequest);
        }

//        List<Seller> fetch = queryFactory.selectFrom(seller).orderBy(seller.id.desc()).offset(1).limit(5).fetch();
//
//        for (Seller fetch1 : fetch) {
//            System.out.println("fetch1.getName() = " + fetch1.getName());
//        }

        List<Tuple> fetch = queryFactory.select(authority, member)
                .from(authority)
                .join(authority.member, member)
                .where(member.userId.eq("TEST1"))
                .fetch();

        for (Tuple authority : fetch) {
            Authority authority1 = authority.get(0, Authority.class);
            Seller member1 = (Seller) authority.get(1, Member.class);

            System.out.println("authority.getMember().getId() = " + member1.getAccountNumber());
            System.out.println("authority.getAuth().getPaid() = " + authority1.getAuth().getPaid());
        }
    }

    @Test
    public void instanceTEST () throws RestException {
        SellerSaveRequest sellerSaveRequest =
                SellerSaveRequest.builder()
                        .userId("TEST")
                        .city("TEST_CITY")
                        .companyCity("TEST_COMPANY_CITY")
                        .acountNumber("TEST_ACCOUNT")
                        .bankName("TEST_BANK_NAME")
                        .street("TEST_STREET")
                        .zipCode("TEST_ZIP_CODE")
                        .name("TEST_NAME")
                        .category("TEST_CATEGORY")
                        .companyStreet("TEST_STREET")
                        .companyZipCode("TEST_ZIP")
                        .password("TEST_123123")
                        .brandName("TEST_BRAND")
                        .build();
        //when
        Response<String> stringResponse = memberService.saveMember(sellerSaveRequest);
        Optional<Member> byUserId = memberRepository.findByUserId(stringResponse.getResponse());
        Optional<Authority> byMember_userId = authorityRepository.findByMember_UserId(stringResponse.getResponse());

        // 예를 들어서 이렇게 만든다고 가정하고,
        //    @Column(unique = true)
        //    private String userId;
        // 멤버의 userId 가 스트링일 때
        if(byUserId.isPresent()){
            if(byUserId.get().getUserId() instanceof String){
                System.out.println("스트링입니다.");
            } else { System.out.println("스트링이 아닙니다.");
            }
        }

        if(byUserId.isPresent()){
            if(byUserId.get().getUserId() != null){
                System.out.println("스트링입니다.");
            } else { System.out.println("스트링이 아닙니다.");
            }
        }

        class Members <T>{
            private T memberName;
        }

        Members members = new Members<>();
        if (members.memberName instanceof String){
            System.out.println("members = " + members);
        }
        // 이런형식으로 활용하기 도합니다.
    }

}