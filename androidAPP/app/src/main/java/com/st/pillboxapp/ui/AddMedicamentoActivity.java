package com.st.pillboxapp.ui;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments_list.MyPersonasRecyclerViewAdapter;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MedicamentoResponse;
import com.st.pillboxapp.responses.OneUserResponse;
import com.st.pillboxapp.retrofit.generator.ServiceApiGenerator;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.retrofit.services.UserService;
import com.st.pillboxapp.util.Util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMedicamentoActivity extends AppCompatActivity {

    private TextView nombreAddMedicamento, dosisAddMedicamento,picker;
    private Spinner spinnerPersonas;
    private Button btnAnadirMedicamento, escogerHora;
    private ArrayAdapter<Persona> adapter;
    private List<String> list;
    private String idSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicamento);

        nombreAddMedicamento = findViewById(R.id.nombreAddMedicamento);
        dosisAddMedicamento = findViewById(R.id.dosisAddMedicamento);
        btnAnadirMedicamento = findViewById(R.id.anadirMedicamento);
        picker = findViewById(R.id.pickTime);
        escogerHora = findViewById(R.id.escogerHora);
        spinnerPersonas = findViewById(R.id.spinnerPersonas);

        cargarSpinner();
        onClickPickTimer();
        getOneMedicamentoClicked();
        addMedicamento();


    }

    public void cargarSpinner() {
        UserService userService = ServiceGenerator.createService(UserService.class, Util.getToken(AddMedicamentoActivity.this), TipoAutenticacion.JWT);
        Call<OneUserResponse> call = userService.oneUserById(Util.getUserId(AddMedicamentoActivity.this));

        call.enqueue(new Callback<OneUserResponse>() {
            @Override
            public void onResponse(Call<OneUserResponse> call, Response<OneUserResponse> response) {
                if (response.isSuccessful()) {
                    list = new ArrayList<>();
                   /* for(int i = 0; i < response.body().getPersonas().size(); i++){
                        list.add(response.body().getPersonas().get(i).getNombre());
                    }*/
                    adapter = new ArrayAdapter<>(AddMedicamentoActivity.this, android.R.layout.simple_spinner_dropdown_item, response.body().getPersonas());
                    spinnerPersonas.setAdapter(adapter);

                } else {
                    Toast.makeText(AddMedicamentoActivity.this, "Error al obtener datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<OneUserResponse> call, Throwable t) {


                Toast.makeText(AddMedicamentoActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onClickPickTimer() {

        escogerHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMedicamentoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute <10) {
                            picker.setText(selectedHour + ":" + "0"+selectedMinute);

                        }else {
                            picker.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
    }




    public void getOneMedicamentoClicked() {
        MedicamentoService service = ServiceApiGenerator.createService(MedicamentoService.class);
        Call<MedicamentoResponse> call = service.getOneMedicamento(Util.getIdMedicamento(AddMedicamentoActivity.this));

        call.enqueue(new Callback<MedicamentoResponse>() {
            @Override
            public void onResponse(Call<MedicamentoResponse> call, Response<MedicamentoResponse> response) {
                if (response.isSuccessful()) {
                    nombreAddMedicamento.setText(response.body().getResultados().get(0).getNombre());
                    dosisAddMedicamento.setText(response.body().getResultados().get(0).getDosis());
                } else {

                }
            }

            @Override
            public void onFailure(Call<MedicamentoResponse> call, Throwable t) {

            }
        });
    }


    public void addMedicamento() {
        btnAnadirMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
