package com.st.pillboxapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.st.pillboxapp.R;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.AuthAndRegisterService;

import java.util.regex.Pattern;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin, btnRegistro;
    GoogleSignInClient gSignInClient;
    SignInButton gSignButton;
    final int RC_SIGN_IN = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        GoogleSignInOptions gSignOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        gSignInClient = GoogleSignIn.getClient(this,gSignOptions);



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnGuardar);
        btnRegistro = findViewById(R.id.btnRegistro);
        gSignButton = findViewById(R.id.btnGoogle);
        gSignButton.setSize(SignInButton.SIZE_STANDARD);

        gSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }

            private void googleSignIn() {
                Intent signIntent = gSignInClient.getSignInIntent();
                startActivityForResult(signIntent,RC_SIGN_IN);
                startActivity(new Intent(LoginActivity.this,DashboardActivity.class));


            }
        });

        doLogin();


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

    }



    //Métodos------------


    public void onLoginSuccess(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", response.body().getToken());
        editor.putString("idUser", response.body().getUser().getId());
        editor.putString("emailUser", response.body().getUser().getEmail());
        editor.putString("nombreUser", response.body().getUser().getName());
        editor.putString("fotoUser", response.body().getUser().getPicture());
        editor.commit();

        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        finish();

    }

    public void onLoginFail() {
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

    public void doLogin(){

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Autenticando...");
                    progressDialog.show();


                    String emailTxt = email.getText().toString();
                    String passwordTxt = password.getText().toString();

                    String credentials = Credentials.basic(emailTxt, passwordTxt);

                    AuthAndRegisterService loginService = ServiceGenerator.createService(AuthAndRegisterService.class);

                    Call<AuthAndRegisterResponse> call = loginService.login(credentials);

                    call.enqueue(new Callback<AuthAndRegisterResponse>() {
                        @Override
                        public void onResponse(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {

                            if (response.isSuccessful()) {

                                progressDialog.dismiss();
                                onLoginSuccess(call, response);

                            } else {
                                progressDialog.dismiss();
                                onLoginFail();
                            }
                        }

                        @Override
                        public void onFailure(Call<AuthAndRegisterResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();

                        }
                    });


            }
        });

    }
}
