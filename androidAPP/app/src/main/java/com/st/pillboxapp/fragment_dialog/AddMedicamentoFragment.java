package com.st.pillboxapp.fragment_dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.st.pillboxapp.R;

import com.st.pillboxapp.fragments_list.MyPersonasRecyclerViewAdapter;
import com.st.pillboxapp.models.Medicamento;
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
    private RadioGroup rg_diaSemana, rg_horaToma;
    private Spinner spinnerPersonas;
    private ArrayAdapter<Persona> personas;
    private List<Persona> listpersonas;
    private String id_persona;


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

        rg_diaSemana = view.findViewById(R.id.rgDiaSemana);
        rg_horaToma = view.findViewById(R.id.rgHoraToma);

        nombre.setText(argNombre);
        dosis.setText(argDosis);

        nombre.setEnabled(true);
        dosis.setEnabled(true);
        cargarSpinner();

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


        //*Se crea el DialogFragment*//
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                String nombreMedicamento = nombre.getText().toString();
                String dosisMedicamento = dosis.getText().toString();


                id_persona = ((Persona) spinnerPersonas.getSelectedItem()).getId();

                String casilla = asignarCasillaPastillero();

                Medicamento medicamento = new Medicamento(nombreMedicamento, dosisMedicamento, id_persona);
                mViewModel.addMedicamento(medicamento, dialog, casilla);



            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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


    }

    public String seleccionarHora() {
        String numCasilla = null;

        switch (rg_horaToma.getCheckedRadioButtonId()) {
            case R.id.rdbtnDesayuno:
                numCasilla = "Desayuno";
                break;
            case R.id.rdbtnMediaManana:
                numCasilla = "M.Mediamañana";
                break;
            case R.id.rdbtnAlmuerzo:
                numCasilla = "Almuerzo";
                break;
            case R.id.rdbtnCena:
                numCasilla = "Cenar";
                break;
        }

        return numCasilla;
    }

    public String asignarCasillaPastillero() {

        String casilla = null;

        switch (rg_diaSemana.getCheckedRadioButtonId()) {
            case R.id.rdbtnLunes:
                casilla = "Lunes " + seleccionarHora();
                break;
            case R.id.rdbtnMartes:
                casilla = "Martes " + seleccionarHora();
                break;
            case R.id.rdbtnMiercoles:
                casilla = "Miercoles " + seleccionarHora();
                break;
            case R.id.rdbtnJueves:
                casilla = "Jueves " + seleccionarHora();
                break;
            case R.id.rdbtnViernes:
                casilla = "Viernes " + seleccionarHora();
                break;
            case R.id.rdbtnSabado:
                casilla = "Sabado " + seleccionarHora();
                break;
            case R.id.rdbtnDomingo:
                casilla = "Domingo " + seleccionarHora();
                break;
        }

        return casilla;

    }

}
