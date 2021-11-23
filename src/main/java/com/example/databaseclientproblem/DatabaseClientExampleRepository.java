package com.example.databaseclientproblem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class DatabaseClientExampleRepository implements ExampleRepository {

    DatabaseClient databaseClient;

    @Override
    public Mono<Long> save(ExampleEntity example) {
        return databaseClient.sql("insert into example (created_at) values (:created_at)")
                .filter((statement, executionFunction) -> statement.returnGeneratedValues("id").execute())
                .bind("created_at", example.getCreatedAt())
                .fetch()
                .first()
                .map(r -> (Long) r.get("id"));
    }

    @Override
    public Mono<ExampleEntity> findById(long id) {
        return databaseClient.sql("select id, created_at from example where id = :id")
                .bind("id", id)
                .map(row -> new ExampleEntity(
                        row.get("id", Long.class),
                        row.get("created_at", Instant.class)))
                .one();
    }
}
