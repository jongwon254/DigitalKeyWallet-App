package jongwon.lee.org.digitalwallet;

import androidx.appcompat.app.AppCompatActivity;
import jongwon.lee.org.digitalwallet.entity.Key;
import jongwon.lee.org.digitalwallet.entity.Token;
import jongwon.lee.org.digitalwallet.entity.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    // UI Elements
    private EditText text_email;
    private EditText text_password;

    private Button button_login;
    private Button button_register;

    // API Connection
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Token token;
    private boolean successfulConnection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        text_email = findViewById(R.id.text_email);
        text_password = findViewById(R.id.text_password);

        button_login = findViewById(R.id.button_login);
        button_register = findViewById(R.id.button_register);

        // register button click
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        // login button click
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = text_email.getText().toString();
                String password = text_password.getText().toString();

                // input check
                if(email.isEmpty()) {
                    text_email.setError("Please enter your email.");
                    text_email.requestFocus();
                } else if(password.isEmpty()) {
                    text_password.setError("Please enter your password.");
                    text_password.requestFocus();
                } else if(email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your information!", Toast.LENGTH_SHORT).show();
                } else if(!(email.isEmpty() && password.isEmpty())) {
                    // correct input
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    // login user
                    logInUser(email, password);

                    // get keys with authentication token
                    if(successfulConnection) {
                        getKeys(token.getToken());

                        // go to keys screen
                        Toast.makeText(LoginActivity.this,"Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, KeyActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void logInUser(String email, String password) {
        User user = new User(email, password);

        Call<Token> call = jsonPlaceHolderApi.logInUser(user);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    successfulConnection = false;
                    return;
                }

                token = response.body();

                System.out.println(token.getToken());
                successfulConnection = true;
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                System.out.println(t.getMessage());
                successfulConnection = false;
            }
        });
    }

    private void getKeys(String token) {
        Call<List<Key>> call = jsonPlaceHolderApi.getKeys("Bearer " + token);

        call.enqueue(new Callback<List<Key>>() {
            @Override
            public void onResponse(Call<List<Key>> call, Response<List<Key>> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Code: " + response.code());
                    return;
                }

                List<Key> keys = response.body();
                StringBuilder content = new StringBuilder();

                for(Key key : keys) {
                    content.append("ID: " + key.getId() + "\n");
                    content.append("First Name: " + key.getFirstName() + "\n");
                    content.append("Last Name: " + key.getLastName() + "\n");
                    content.append("Car Brand: " + key.getCarBrand() + "\n");
                    content.append("Car Model: " + key.getCarModel() + "\n\n");
                }

                System.out.println(content.toString());
            }

            @Override
            public void onFailure(Call<List<Key>> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }
}