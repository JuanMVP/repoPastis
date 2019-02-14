package com.st.pillboxapp.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.util.Util;
import com.st.pillboxapp.viewModel.EditPersonaViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class EditPersonaFragment extends DialogFragment {

    private static final String ARG_FECHA_NAC = "fecha_nacimiento";
    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_ID_PERSONA = "id_persona";
    private EditPersonaViewModel mViewModel;

    private DialogInterface.OnDismissListener onDismissListener;

    private View view;
    private EditText nombre, fechaNacimiento;
    private String argNombre, argFecha, argId;


    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public static EditPersonaFragment newInstance() {
        return new EditPersonaFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EditPersonaViewModel.class);
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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.edit_persona_fragment, null);

        nombre = view.findViewById(R.id.editNombrePersona);
        fechaNacimiento = view.findViewById(R.id.editFechaNacPersona);

        nombre.setText(argNombre);
        fechaNacimiento.setText(argFecha);

        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Editar persona: ")

                .setPositiveButton(R.string.editPersona, new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int id) {
                        String nombreEditado = nombre.getText().toString();
                        String fechaNacimientoEditado = fechaNacimiento.getText().toString();

                        Persona persona = new Persona(nombreEditado, fechaNacimientoEditado, Util.getUserId(getContext()));

                        mViewModel.editPersona(persona,argId, dialog);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });


        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

}
