package com.st.pillboxapp.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.st.pillboxapp.R;
import com.st.pillboxapp.viewModel.DeleteMisMedicamentosViewModel;

public class DeleteMisMedicamentos extends DialogFragment {

    private static final String ARG_NOMBRE = "nombre";
    private DeleteMisMedicamentosViewModel mViewModel;

    private DialogInterface.OnDismissListener onDismissListener;

    private View view;
    private TextView nombre;
    private String argNombre;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public static DeleteMisMedicamentos newInstance() {
        return new DeleteMisMedicamentos();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DeleteMisMedicamentosViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            argNombre = getArguments().getString(ARG_NOMBRE);
        }
    }

    public static DeleteMisMedicamentos newInstance(String nombre) {
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, nombre);

        DeleteMisMedicamentos fragment = new DeleteMisMedicamentos();
        fragment.setArguments(args);

        return fragment;

    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.delete_mis_medicamentos_fragment, null);

        nombre = view.findViewById(R.id.deleteMisMedicamentos);

        nombre.setText(argNombre);

        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Eliminar: ")

                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int id) {
                        String nombreEditado = nombre.getText().toString();

                        mViewModel.deleteMisMedicamentos(nombreEditado, dialog);
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
