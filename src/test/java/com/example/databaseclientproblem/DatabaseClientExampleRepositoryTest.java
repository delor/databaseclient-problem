package com.example.databaseclientproblem;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataR2dbcTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseClientExampleRepositoryTest extends ExampleRepositoryTest {

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
            @Autowired DatabaseClient databaseClient
    ) {
        exampleRepository = new DatabaseClientExampleRepository(databaseClient);
    }
}
