package com.st.pillboxapp.ui;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.responses.LoginResponse;
import com.st.pillboxapp.retrofit.generator.LoginServiceGenerator;
import com.st.pillboxapp.retrofit.services.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();

                String credentials = emailTxt + ":" + passwordTxt;

                final String basic = "Basic" + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

                LoginService loginService = LoginServiceGenerator.createService(LoginService.class);

                Call<LoginResponse> call = loginService.login(LoginServiceGenerator.access_token, basic);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "LOGIN CORRECTO", Toast.LENGTH_LONG);

                        }else {
                            Toast.makeText(LoginActivity.this, "Error en el login", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_LONG);

                    }
                });
            }
        });
    }
}
