package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModel;
import android.content.DialogInterface;
import android.media.AsyncPlayer;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.ui.DashboardActivity;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletePersonaViewModel extends AndroidViewModel {

    public DeletePersonaViewModel(@NonNull Application application) {super(application);}

    public void deletePersona(String nombre, String id, final DialogInterface dialog) {
        PersonaService service = ServiceGenerator.createService(PersonaService.class, Util.getToken(getApplication().getApplicationContext()), TipoAutenticacion.JWT);
        Call<PersonaResponse> call = service.deleteOne(id);

        call.enqueue(new Callback<PersonaResponse>() {
            @Override
            public void onResponse(Call<PersonaResponse> call, Response<PersonaResponse> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al borrar", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<PersonaResponse> call, Throwable t) {
                Toast.makeText(getApplication().getApplicationContext(), "Error al borrar", Toast.LENGTH_LONG);

            }
        });

    }
}
