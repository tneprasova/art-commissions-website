package cz.cvut.fit.tjv.art_commissions.client.api_client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private int code;
    private HttpStatus status;
    private String uri;
    private String timestamp;
    private String message;
    private String debugMessage;
}
