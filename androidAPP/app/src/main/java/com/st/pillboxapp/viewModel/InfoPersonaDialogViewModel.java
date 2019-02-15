package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPersonaDialogViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    public InfoPersonaDialogViewModel(@NonNull Application application) {
        super(application);
    }

    List<Resultado> listaMedicamentos;

    public void setData(String argIdPersona, final Spinner spinner) {
        PersonaService personaService = ServiceGenerator.createService(PersonaService.class, Util.getToken(getApplication().getApplicationContext()), TipoAutenticacion.JWT);
        Call<PersonaResponse> call = personaService.findOne(argIdPersona);
        call.enqueue(new Callback<PersonaResponse>() {
            @Override
            public void onResponse(Call<PersonaResponse> call, Response<PersonaResponse> response) {
                if (response.isSuccessful()) {
                    final ArrayAdapter<Resultado> listaMedicamentos;
                    listaMedicamentos = new ArrayAdapter<Resultado>(getApplication().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, response.body().getMedicamentos());
                    spinner.setAdapter(listaMedicamentos);
                } else {

                }
            }

            @Override
            public void onFailure(Call<PersonaResponse> call, Throwable t) {

            }
        });
    }
}
