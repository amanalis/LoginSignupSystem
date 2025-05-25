package com.example.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.loginsystem.data.MyDBHandler;
import com.example.loginsystem.databinding.ActivityLoginBinding;
import com.example.loginsystem.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    MyDBHandler myDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        myDBHandler = new MyDBHandler(this);
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPswd = binding.signupConfirm.getText().toString();

                if(email.equals("") || password.equals("") || confirmPswd.equals("")){
                    Toast.makeText(SignupActivity.this,"All fields are mandatory",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(password.equals(confirmPswd)){
                        Boolean checkUserEmail = myDBHandler.checkEmail(email);

                        if (checkUserEmail == false){
                            Boolean insert = myDBHandler.insertData(email,password);

                            if(insert == true){
                                Toast.makeText(SignupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignupActivity.this, "Email Already Exists", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(SignupActivity.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}