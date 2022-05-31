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

import java.io.IOException;
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
                if (email.isEmpty()) {
                    text_email.setError("Please enter your email.");
                    text_email.requestFocus();
                } else if (password.isEmpty()) {
                    text_password.setError("Please enter your password.");
                    text_password.requestFocus();
                } else if (email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your information!", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    // correct input
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.0.2.2:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    // login user on separate thread
                    String token = logInUser(email, password);

                    // get keys with authentication token
                    if(!token.isEmpty()) {
                        getKeys(token);

                        // go to keys screen
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, KeyActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login not successful", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    private String logInUser(String email, String password) {
        User user = new User(email, password);
        final String[] token = new String[1];

        Call<Token> call = jsonPlaceHolderApi.logInUser(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Token> response = call.execute();

                    if (!response.isSuccessful()) {
                        System.out.println("Code: " + response.code());
                        Toast.makeText(LoginActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    token[0] = response.body().getToken();

                    System.out.println("method token: " + token[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                /*call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(!response.isSuccessful()) {
                            System.out.println("Code: " + response.code());
                            Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        token[0] = response.body().getToken();

                        System.out.println("method token: " + token[0]);
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                        System.out.println(t.getMessage());
                    }
                });
            }*/
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token[0];
    }

    private void getKeys(String token) {
        Call<List<Key>> call = jsonPlaceHolderApi.getKeys("Bearer " + token);

        System.out.println("Get keys token: " + token);

        call.enqueue(new Callback<List<Key>>() {
            @Override
            public void onResponse(Call<List<Key>> call, Response<List<Key>> response) {
                if(!response.isSuccessful()) {
                    System.out.println("Get Keys Code: " + response.code());
                    Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                    return;
                }

                System.out.println(response.body().get(0).getCarModel());
                System.out.println(response.body().get(1).getCarModel());

                /*List<Key> keys = response.body();
                StringBuilder content = new StringBuilder();

                for(Key key : keys) {
                    content.append("ID: " + key.getId() + "\n");
                    content.append("First Name: " + key.getFirstName() + "\n");
                    content.append("Last Name: " + key.getLastName() + "\n");
                    content.append("Car Brand: " + key.getCarBrand() + "\n");
                    content.append("Car Model: " + key.getCarModel() + "\n\n");
                }

                System.out.println(content.toString());*/
            }

            @Override
            public void onFailure(Call<List<Key>> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Error Occurred!", Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }
}
