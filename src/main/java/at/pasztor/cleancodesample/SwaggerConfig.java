package at.pasztor.cleancodesample;

import com.google.common.base.Predicates;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@PropertySource("classpath:swagger.properties")
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                    .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build()
                .apiInfo(metadata())
                .forCodeGeneration(true)
                .tags(
                        new Tag(
                                "Users",
                                "Create, verify, list, retreive or delete users"
                        )
                )
                .useDefaultResponseMessages(false)
                ;
    }

    public static ApiInfo metadata(){
        return new ApiInfoBuilder()
                .title("Sample Clean Code App API")
                .description(
                    "The API can be used by any standard HTTP client. It should submit data in the JSON " +
                        "format and the response will also be in the JSON format. It is recommended that the " +
                        "`Accept` header should be set to the value of `application/json` for forward compatibility, " +
                        "as well as the `Content-Type` header for any requests with a body."
                )
                .version(at.pasztor.cleancodesample.SwaggerConfig.class.getPackage().getImplementationVersion())
                .build();
    }
}
