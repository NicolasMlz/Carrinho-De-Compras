package me.dio.carrinho.configuration;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
	@Bean
	public Docket getBean() {
		return new Docket(DocumentationType.SWAGGER_2).select()
         .paths(PathSelectors.regex("/ifood/.*"))
         .build()
         .apiInfo(getInfo());
	}

	private ApiInfo getInfo() {
          return new ApiInfoBuilder()
                  .title("Sacola API")
                  .description("Sacola API para Servir uma Aplicação de Delivery")
                  .build();
  }
}