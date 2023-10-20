package toy.project.local_specialty.local_famous_goods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import toy.project.local_specialty.local_famous_goods.dto.authority.MemberDetails;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class JpaAuditorAwareConfig {

    @Bean
    public AuditorAware<String> loginAuditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null || authentication.getPrincipal() instanceof String){
                return Optional.of("비 로그인 사용자");
            }

            MemberDetails principal = (MemberDetails) authentication.getPrincipal();
            return Optional.of(principal.getUsername());
        };
    }
}
