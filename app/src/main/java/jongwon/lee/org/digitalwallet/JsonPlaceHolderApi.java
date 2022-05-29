package jongwon.lee.org.digitalwallet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @GET("key")
    Call<List<Key>> getKeys(@Header("Authorization") String authHeader);

    @POST("auth/login")
    Call<Token> logInUser(@Body User user);

    @POST("auth/register")
    Call<Register> registerUser(@Body User user);
}
