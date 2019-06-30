package jhchv.searchplace;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SearchPlaceApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SearchPlaceApplication.class, args);
        new SpringApplicationBuilder(SearchPlaceApplication.class)
                .beanNameGenerator((beanDefinition, beanDefinitionRegistry) -> beanDefinition.getBeanClassName())
                .run(args);
    }

}
