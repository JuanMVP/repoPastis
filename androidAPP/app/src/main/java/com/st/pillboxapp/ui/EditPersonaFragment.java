package com.st.pillboxapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPersonaFragment extends DialogFragment {

    private Button registrarPersona;

    private static final String ARG_FECHA_NAC = "fecha_nacimiento";
    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_ID_PERSONA = "id_persona";
    private EditPersonaViewModel mViewModel;

    public static EditPersonaFragment newInstance() {
        return new EditPersonaFragment();
    }

    private View view;
    private EditText nombre, fechaNacimiento;
    private String argNombre, argFecha, argId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditPersonaViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            argNombre = getArguments().getString(ARG_NOMBRE);
            argFecha = getArguments().getString(ARG_FECHA_NAC);
            argId = getArguments().getString(ARG_ID_PERSONA);
        }
    }

    public static EditPersonaFragment newInstance(Persona p) {
        Bundle args = new Bundle();
        args.putString(ARG_FECHA_NAC, p.getFecha_nacimiento());
        args.putString(ARG_NOMBRE, p.getNombre());
        args.putString(ARG_ID_PERSONA, p.getId());

        EditPersonaFragment fragment = new EditPersonaFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("")
                .setPositiveButton(R.string.edit_persona, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String nombreEditado = nombre.getText().toString();
                        String fechaNacimientoEditado = fechaNacimiento.getText().toString();
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.edit_persona_fragment, null);

        nombre = view.findViewById(R.id.editNombrePersona);
        fechaNacimiento = view.findViewById(R.id.editFechaNacPersona);
        registrarPersona = view.findViewById(R.id.btnRegistrarEditPersona);

        nombre.setText(argNombre);
        fechaNacimiento.setText(argFecha);

        registrarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nombre.getText().toString().trim();
                String fecha = fechaNacimiento.getText().toString().trim();

                final SharedPreferences prefs = getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                Persona persona = new Persona(name, fecha, prefs.getString("idUser", ""));

                PersonaService pService = ServiceGenerator.createService(PersonaService.class, prefs.getString("token", ""), TipoAutenticacion.JWT);


            }
        });


        builder.setView(view);

        // Create the AlertDialog object and return it
        return builder.create();
    }

}
