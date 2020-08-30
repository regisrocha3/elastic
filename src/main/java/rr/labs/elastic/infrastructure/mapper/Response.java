package rr.labs.elastic.infrastructure.mapper;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message;
    private HttpStatus status;
    private String timestamp;
}
