package com.st.pillboxapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.st.pillboxapp.fragment_dialog.EditPersonaFragment;
import com.st.pillboxapp.models.Persona;

import java.util.List;

public class EditPersonaViewModel extends AndroidViewModel {
    private LiveData<List<Persona>> personas;

    public EditPersonaViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Persona>> getPersonas() {
        personas = new MutableLiveData<List<Persona>>();
        return personas;
    }

    private void editPersona() {
        // Do an asynchronous operation to fetch users.
    }


}
