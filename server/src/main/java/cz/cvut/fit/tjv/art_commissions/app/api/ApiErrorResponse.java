package cz.cvut.fit.tjv.art_commissions.app.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiErrorResponse {
    private int code;
    private HttpStatus status;
    private String uri;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    public ApiErrorResponse(HttpStatus status, String message, WebRequest webRequest, Throwable ex) {
        this.timestamp = LocalDateTime.now();
        this.code = status.value();
        this.status = status;
        this.uri = ((ServletWebRequest) webRequest).getRequest().getRequestURI();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }
}
