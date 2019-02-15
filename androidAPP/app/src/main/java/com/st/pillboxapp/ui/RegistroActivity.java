package com.st.pillboxapp.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Register;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.AuthAndRegisterService;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText nombre, correo, clave, comprobacionClave;
    private Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        nombre = findViewById(R.id.editNombreRegistro);
        correo = findViewById(R.id.editEmailRegistro);
        clave = findViewById(R.id.passwordRegsitro);
        comprobacionClave = findViewById(R.id.comprobacionPasswordRegistro);
        btnRegistro = findViewById(R.id.btnRegistro);

        doRegister();

    }

    public void onRegisterSuccess(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {

        Util.setData(RegistroActivity.this, response.body().getToken(), response.body().getUser().getId(),
                response.body().getUser().getEmail(),response.body().getUser().getName()
                ,response.body().getUser().getPicture());

        startActivity(new Intent(RegistroActivity.this, DashboardActivity.class));

    }

    //alerta de error en caso de fallo
    public void onRegisterFail(int tipoError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistroActivity.this);

        builder.setIcon(R.drawable.ic_cancelar);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        builder.setMessage(tipoError)
                .setTitle(R.string.error);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void doRegister() {

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(RegistroActivity.this, R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Registrando...");
                progressDialog.show();

                String name = nombre.getText().toString().trim();
                String email = correo.getText().toString().trim();
                String password = clave.getText().toString().trim();
                String compassword = comprobacionClave.getText().toString().trim();

                if(password.length() < 6){
                    onRegisterFail(R.string.register_contraseña_no_segura);
                }

                if(!password.equals(compassword)){
                    onRegisterFail(R.string.register_contraseña_incorrecta);
                }
            }
        });
    }

    public void crearUsuarioNuevo(Register registro, final ProgressDialog progressDialog){
        AuthAndRegisterService service = ServiceGenerator.createService(AuthAndRegisterService.class);

        Call<AuthAndRegisterResponse> registerResponseCall = service.register(registro);

        registerResponseCall.enqueue(new Callback<AuthAndRegisterResponse>() {

            @Override
            public void onResponse(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {

                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    onRegisterSuccess(call, response);

                } else {
                    progressDialog.dismiss();
                    onRegisterFail(R.string.error);
                }
            }

            @Override
            public void onFailure(Call<AuthAndRegisterResponse> call, Throwable t) {
                Log.e("NetworkFail", t.getMessage());
                Toast.makeText(RegistroActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
