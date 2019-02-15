package com.st.pillboxapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.st.pillboxapp.R;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.AuthAndRegisterService;
import com.st.pillboxapp.util.Util;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btnLogin, btnRegistro;
    /*private GoogleSignInClient gSignInClient;
    private SignInButton gSignButton;
    private final int RC_SIGN_IN = 7;
    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;
    private FirebaseApp firebaseApp;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //FirebaseApp.initializeApp(this);
        /*if(Util.getToken(LoginActivity.this) != null) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }*/

        getSupportActionBar().hide();

        /*GoogleSignInOptions gSignOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("524853302850-s2rpbnoaajj6qqj8u2udipmhulp4ljqe.apps.googleusercontent.com")
                .requestEmail()
                .build();*/





        //gSignInClient = GoogleSignIn.getClient(this,gSignOptions);







        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        email = findViewById(R.id.emailLogin);
        password = findViewById(R.id.passwordLogin);

        btnLogin = findViewById(R.id.btnGuardar);
        btnRegistro = findViewById(R.id.btnRegistro);
        /*gSignButton = findViewById(R.id.btnGoogle);
        gSignButton.setSize(SignInButton.SIZE_STANDARD);

        gSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn();
            }

            private void googleSignIn() {
                mAuth = FirebaseAuth.getInstance();
                Intent signIntent = gSignInClient.getSignInIntent();
                startActivityForResult(signIntent,RC_SIGN_IN);



            }
        });*/

        doLogin();


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
            }
        });

    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{

                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            }catch (ApiException e){

            }
        }
    }*/


    /*private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Error en el Login", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }*/

    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user){

        Toast.makeText(this, "Entra en updateUI", Toast.LENGTH_SHORT).show();


    }*/


    //Métodos------------


    public void onLoginSuccess(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {


        Util.setData(LoginActivity.this, response.body().getToken(), response.body().getUser().getId(),
                response.body().getUser().getEmail(),response.body().getUser().getName()
                ,response.body().getUser().getPicture());

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

        builder.setMessage(R.string.login_error)
                .setTitle(R.string.error);


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void doLogin() {

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
                    public void onResponse(final Call<AuthAndRegisterResponse> call, final Response<AuthAndRegisterResponse> response) {

                        if (response.isSuccessful()) {

                            Runnable progressRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    progressDialog.cancel();
                                    onLoginSuccess(call, response);
                                }
                            };

                            Handler pdCanceller = new Handler();
                            pdCanceller.postDelayed(progressRunnable, 2000);


                        } else {
                            progressDialog.cancel();
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
