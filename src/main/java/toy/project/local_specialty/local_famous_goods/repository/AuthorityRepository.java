package toy.project.local_specialty.local_famous_goods.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import toy.project.local_specialty.local_famous_goods.domain.auth.Authority;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority , Long> {
    @EntityGraph(attributePaths = { "member" })
    Optional<Authority> findByMember_UserId(String userName);

    @EntityGraph(attributePaths = { "member" })
    Optional<Authority> findById(Long id);
}
