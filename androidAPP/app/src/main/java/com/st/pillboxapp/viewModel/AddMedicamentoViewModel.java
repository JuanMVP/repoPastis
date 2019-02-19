package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MyMedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddMedicamentoViewModel extends AndroidViewModel {
    public AddMedicamentoViewModel(@NonNull Application application) {
        super(application);
    }

    public void addMedicamento(Medicamento medicamento, final DialogInterface dialog){
        MedicamentoService mService = ServiceGenerator.createService(MedicamentoService.class, Util.getToken(getApplication().getApplicationContext()),  TipoAutenticacion.JWT);
        Call<Medicamento> call = mService.addMedicamento(medicamento);

        call.enqueue(new Callback<Medicamento>() {
            @Override
            public void onResponse(Call<Medicamento> call, Response<Medicamento> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getApplication().getApplicationContext(), "Medicamento añadido correctamente", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al añadir", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Medicamento> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Error al añadir", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
