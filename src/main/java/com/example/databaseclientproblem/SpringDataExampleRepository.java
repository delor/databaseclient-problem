package com.example.databaseclientproblem;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SpringDataExampleRepository extends ReactiveCrudRepository<ExampleEntity, Long> {
}
