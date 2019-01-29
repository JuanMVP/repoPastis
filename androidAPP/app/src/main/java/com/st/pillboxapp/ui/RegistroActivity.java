package com.st.pillboxapp.ui;

import android.content.Intent;
import android.os.Bundle;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    EditText nombre, correo, clave;
    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre = findViewById(R.id.editNombreRegistro);
        correo = findViewById(R.id.editEmailRegistro);
        clave = findViewById(R.id.passwordRegsitro);
        btnRegistro = findViewById(R.id.buttonRegistro);


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString().trim();
                String email = correo.getText().toString().trim();
                String password = clave.getText().toString().trim();

                Register registro = new Register(name,email,password);

                AuthAndRegisterService service = ServiceGenerator.createService(AuthAndRegisterService.class);

                Call<AuthAndRegisterResponse> registerResponseCall = service.register(registro);

                registerResponseCall.enqueue(new Callback<AuthAndRegisterResponse>() {

                    @Override
                    public void onResponse(Call<AuthAndRegisterResponse> call, Response<AuthAndRegisterResponse> response) {

                        if(response.isSuccessful()) {

                            ServiceGenerator.jwToken = response.body().getToken();

                            Toast.makeText(RegistroActivity.this, response.body().getToken(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(RegistroActivity.this,LoginActivity.class));

                        }else {

                            Toast.makeText(RegistroActivity.this, "Error al registrar, revise los datos introducidos", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthAndRegisterResponse> call, Throwable t) {
                            Log.e("NetworkFail",t.getMessage());
                        Toast.makeText(RegistroActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


    }
}
