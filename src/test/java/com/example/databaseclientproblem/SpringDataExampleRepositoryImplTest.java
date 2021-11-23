package com.example.databaseclientproblem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Disabled("for some reason SpringDataExampleRepository is not initialized by spring")
@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringDataExampleRepositoryImplTest extends ExampleRepositoryTest {

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
            @Autowired SpringDataExampleRepository repository
    ) {
        exampleRepository = new SpringDataExampleRepositoryImpl(repository);
    }
}
