package com.example.moscowsightsguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(v -> {
            User user = new User(
                    ((EditText) findViewById(R.id.surname)).getText().toString(),
                    ((EditText) findViewById(R.id.name)).getText().toString(),
                    ((EditText) findViewById(R.id.email)).getText().toString()
            );

            Intent intent = new Intent(LoginActivity.this, SightsListActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        });
    }
}