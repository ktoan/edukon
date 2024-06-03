package com.java.backend;

import com.cloudinary.Cloudinary;
import com.paypal.base.rest.APIContext;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SpringBootApplication
public class BackendApplication {
	@Value("${app.cloudinary.cloud-name}")
	private String cloudinaryCloudName;
	@Value("${app.cloudinary.api-key}")
	private String cloudinaryApiKey;
	@Value("${app.cloudinary.api-secret}")
	private String cloudinaryApiSecret;
	@Value("${paypal.mode}")
	private String paypalMode;
	@Value("${paypal.client-id}")
	private String paypalClientId;
	@Value("${paypal.client-secret}")
	private String paypalClientSecret;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public Cloudinary cloudinary() {
		Map<String, String> config = new HashMap<>();
		config.put("cloud_name", cloudinaryCloudName);
		config.put("api_key", cloudinaryApiKey);
		config.put("api_secret", cloudinaryApiSecret);
		return new Cloudinary(config);
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setDefaultLocale(Locale.ENGLISH);
		messageSource.setCacheSeconds(3600);
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("EduKon API")
                        .version("1.0.0")
                        .description("EduKon API is the backend service powering an educational website. It provides endpoints for managing educational resources, courses, categories, and blogs. Users can access a wealth of educational content, enroll in courses, and stay updated with the latest educational blog posts. This API follows the REST architectural style and adheres to the OpenAPI Specification.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Swagger")
                                .url("http://swagger.io"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
	}

	@Bean
	public APIContext apiContext() {
		return new APIContext(paypalClientId, paypalClientSecret, paypalMode);
	}
}
