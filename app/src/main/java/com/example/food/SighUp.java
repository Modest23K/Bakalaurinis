package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SighUp extends AppCompatActivity {
    TextInputEditText textInputEditTextfullname, textInputEditusername ,   textInputEditemail, textInputEditpassword;
    Button btnregister, btnlogin;
    ProgressBar progressBar;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 6 characters
                    "$");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigh_up);

        textInputEditTextfullname = findViewById(R.id.fullname);
        textInputEditemail = findViewById(R.id.email);
        textInputEditusername = findViewById(R.id.username);
        textInputEditpassword = findViewById(R.id.password);
        btnregister =findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        progressBar = findViewById(R.id.progressBar);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, email, username, password;
                fullname = String.valueOf(textInputEditTextfullname.getText());
                email = String.valueOf(textInputEditemail.getText());
                username = String.valueOf(textInputEditusername.getText());
                password = String.valueOf(textInputEditpassword.getText());



               if (fullname.isEmpty()&&!email.equals("")&&!username.equals("")&&!password.equals("")){
                    Toast.makeText(getApplicationContext(),"Name field is missing",Toast.LENGTH_SHORT).show();
                }
                if (!fullname.equals("")&&email.isEmpty()&&!username.equals("")&&!password.equals("")){
                    Toast.makeText(getApplicationContext(),"Email field is missing",Toast.LENGTH_SHORT).show();
                }
                if (!fullname.equals("")&&!email.equals("")&&!password.equals("")&&username.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Username is missing",Toast.LENGTH_SHORT).show();
                }
                if (!fullname.equals("")&&!email.equals("")&&password.isEmpty()&&!username.equals("")){
                    Toast.makeText(getApplicationContext(),"Password is missing",Toast.LENGTH_SHORT).show();
                }
                if (!PASSWORD_PATTERN.matcher(password).matches()) {
                    Toast.makeText(getApplicationContext(),"Password is weak",Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"Slaptažodis turi turėti 6 simbolius, be jokių tarpų, ir su vienu specialiu simboliu",Toast.LENGTH_SHORT).show();
                }
             try {
                    if(!fullname.equals("")&&!email.equals("")&&!username.equals("")&&!password.equals("")){
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Starting Write and Read data with URL
                                //Creating array for parameters
                                String[] field = new String[4];
                                field[0] = "fullname";
                                field[1] = "email";
                                field[2] = "username";
                                field[3] = "password";
                                //Creating array for data
                                String[] data = new String[4];
                                data[0] = fullname;
                                data[1] = email;
                                data[2] = username;
                                data[3] = password;
                                PutData putData = new PutData("http://192.168.240.95/LoginRegister/signup.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if(result.equals("Sign Up Success")){
                                            Toast.makeText(getApplicationContext(),"Sign Up Success",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),Login.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Sign Up not successful",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                //End Write and Read data with URL
                            }
                        });
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"All fields are nessesary", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}