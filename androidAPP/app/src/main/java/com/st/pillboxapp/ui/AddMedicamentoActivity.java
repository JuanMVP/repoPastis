package com.st.pillboxapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.st.pillboxapp.R;
import com.st.pillboxapp.responses.MedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceApiGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicamentoActivity extends AppCompatActivity {

    EditText etMedicamento;
    ImageView imgMedicamentoAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicamento);


        etMedicamento = findViewById(R.id.nombreMedicamentoAdd);
        imgMedicamentoAdd = findViewById(R.id.imagenMedicamentoAdd);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        MedicamentoService service = ServiceApiGenerator.createService(MedicamentoService.class);

        Call<MedicamentoResponse> call = service.getOneMedicamento(prefs.getString("nregistro", ""));

        call.enqueue(new Callback<MedicamentoResponse>() {
            @Override
            public void onResponse(Call<MedicamentoResponse> call, Response<MedicamentoResponse> response) {
                if (response.isSuccessful()) {
                    etMedicamento.setText(response.body().getResultados().get(0).getNombre());
                    if (response.body().getFotos() != null) {
                        Glide.with(AddMedicamentoActivity.this).load(response.body().getFotos().get(1).getUrl()).into(imgMedicamentoAdd);

                    } else {
                        imgMedicamentoAdd.setImageResource(R.drawable.ic_no_photo);
                    }

                } else {
                    Toast.makeText(AddMedicamentoActivity.this, "Error", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MedicamentoResponse> call, Throwable t) {
                Toast.makeText(AddMedicamentoActivity.this, "Error", Toast.LENGTH_LONG);

            }
        });
    }
}
