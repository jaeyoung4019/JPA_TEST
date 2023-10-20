package toy.project.local_specialty.local_famous_goods.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.jwt.header}")
    private String SPRING_JWT_HEADER;

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("local_famous_goods REST API")
                .description("rest REST API Documentation")
                .version("1.0")
                .build();
    }

    @Bean
    public Docket authSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("인증 API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("toy.project.local_specialty.local_famous_goods.controller.auth"))
                .paths(PathSelectors.ant("/auth/**"))
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket saveSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("save API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("toy.project.local_specialty.local_famous_goods.controller.member"))
                .paths(PathSelectors.ant("/save/**"))
                .build()
                .useDefaultResponseMessages(false);
    }

    @Bean
    public Docket testSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("test API")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("toy.project.local_specialty.local_famous_goods.controller"))
                .paths(PathSelectors.ant("/sale_member/**"))
                .build()
                .securitySchemes(List.of(apiKey()))
                .securityContexts(Collections.singletonList(adminSecurityContext()))
                .useDefaultResponseMessages(false);
    }

//    @Bean
//    public Docket authTestSwagger() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("권한인증 API")
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("toy.project.local_specialty.local_famous_goods.controller"))
//                .paths(PathSelectors.ant("/sale_member/**"))
//                .build()
//                .securitySchemes(List.of(apiKey()))
//                .securityContexts(Collections.singletonList(adminSecurityContext()))
//                .useDefaultResponseMessages(false);
//    }


    private ApiKey apiKey() {
        return new ApiKey("JWT", SPRING_JWT_HEADER, "header");
    }

    private SecurityContext adminSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.ant("/sale_member/**"))
                .build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = {authorizationScope};

        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

}
