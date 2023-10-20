//package toy.project.local_specialty.local_famous_goods.service;
//
//import org.assertj.core.api.Assertions;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import toy.project.local_specialty.local_famous_goods.domain.auth.Member;
//import toy.project.local_specialty.local_famous_goods.dto.member.save.SellerSaveRequest;
//import toy.project.local_specialty.local_famous_goods.exception.RestException;
//import toy.project.local_specialty.local_famous_goods.repository.MemberRepository;
//import toy.project.local_specialty.local_famous_goods.service.member_service.MemberService;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//public class PagingTest {
//
//    @Qualifier("sellerService")
//    @Autowired
//    private MemberService memberService;
//    @Autowired
//    private MemberRepository memberRepository;
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Test
//    @Rollback(value = false)
//    public void paingTest() throws RestException {
//
//        /**
//         * 여기서 빌더를 써야하는 이유중 하나인 생성자 매개변수가 많을 때 어디에 무슨 값이 들어가는지 모르는 상황이 있습니다
//         * 그래서 인텔리 제이의 경우에는 커맨드 + P 를 눌러서 찾아가며 넣게 됩니다.
//         *
//         */
//        for (int i = 0; i < 200; i++) {
//            SellerSaveRequest sellerSaveRequest =
//                    new SellerSaveRequest("memberTest123" + i, "member@@1121" + i, "테스트멤버" + i, "코스모" + i,
//                            "신한은행", "3414123123", "안양", "관양동", "1429-31",
//                            "안양", "관양동", "1429-31", "카테고리");
//            memberService.saveMember(sellerSaveRequest);
//        }
//
//        /**
//         *  검색조건 멤버 인덱스 20 이상 중
//         *  정렬조건 인덱스 이름 오름차순
//         *  페이징 조건 -> 페이지당 보여줘야할 데이터 는 20건이고
//         *  3페이지에 있는 사람을 보고 싶다.
//         *
//         *  우선 오프셋에 대해 말하자면,
//         *  1 page -> 0 이 할당되고 0부터 시작해서 limit (가져올 갯수) 20개를 가져오니까 0 ~ 19 까지
//         *  2 page -> 20 이 할당되고 20부터 시작해서 limit 20개를 가져오니까 20~ 39 까지
//         *  이런 형식으로 적용 됩니다.
//         *  그래서 현제 피이지를 뷰에서 레스트로 꼭 보내야해요.
//         *
//         */
//        final long idx = 20;
//        final int limit = 20;
//        final int offset = 3 * limit; // 이부분은 항상 변경이 가능한 부분이라, 원하는 데이터 에 따라 조정하면 됩니다.
//        List<Member> byPage = findByPage(idx, offset, limit);
//        for (Member member : byPage) {
//            System.out.println("member.getId() = " + member.getId());
//            /**
//             * 결과 값
//             member.getId() = 81
//             member.getId() = 82
//             member.getId() = 83
//             member.getId() = 84
//             member.getId() = 85
//             member.getId() = 86
//             member.getId() = 87
//             member.getId() = 88
//             member.getId() = 89
//             member.getId() = 90
//             member.getId() = 91
//             member.getId() = 92
//             member.getId() = 93
//             member.getId() = 94
//             member.getId() = 95
//             member.getId() = 96
//             member.getId() = 97
//             member.getId() = 98
//             member.getId() = 99
//             member.getId() = 100
//             * 결과 값
//             * count = 181
//             * 여기서 토탈카운트를 넘기는 이유는 스크립트단에서 써야하기 때문인데, 예를 들어 리엑트에서 페이징처리를 직접한다면,
//             *   <div className="pc-only">
//             *
//             *                         <div className="pagination" >
//             *                             <a onClick={() => setPage( 1) }  style={ page === 1 ? {display : "none"} : {display : "inline-block"}} title="처음" className="btn-arrow first"><span className="blind">처음</span><i
//             *                                 className="ri-arrow-left-s-line"></i><i className="ri-arrow-left-s-line"></i></a>
//             *                             <a onClick={() => setPage(page - 1)} style={ page === 1 ? {display : "none"} : {display : "inline-block"}} title="이전" className="btn-arrow prev"><span className="blind">이전</span><i
//             *                                 className="ri-arrow-left-s-line"></i></a>
//             *                             {
//             *                                     pageNumber.map(function ( a , i) {
//             *                                         return (
//             *                                                 <a
//             *                                                     key={a}
//             *                                                     onClick={() => setPage(a)}
//             *                                                     className={page === a ? "active" : ""}
//             *                                                 >
//             *                                                     {a}
//             *                                                 </a>
//             *
//             *                                         )
//             *                                     })
//             *                             }
//             *                             <a onClick={() => setPage(page + 1)} style={ page === numPages + 1? {display : "none"} : {display : "inline-block"}} title="다음" className="btn-arrow next"><span className="blind">다음</span><i
//             *                                 className="ri-arrow-right-s-line"></i></a>
//             *                             <a onClick={() => setPage( Number(Math.floor(total/10) ) + 1)} style={ page === numPages  + 1 ? {display : "none"} : {display : "inline-block"}}  title="끝" className="btn-arrow end"><span className="blind">끝</span><i
//             *                                 className="ri-arrow-right-s-line"></i><i className="ri-arrow-right-s-line"></i></a>
//             *                         </div>
//             *
//             *                     </div>
//             *
//             *                     이런식으로 페이지를 눌렀을 때 값을 변경해서 ajax로 페이지 를 찍어주면 됩니다.
//             *                     Ajax 요청은
//             *   const getList = async () => {
//             *         // Promise.all([ ])
//             *         Promise.all([
//             *             axios.get(process.env.REACT_APP_REST_URL+'/user/notice/list',
//             *                 {
//             *                     params: {
//             *                         page: page,
//             *                         category: category,
//             *                         keyword: keyword
//             *                     }
//             *                 }
//             *             )
//             *         ]).then((data) => {
//             *             setPosts(data[0].data.response);
//             *             setTotal(data[0].data.total);
//             *             setNumPages(Number(data[0].data.message));
//             *             let arr = [];
//             *             for (let i = ( page % 10 ) === 0 ? (( Math.floor(Number(  page/10 )) * 10 ) - 9 ) : ( Math.floor(Number(  page/10 )) * 10 ) + 1 ; i <= ( (( page % 10 ) === 0 ? (( Math.floor(Number(  page/10 )) * 10 ) - 9 ) : ( Math.floor(Number(  page/10 )) * 10 ) + 1) + 9 ) ; i++) {
//             *                 if(i <= (data[0].data.total/10) + 1) {
//             *                     arr.push(i);
//             *                 }
//             *             }
//             *
//             *             let copy = [...arr];
//             *             setPageNumber(copy)
//             *
//             *         })
//             *
//             *     }
//             *     이런식으로 처리합니다 공식은 보통 다 정해져 있어서 검색하시거나, 해서 하나씩 찍어보는수밖ㅇ..
//             */
//        }
//
//        long count = count(idx);
//        System.out.println("count = " + count);
//
//
//        // 여기까지가 순수 JPA 로 구현 할 때 입니다.
//
//        // Spring data 에서 지원하는 Paging
//
//        // Spring data 에서 제공하는 파라미터인 Sort , Pageable 과 반환타입 Page (카운트 쿼리 결과를 포함할 때) , Slice (다 가져가서 웹에서 페이징 할 때)  가 중요하다.
//        //  Page<Member> findByIdLessThan(long id , Pageable pageable);
//        PageRequest pageRequest = PageRequest.of(3, limit, Sort.by(Sort.Direction.ASC, "id" , "name" ));
//        Page<Member> byIdLessThan = memberRepository.findByIdGreaterThan(idx, pageRequest);
//        long totalElements = byIdLessThan.getTotalElements();
//        List<Member> content = byIdLessThan.getContent();
//        System.out.println("============== SPRING DATA JPA ===============");
//        for (Member member : content) {
//            System.out.println("==== SPRING DATA JPA ===== member.getId() = " + member.getId());
//        }
//        /**
//         * 결과 값 및 쿼리문
//         *
//         *   select
//         *         member0_.member_idx as member_i2_5_,
//         *         member0_.created_date as created_3_5_,
//         *         member0_.modified_date as modified4_5_,
//         *         member0_.city as city5_5_,
//         *         member0_.street as street6_5_,
//         *         member0_.zip_code as zip_code7_5_,
//         *         member0_.last_login_date as last_log8_5_,
//         *         member0_.name as name9_5_,
//         *         member0_.password as passwor10_5_,
//         *         member0_.user_id as user_id11_5_,
//         *         member0_2_.account_number as account_1_6_,
//         *         member0_2_.bank_name as bank_nam2_6_,
//         *         member0_2_.brand_name as brand_na3_6_,
//         *         member0_2_.category as category4_6_,
//         *         member0_2_.company_city as company_5_6_,
//         *         member0_2_.company_street as company_6_6_,
//         *         member0_2_.company_zip_code as company_7_6_,
//         *         member0_.member_type as member_t1_5_
//         *     from
//         *         member member0_
//         *     left outer join
//         *         buyer member0_1_
//         *             on member0_.member_idx=member0_1_.buyer_id
//         *     left outer join
//         *         seller member0_2_
//         *             on member0_.member_idx=member0_2_.seller_id
//         *     where
//         *         member0_.member_idx>?
//         *     order by
//         *         member0_.member_idx asc,
//         *         member0_.name asc limit ? ,  ?
//         *
//         *             select
//         *         member0_.member_idx as member_i2_5_,
//         *         member0_.created_date as created_3_5_,
//         *         member0_.modified_date as modified4_5_,
//         *         member0_.city as city5_5_,
//         *         member0_.street as street6_5_,
//         *         member0_.zip_code as zip_code7_5_,
//         *         member0_.last_login_date as last_log8_5_,
//         *         member0_.name as name9_5_,
//         *         member0_.password as passwor10_5_,
//         *         member0_.user_id as user_id11_5_,
//         *         member0_2_.account_number as account_1_6_,
//         *         member0_2_.bank_name as bank_nam2_6_,
//         *         member0_2_.brand_name as brand_na3_6_,
//         *         member0_2_.category as category4_6_,
//         *         member0_2_.company_city as company_5_6_,
//         *         member0_2_.company_street as company_6_6_,
//         *         member0_2_.company_zip_code as company_7_6_,
//         *         member0_.member_type as member_t1_5_
//         *     from
//         *         member member0_
//         *     left outer join
//         *         buyer member0_1_
//         *             on member0_.member_idx=member0_1_.buyer_id
//         *     left outer join
//         *         seller member0_2_
//         *             on member0_.member_idx=member0_2_.seller_id
//         *     where
//         *         member0_.member_idx>?
//         *     order by
//         *         member0_.member_idx asc,
//         *         member0_.name asc limit ? , ?
//         */
//
//        // 이렇게 만들고 돌려보면 되겠죠
//       for(int i = 0; i < 20 ; i++){
//           Assertions.assertThat(content.get(i).getId()).isEqualTo(byPage.get(i).getId());
//       }
//
//       // 변환해서 리턴
//        // byIdLessThan.map( member -> MemberListResponse,createMemberList(member));
//
//
//    }
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    // 순수 JPA 구현하면,
//    /**
//     * 패아장 처리시 순수 JPA 로 구성할 경우에,
//     */
//    public List<Member> findByPage(long idx , int offset , int limit){
//
//        return  entityManager.createQuery("select m from Member m where m.id > :idx order by m.id , m.name asc", Member.class)
//                .setParameter("idx", idx)
//                .setFirstResult(offset)  // 어디서 시작할 것인가
//                .setMaxResults(limit) // 몇개를 가져올 것인가.
//                .getResultList();
//        /**
//         * 기본 쿼리문은 그러면 ,
//         *     select
//         *         member0_.member_idx as member_i2_5_,
//         *         member0_.created_date as created_3_5_,
//         *         member0_.modified_date as modified4_5_,
//         *         member0_.city as city5_5_,
//         *         member0_.street as street6_5_,
//         *         member0_.zip_code as zip_code7_5_,
//         *         member0_.last_login_date as last_log8_5_,
//         *         member0_.name as name9_5_,
//         *         member0_.password as passwor10_5_,
//         *         member0_.user_id as user_id11_5_,
//         *         member0_2_.account_number as account_1_6_,
//         *         member0_2_.bank_name as bank_nam2_6_,
//         *         member0_2_.brand_name as brand_na3_6_,
//         *         member0_2_.category as category4_6_,
//         *         member0_2_.company_city as company_5_6_,
//         *         member0_2_.company_street as company_6_6_,
//         *         member0_2_.company_zip_code as company_7_6_,
//         *         member0_.member_type as member_t1_5_
//         *     from
//         *         member member0_
//         *     left outer join
//         *         buyer member0_1_
//         *             on member0_.member_idx=member0_1_.buyer_id
//         *     left outer join
//         *         seller member0_2_
//         *             on member0_.member_idx=member0_2_.seller_id
//         *     where
//         *         member0_.member_idx > 20
//         *     order by
//         *         member0_.name limit 81 , 20,
//         */
//    }
//
//    public long count(long idx){
//        return  entityManager.createQuery("select count(m) from Member m where m.id >= :idx" , Long.class)
//                .setParameter("idx", idx)
//                .getSingleResult();
//    }
//
//    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//}
