package jongwon.lee.org.digitalwallet;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getKeys();
        //logInUser();
        registerUser();
    }

    private void getKeys() {
        String authToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MkB0ZXN0LmNvbSIsImlhdCI6MTY1Mzg0MDg0MywiZXhwIjoxNjU0NDQ1NjQzfQ.vwQqXaRPDogHf1DpTtyNoABLIO6msCl6iyv_lNnBHdg";

        Call<List<Key>> call = jsonPlaceHolderApi.getKeys("Bearer " + authToken);

        call.enqueue(new Callback<List<Key>>() {
            @Override
            public void onResponse(Call<List<Key>> call, Response<List<Key>> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                List<Key> keys = response.body();

                for(Key key : keys) {
                    String content = "";
                    content += "ID: " + key.getId() + "\n";
                    content += "First Name: " + key.getFirstName() + "\n";
                    content += "Last Name: " + key.getLastName() + "\n";
                    content += "Car Brand: " + key.getCarBrand() + "\n";
                    content += "Car Model: " + key.getCarModel() + "\n\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Key>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void logInUser() {
        User user = new User("test2@test.com", "1234");

        Call<Token> call = jsonPlaceHolderApi.logInUser(user);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                Token token = response.body();

                textViewResult.setText(token.getToken());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void registerUser() {
        User user = new User("test7@test.com", "1234");

        Call<Register> call = jsonPlaceHolderApi.registerUser(user);

        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(!response.isSuccessful()) {
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                textViewResult.setText("Code :" + response.code() + "\n" + response.body().getEmail());
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}