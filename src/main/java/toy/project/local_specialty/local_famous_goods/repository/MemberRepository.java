package toy.project.local_specialty.local_famous_goods.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import toy.project.local_specialty.local_famous_goods.domain.auth.Member;
import toy.project.local_specialty.local_famous_goods.domain.auth.Seller;
import toy.project.local_specialty.local_famous_goods.repository.custom.CustomMemberRepository;

import java.time.LocalDateTime;
import java.util.Optional;


/**
 * 벌크연산 때릴 때는
 * update member set id = id + 1 where id >= 20 이런 업데이트 문을 때릴때
 * entityManager.clear();
 *
 *  @Modifying(clearAutomatically = true) 이거와 같음
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Long> , CustomMemberRepository {

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.lastLoginDate = :lastDate where m.id = :id")
    void updateLoginDate(@Param(value = "id") long id , @Param(value = "lastDate")LocalDateTime lastLoginDate);

    Optional<Member> findByUserId(String userId);
    //    @Modifying(clearAutomatically = true)
    //    @Query("update Member m set m.password = :password where m.userId = :userId")
    //    int memberSelfModifyByPassword(@Param(value = "userId") String userId, @Param(value = "password") String password);
    /**
     * 카운트에 조인문이 나가니까 리팩토링
     * @param id
     * @param pageable
     * @return
     * 암달의 법칙 , 조회용으로 쓰니까 리드온리 다넣어야지 해봐야 얼마 성능향상이 되지 않음.
     * 쿼리의 문제가 대부분임. 트래픽 많을 때만 하세요.
     * 힌트 튜닝은 마지막에 할것
     */
   // @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly" , value = "true")) 조회만을 위한 처리를 할 때 사용하면 성능 최적화가 됨. 변경감지 체크 안함
//    @Query(value = "select m from Member m where m.id > :id" , countQuery = "select count(m.id) from Member m where m.id > :id")
//    Page<Member> findByIdGreaterThan(@Param(value = "id") long id , Pageable pageable);


}
