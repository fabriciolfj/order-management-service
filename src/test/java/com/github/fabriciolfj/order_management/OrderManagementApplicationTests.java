package com.github.fabriciolfj.order_management;

import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = OrderManagementApplication.class
)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Testcontainers
public class OrderManagementApplicationTests {

	private static final PostgreSQLContainer<?> postgresContainer;

	static {
		postgresContainer = new PostgreSQLContainer<>("postgres:16.1")
				.withDatabaseName("testdb")
				.withUsername("test")
				.withPassword("test")
				.withReuse(true);

		postgresContainer.start();
	}

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
		registry.add("spring.datasource.username", postgresContainer::getUsername);
		registry.add("spring.datasource.password", postgresContainer::getPassword);
		registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
		registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
		registry.add("spring.flyway.enabled", () -> "true");
		registry.add("spring.flyway.url", postgresContainer::getJdbcUrl);
		registry.add("spring.flyway.user", postgresContainer::getUsername);
		registry.add("spring.flyway.password", postgresContainer::getPassword);
		registry.add("spring.flyway.locations", () -> "classpath:db/migration");
	}

}
