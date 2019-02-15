package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
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

    public void addMedicamento(final Resultado resultado, final DialogInterface dialog, final String casilla) {

        MedicamentoService service = ServiceGenerator.createService(MedicamentoService.class, Util.getToken(getApplication().getApplicationContext()), TipoAutenticacion.JWT);
        Call<Resultado> call = service.addMedicamento(resultado);

        call.enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getApplication().getApplicationContext(), "Agregar pastilla a "+casilla, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al añadir medicamento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Error al añadir medicamento", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
