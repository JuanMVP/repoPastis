package com.st.pillboxapp.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.util.Util;
import com.st.pillboxapp.viewModel.AddPersonaViewModel;

public class AddPersonaFragment extends DialogFragment {

    private AddPersonaViewModel mViewModel;

    private DialogInterface.OnDismissListener onDismissListener;

    private View view;
    private EditText nombre, fechaNacimiento;
    private String argNombre, argFecha, argId;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public static AddPersonaFragment newInstance() {
        return new AddPersonaFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPersonaViewModel.class);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_persona_fragment, null);

        nombre = view.findViewById(R.id.editNombrePersona);
        fechaNacimiento = view.findViewById(R.id.editFechaNacPersona);

        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Añadir persona: ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreAdd = nombre.getText().toString();
                String fechaNacimientoAdd = fechaNacimiento.getText().toString();

                Persona persona = new Persona(nombreAdd, fechaNacimientoAdd, Util.getUserId(getContext()));
                mViewModel.AddPersona(persona, dialog);

            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
