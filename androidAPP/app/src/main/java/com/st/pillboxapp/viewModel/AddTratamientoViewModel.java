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
import com.st.pillboxapp.models.Tomas;
import com.st.pillboxapp.responses.MyMedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.retrofit.services.TomasService;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTratamientoViewModel extends AndroidViewModel {

    public AddTratamientoViewModel(@NonNull Application application) {
        super(application);
    }

    public void addTratamiento(final Tomas tomas, final DialogInterface dialog) {

        TomasService service = ServiceGenerator.createService(TomasService.class, Util.getToken(getApplication().getApplicationContext()), TipoAutenticacion.JWT);
        Call<Tomas> call = service.add(tomas);

        call.enqueue(new Callback<Tomas>() {
            @Override
            public void onResponse(Call<Tomas> call, Response<Tomas> response) {

                if (response.isSuccessful()) {
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al añadir tratamiento", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Tomas> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexion al añadir tratamiento", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
