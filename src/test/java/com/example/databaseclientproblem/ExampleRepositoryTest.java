package com.example.databaseclientproblem;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class ExampleRepositoryTest {

    protected ExampleRepository exampleRepository;

    @Test
    public void test_save_and_fetch() {
        ExampleEntity newEntity = new ExampleEntity();

        Long id = exampleRepository.save(newEntity).block();
        ExampleEntity dbEntity = exampleRepository.findById(id).block();

        assertThat(dbEntity).isNotNull();
        assertThat(dbEntity.getCreatedAt()).isEqualTo(newEntity.getCreatedAt());
    }
}
