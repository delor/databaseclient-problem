package com.example.databaseclientproblem;

import reactor.core.publisher.Mono;

public interface ExampleRepository {

    Mono<Long> save(ExampleEntity example);

    Mono<ExampleEntity> findById(long id);
}
