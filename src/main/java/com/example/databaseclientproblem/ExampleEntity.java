package com.example.databaseclientproblem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Data
//@Table("example")
@NoArgsConstructor
@AllArgsConstructor
public class ExampleEntity {

    @Id
    Long id;

    Instant createdAt = Instant.now().truncatedTo(ChronoUnit.MILLIS);
}
