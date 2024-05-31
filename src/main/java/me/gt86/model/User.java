package me.gt86.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Nullable
    private String username;
    @Nullable
    private String rawid;
    @Nullable
    private String uuid;
    @Nullable
    private String error;
}
