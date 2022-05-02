package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    TextInputEditText  textInputEditusername , textInputEditpassword;
    Button btnregister, btnlogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditusername = findViewById(R.id.usernamelogin);
        textInputEditpassword = findViewById(R.id.passwordlogin);
        btnregister =findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        progressBar = findViewById(R.id.progressBar2);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  username, password;

                username = String.valueOf(textInputEditusername.getText());
                password = String.valueOf(textInputEditpassword.getText());

                if(!username.equals("")&&!password.equals("")){
                    try {
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[2];
                                field[0] = "username";
                                field[1] = "password";
                                //Creating array for data
                                String[] data = new String[2];
                                data[0] = username;
                                data[1] = password;
                                PutData putData = new PutData("http://192.168.240.95/LoginRegister/login.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if(result.equals("Login Success")){
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"All fields are required", Toast.LENGTH_SHORT).show();
                    }
                }
                    }

        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SighUp.class);
                startActivity(intent);
                finish();
            }
        });
    }


}