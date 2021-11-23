package com.example.databaseclientproblem;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Map;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class JdbcExampleRepository implements ExampleRepository {

    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Mono<Long> save(ExampleEntity example) {
        return Mono.fromSupplier(() -> {
            var sql = "insert into example (created_at) values (:createdAt)";
            var parameterSource = new MapSqlParameterSource("createdAt", Timestamp.from(example.getCreatedAt()));
            var generatedKeyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql, parameterSource, generatedKeyHolder);
            return (Long) generatedKeyHolder.getKeys().get("id");
        });
    }

    @Override
    public Mono<ExampleEntity> findById(long id) {
        return Mono.fromSupplier(() -> {
            var sql = "select * from example where id = :id";
            var paramMap = Map.of("id", id);
            var rowMapper = (RowMapper<ExampleEntity>) (rs, rowNum) -> new ExampleEntity(
                    rs.getLong("id"),
                    rs.getTimestamp("created_at").toInstant());
            return namedParameterJdbcTemplate.queryForObject(sql, paramMap, rowMapper);
        });
    }
}
