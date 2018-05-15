package com.vivekapps.avtodock_new;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
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
import retrofit2.Retrofit;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class LoginActivity extends AppCompatActivity {

    EditText usernameText,passwordText;
    TextView showPasswordText,signupLink;
    Button signInButton;
    ConstraintLayout loginForm;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameText = (EditText) findViewById(R.id.usernameEditText);
        passwordText = (EditText) findViewById(R.id.passwordEditText);
        signInButton = (Button) findViewById(R.id.signInButton);
        loginForm = (ConstraintLayout) findViewById(R.id.loginForm);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Check if the user is already logged in
        if (SaveSharedPreference.getLoggedStatus(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), vehicleTypeSelectionActivity.class);
            startActivity(intent);
        } else {
            loginForm.setVisibility(View.VISIBLE);
        }

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(),"Username or Password is empty",Toast.LENGTH_LONG).show();
                } else {
                    userLogin(usernameText.getText().toString(),passwordText.getText().toString());
                }
            }
        });

        showPasswordText = (TextView) findViewById(R.id.showPasswordTextView);
        showPasswordText.setTextColor(Color.BLUE);
        showPasswordText.setPaintFlags(showPasswordText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        showPasswordText.setText("Show Password");
        showPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransformationMethod transformationMethod = passwordText.getTransformationMethod();
                if (transformationMethod.equals(PasswordTransformationMethod.getInstance())) {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPasswordText.setText("Hide Password");
                } else if (transformationMethod.equals(HideReturnsTransformationMethod.getInstance())) {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPasswordText.setText("Show Password");
                }
            }
        });

        // Intent to Sign Up Activity
        signupLink = (TextView) findViewById(R.id.signUpLink);
        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(getApplicationContext(),signupActivity.class);
                startActivity(signupIntent);
            }
        });

    }

    /**
     * Login API call
     * @param username
     * @param password
     */
    private void userLogin(String username, String password) {

        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        Retrofit retrofit = RetrofitClient.getClient();
        final LoginServices loginServices = retrofit.create(LoginServices.class);
        Call<Void> call = loginServices.userLogin("login",username,password);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    // Set Logged In statue to 'true'
                    SaveSharedPreference.setLoggedIn(getApplicationContext(), true);
                    Intent intent = new Intent(getApplicationContext(), vehicleTypeSelectionActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    Toast.makeText(getApplicationContext(), "Credentials are not Valid.", Toast.LENGTH_SHORT).show();
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
