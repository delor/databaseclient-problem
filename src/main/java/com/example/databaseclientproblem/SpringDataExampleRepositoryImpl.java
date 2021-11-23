package com.example.databaseclientproblem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SpringDataExampleRepositoryImpl implements ExampleRepository {

    SpringDataExampleRepository repository;

    @Override
    public Mono<Long> save(ExampleEntity example) {
        return repository.save(example).map(ExampleEntity::getId);
    }

    @Override
    public Mono<ExampleEntity> findById(long id) {
        return repository.findById(id);
    }
}
