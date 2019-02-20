package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MyMedicamentoResponse;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteMisMedicamentosViewModel extends AndroidViewModel {

    public DeleteMisMedicamentosViewModel(@NonNull Application application) {super(application);}

    public void deleteMisMedicamentos(String nombre, final DialogInterface dialog) {
        MedicamentoService service = ServiceGenerator.createService(MedicamentoService.class, Util.getToken(getApplication().getApplicationContext()), TipoAutenticacion.JWT);
        Call<MyMedicamentoResponse> call = service.deleteOne(nombre);

        call.enqueue(new Callback<MyMedicamentoResponse>() {
            @Override
            public void onResponse(Call<MyMedicamentoResponse> call, Response<MyMedicamentoResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al borrar", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<MyMedicamentoResponse> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Error al borrar", Toast.LENGTH_LONG);

            }
        });

    }

}
