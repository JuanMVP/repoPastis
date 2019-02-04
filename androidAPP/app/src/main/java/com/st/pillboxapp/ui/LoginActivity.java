package com.st.pillboxapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.AuthAndRegisterService;

import okhttp3.Credentials;
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

                    String credentials = Credentials.basic(emailTxt, passwordTxt);

                    AuthAndRegisterService loginService = ServiceGenerator.createService(AuthAndRegisterService.class);

                    Call<AuthAndRegisterResponse> call = loginService.login(credentials);

                    call.enqueue(new Callback<AuthAndRegisterResponse>() {
                        @Override
                        public void onResponse(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {
                            if (response.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this,MenuActivity.class));

                            } else {

                               AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);


                                builder.setIcon(R.drawable.ic_cancelar);

                                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();

                                    }
                                });

                                builder.setMessage(R.string.dialog_message)
                                        .setTitle(R.string.dialog_title);


                                AlertDialog dialog = builder.create();
                                dialog.show();
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
