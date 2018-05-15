package com.vivekapps.avtodock_new;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vivekapps.utils.LoginServices;
import com.vivekapps.utils.RetrofitClient;
import com.vivekapps.utils.SaveSharedPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class signupActivity extends AppCompatActivity {

    EditText nameET,emailET,phoneET,usernameET,passwordET;
    Button signUpBtn;
    TextView signInTV;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameET = (EditText) findViewById(R.id.signupNameEditText);
        emailET = (EditText) findViewById(R.id.signupEmailEditText);
        phoneET = (EditText) findViewById(R.id.signupPhoneEditText);
        usernameET = (EditText) findViewById(R.id.signupUserNameEditText);
        passwordET = (EditText) findViewById(R.id.signupPasswordEditText);

        signUpBtn = (Button) findViewById(R.id.signupButton);

        signInTV = (TextView) findViewById(R.id.signInTextView);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String phone = phoneET.getText().toString();
                String username = usernameET.getText().toString();
                String password = passwordET.getText().toString();
                if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || username.isEmpty()
                        || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Please fill all the fields",Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(name,email,phone,username,password);
                }
            }
        });

    }

    // Register API Call

    private void registerUser(String name, String email, String phone, String username, String password) {

        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Retrofit retrofit = RetrofitClient.getClient();
        final LoginServices loginServices = retrofit.create(LoginServices.class);
        Call<Void> call = loginServices.userRegister("register",name,email,phone,password,"testaddress");

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(),"Registration Successfull!! Login with your credentials",
                            Toast.LENGTH_LONG).show();
                    // Set Logged In statue to 'true'
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), "Registration Failed!! Try again later!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                Log.e("TAG", "=======onFailure: " + t.toString());
                t.printStackTrace();
                // Log error here since request failed
            }
        });
    }
}
