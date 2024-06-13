package dev.kropotov.study.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public final class ExpirationValue {
    private Object value;

    @Setter
    private LocalDateTime expiration;

}
