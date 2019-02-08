package com.st.pillboxapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments.PersonasFragment;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.AuthAndRegisterResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddPersonaActivity extends AppCompatActivity {

    EditText nombre, fechaNacimiento;
    Button registrar;
    Fragment f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_persona);

        nombre = findViewById(R.id.nombrePersona);
        fechaNacimiento = findViewById(R.id.fechaNacPersona);
        registrar = findViewById(R.id.btnRegistrarPersona);

        registrar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString().trim();
                String fecha = fechaNacimiento.getText().toString().trim();

                Persona persona = new Persona(name, fecha);

                SharedPreferences prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                PersonaService pService = ServiceGenerator.createService(PersonaService.class, prefs.getString("token", ""), TipoAutenticacion.JWT);

                Call personaResponseCall = pService.register(persona);

                personaResponseCall.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {

                        f = null;

                        if(response.isSuccessful()){
                            f = new PersonasFragment();
                            getSupportFragmentManager().beginTransaction().add(R.id.contenedor, f).commit();
                        }else{
                            Toast.makeText(AddPersonaActivity.this, "no funka", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {

                    }
                });

            }

        });

    }

}
