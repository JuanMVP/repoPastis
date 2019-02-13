package com.st.pillboxapp.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.EditText;


import com.st.pillboxapp.R;

import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;

import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;

import com.st.pillboxapp.viewModel.AddMedicamentoViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicamentoFragment extends DialogFragment {

    private AddMedicamentoViewModel mViewModel;
    private static final String ARG_DOSIS = "dosis";
    private static final String ARG_NOMBRE_MED = "nombre";
    private static final String ARG_ID_MEDICAMENTO = "nregistro";


    public static AddMedicamentoFragment newInstance() {
        return new AddMedicamentoFragment();
    }

    private View view;
    private EditText nombre, dosis;
    private String argNombre, argDosis, argIdMedicamento;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddMedicamentoViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            argNombre = getArguments().getString(ARG_NOMBRE_MED);
            argIdMedicamento = getArguments().getString(ARG_ID_MEDICAMENTO);
            argDosis = getArguments().getString(ARG_DOSIS);
        }
    }

    public static AddMedicamentoFragment newInstance(Resultado resultado) {
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE_MED, resultado.getNombre());
        args.putString(ARG_DOSIS, resultado.getDosis());

        AddMedicamentoFragment fragment = new AddMedicamentoFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton(R.string.add_medicamento, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                String nombreMedicamento = nombre.getText().toString();
                String dosisMedicamento = dosis.getText().toString();

                SharedPreferences prefs = getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                Resultado resultado = new Resultado(nombreMedicamento, dosisMedicamento);

                MedicamentoService service = ServiceGenerator.createService(MedicamentoService.class, prefs.getString("token", ""), TipoAutenticacion.JWT);

                Call<Resultado> call = service.addMedicamento(resultado);

                call.enqueue(new Callback<Resultado>() {
                    @Override
                    public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                        } else {

                        }
                    }

                    @Override
                    public void onFailure(Call<Resultado> call, Throwable t) {

                    }
                });

            }
        })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_medicamento_fragment, null);

        nombre = view.findViewById(R.id.nombreMedicamentoAdd);
        dosis = view.findViewById(R.id.dosisMedicamentoAdd);

        nombre.setText(argNombre);
        dosis.setText(argDosis);

        builder.setView(view);
        return builder.create();
    }
}
