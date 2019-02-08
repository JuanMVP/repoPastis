package com.st.pillboxapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.st.pillboxapp.R;

public class AddPersonaActivity extends AppCompatActivity {

    TextView nombre, fechaNacimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_persona);

        nombre = findViewById(R.id.nombrePersona);
        fechaNacimiento = findViewById(R.id.fechaNacPersona);

    }



}
