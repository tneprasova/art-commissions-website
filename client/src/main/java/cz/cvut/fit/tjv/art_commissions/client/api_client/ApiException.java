package cz.cvut.fit.tjv.art_commissions.client.api_client;

public class ApiException extends RuntimeException{
    public ApiErrorResponse response;

    public ApiException(ApiErrorResponse response) {
        this.response = response;
    }
}
