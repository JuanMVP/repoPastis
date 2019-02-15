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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.st.pillboxapp.R;

import com.st.pillboxapp.fragments_list.MyPersonasRecyclerViewAdapter;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;

import com.st.pillboxapp.responses.OneUserResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;

import com.st.pillboxapp.retrofit.services.UserService;
import com.st.pillboxapp.util.Util;
import com.st.pillboxapp.viewModel.AddMedicamentoViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

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
    private TextView nombre, dosis;
    private String argNombre, argDosis, argIdMedicamento;
    private Spinner spinnerPersonas;
    private ArrayAdapter<Persona> personas;
    private List<Persona> listpersonas;


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

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.add_medicamento_fragment, null);

        nombre = view.findViewById(R.id.nombreAddMedicamentoFrag);
        dosis = view.findViewById(R.id.dosisAddMedicamentoFrag);
        spinnerPersonas = view.findViewById(R.id.spinnerPersonas);

        nombre.setText(argNombre);
        dosis.setText(argDosis);

        nombre.setEnabled(true);
        dosis.setEnabled(true);
        cargarSpinner();


        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Añadir tratamiento", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                String nombreMedicamento = nombre.getText().toString();
                String dosisMedicamento = dosis.getText().toString();

                //*Petición a nuestra API*//
                Resultado resultado = new Resultado(nombreMedicamento, dosisMedicamento);

                MedicamentoService service = ServiceGenerator.createService(MedicamentoService.class, Util.getToken(getContext()), TipoAutenticacion.JWT);
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
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        builder.setView(view);


        return builder.create();
    }

    public void cargarSpinner() {

        UserService userService = ServiceGenerator.createService(UserService.class, Util.getToken(this.getActivity()), TipoAutenticacion.JWT);
        Call<OneUserResponse> call = userService.oneUserById(Util.getUserId(this.getActivity()));

        call.enqueue(new Callback<OneUserResponse>() {
            @Override
            public void onResponse(Call<OneUserResponse> call, Response<OneUserResponse> response) {
                if (response.isSuccessful()) {
                    listpersonas = new ArrayList<>(response.body().getPersonas());
                    personas = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, listpersonas);
                    spinnerPersonas.setAdapter(personas);
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OneUserResponse> call, Throwable t) {

                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });

        spinnerPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Persona persona = listpersonas.get(position);
                Toast.makeText(getContext(), persona.getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
}
