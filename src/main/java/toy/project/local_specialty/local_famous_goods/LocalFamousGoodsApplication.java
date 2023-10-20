package toy.project.local_specialty.local_famous_goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class LocalFamousGoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocalFamousGoodsApplication.class, args);
	}

}
