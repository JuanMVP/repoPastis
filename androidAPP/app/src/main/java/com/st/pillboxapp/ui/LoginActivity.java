package com.st.pillboxapp.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.AuthAndRegisterService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnLogin);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailTxt = email.getText().toString();
                String passwordTxt = password.getText().toString();

                AuthAndRegisterService loginService = ServiceGenerator.createService(AuthAndRegisterService.class, emailTxt, passwordTxt);

                Call<AuthAndRegisterResponse> call = loginService.login();

                call.enqueue(new Callback<AuthAndRegisterResponse>() {
                    @Override
                    public void onResponse(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "LOGIN CORRECTO", Toast.LENGTH_LONG).show();

                        }else {
                            Toast.makeText(LoginActivity.this, "Error en el login", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthAndRegisterResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
            }
        });

    }
}
