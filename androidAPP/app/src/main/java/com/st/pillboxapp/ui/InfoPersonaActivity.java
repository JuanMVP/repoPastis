package com.st.pillboxapp.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.st.pillboxapp.R;

public class InfoPersonaActivity extends AppCompatActivity {

    private RecyclerView recyclerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_persona);

        recyclerInfo = findViewById(R.id.listaInfoPersonas);






    }
}
