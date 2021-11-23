package com.example.databaseclientproblem;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataR2dbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class R2dbcEntityTemplateExampleRepositoryTest extends ExampleRepositoryTest {

    @Container
    @SuppressWarnings({"rawtypes"})
    private final static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:11");

    @DynamicPropertySource
    public static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.r2dbc.url", () -> postgresqlContainer.getJdbcUrl().replaceFirst("jdbc", "r2dbc"));
        registry.add("spring.r2dbc.username", postgresqlContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresqlContainer::getPassword);
    }

    @BeforeEach
    void setUp(
            @Autowired R2dbcEntityTemplate r2dbcEntityTemplate
    ) {
        exampleRepository = new R2dbcEntityTemplateExampleRepository(r2dbcEntityTemplate);
    }
}
