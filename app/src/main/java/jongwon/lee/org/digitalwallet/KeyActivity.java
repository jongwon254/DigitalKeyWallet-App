package jongwon.lee.org.digitalwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class KeyActivity extends AppCompatActivity {

    // UI Elements
    private Button button_bmw_big;
    private Button button_bmw_small;
    private Button button_polestar_small;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key);

        button_bmw_big = findViewById(R.id.button_bmw_big);
        button_bmw_small = findViewById(R.id.button_bmw_small);
        button_polestar_small = findViewById(R.id.button_polestar_small);

        // register button click
        button_bmw_big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        button_bmw_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        button_polestar_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}