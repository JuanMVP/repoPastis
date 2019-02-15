package com.st.pillboxapp.fragment_dialog;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MedicamentoResponse;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.util.Util;
import com.st.pillboxapp.viewModel.InfoPersonaDialogViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPersonaDialogFragment extends DialogFragment {

    private InfoPersonaDialogViewModel mViewModel;
    private View view;
    private TextView nombrePersona, fechaPersona;
    private Spinner spinnerInfoPersonas;
    private String argNombre,argFecha, argIdPersona;
    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_FECHA_NACIMIENTO = "fecha_nacimiento";
    private static final String ARG_ID_PERSONA = "id";
    private ArrayAdapter<Medicamento> medicamentos;
    private List<Resultado> listaMedicamentos;

    public static InfoPersonaDialogFragment newInstance(Persona persona) {
        Bundle args = new Bundle();
        args.putString(ARG_ID_PERSONA, persona.getId());

        InfoPersonaDialogFragment fragment = new InfoPersonaDialogFragment();
        fragment.setArguments(args);

        return fragment;

    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.info_persona_dialog_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InfoPersonaDialogViewModel.class);

        if(getArguments() != null){
            argIdPersona = getArguments().getString(ARG_ID_PERSONA);
        }

        // TODO: Use the ViewModel
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.info_persona_dialog_fragment,null);

        nombrePersona = view.findViewById(R.id.nombreInfoPersonaFrag);
        fechaPersona = view.findViewById(R.id.fechaInfoPersonaFrag);
        spinnerInfoPersonas = view.findViewById(R.id.spinnerInfoPersona);

        nombrePersona.setText(argNombre);
        fechaPersona.setText(argFecha);
        mViewModel.setData(argIdPersona,spinnerInfoPersonas);


        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Información");
        builder.setMessage("Datos de una Persona")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("Cerrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });





        builder.setView(view);


        // Create the AlertDialog object and return it
        return builder.create();
    }

}
