package jongwon.lee.org.digitalwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PolestarActivity extends AppCompatActivity {

    // UI Elements
    private Button button_unlock;
    private Button button_trunk;
    private Button button_engine;
    private Button button_alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polestar);

        button_unlock = findViewById(R.id.button_unlock);
        button_trunk = findViewById(R.id.button_trunk);
        button_engine = findViewById(R.id.button_engine);
        button_alarm = findViewById(R.id.button_alarm);

        // register button click
        button_unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PolestarActivity.this,"Car unlocked.", Toast.LENGTH_SHORT).show();
            }
        });

        button_trunk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PolestarActivity.this,"Trunk opened.", Toast.LENGTH_SHORT).show();
            }
        });

        button_engine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PolestarActivity.this,"Engine started.", Toast.LENGTH_SHORT).show();
            }
        });

        button_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PolestarActivity.this,"Alarm on.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}