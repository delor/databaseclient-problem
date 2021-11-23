package com.example.databaseclientproblem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import reactor.core.publisher.Mono;

import static org.springframework.data.relational.core.query.Criteria.where;
import static org.springframework.data.relational.core.query.Query.query;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class R2dbcEntityTemplateExampleRepository implements ExampleRepository {

    R2dbcEntityTemplate template;

    @Override
    public Mono<Long> save(
            ExampleEntity example
    ) {
        return template.insert(ExampleEntity.class)
                .into("example")
                .using(example)
                .map(ExampleEntity::getId);
    }

    @Override
    public Mono<ExampleEntity> findById(
            long id
    ) {
        return template.select(ExampleEntity.class)
                .from("example")
                .matching(query(where("id").is(id)))
                .one();
    }
}
