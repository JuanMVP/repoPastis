package com.st.pillboxapp.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.viewModel.AddMedicamentoViewModel;

public class AddMedicamentoFragment extends DialogFragment {

    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_DOSIS= "dosis";
    private static final String ARG_IMGURL= "imagUrl";
    private AddMedicamentoViewModel mViewModel;

    private DialogInterface.OnDismissListener onDismissListener;

    private View view;
    private TextView nombre;
    private String argNombre, argDosis, argImgurl;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public static AddMedicamentoFragment newInstance() {
        return new AddMedicamentoFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddMedicamentoViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            argNombre = getArguments().getString(ARG_NOMBRE);
            argDosis = getArguments().getString(ARG_DOSIS);
            argImgurl = getArguments().getString(ARG_IMGURL);
        }
    }

    public static AddMedicamentoFragment newInstance(Resultado resultado) {
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, resultado.getNombre());
        args.putString(ARG_DOSIS, resultado.getDosis());
        args.putString(ARG_IMGURL, resultado.getFotos().get(0).getUrl());

        AddMedicamentoFragment fragment = new AddMedicamentoFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_medicamento_fragment, null);

        nombre = view.findViewById(R.id.addMedicamento);

        nombre.setText(argNombre);

        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("")

                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {

                    public void onClick(final DialogInterface dialog, int id) {

                        Medicamento medicamento = new Medicamento(argNombre, argDosis, argImgurl);
                        mViewModel.addMedicamento(medicamento, dialog);
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
