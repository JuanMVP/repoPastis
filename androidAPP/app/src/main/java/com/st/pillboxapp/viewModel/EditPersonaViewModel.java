package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.st.pillboxapp.fragment_dialog.EditPersonaFragment;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.ui.DashboardActivity;
import com.st.pillboxapp.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPersonaViewModel extends AndroidViewModel {

    public EditPersonaViewModel(@NonNull Application application) {
        super(application);
    }

    public void editPersona(Persona persona, String persona_id, final DialogInterface dialog) {
        PersonaService pService = ServiceGenerator.createService(PersonaService.class, Util.getToken(getApplication().getApplicationContext()), TipoAutenticacion.JWT);
        Call<PersonaResponse> call = pService.editOne(persona_id, persona);

        call.enqueue(new Callback<PersonaResponse>() {
            @Override
            public void onResponse(Call<PersonaResponse> call, Response<PersonaResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al editar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PersonaResponse> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Error al editar", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
