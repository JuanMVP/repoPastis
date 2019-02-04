package com.st.pillboxapp.ui;

import android.app.ListActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceApiGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PruebaAddMedicamento extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_prueba_add_medicamento);

        MedicamentoService service = ServiceApiGenerator.createService(MedicamentoService.class);

        Call<MedicamentoResponse> callList = service.getMedicamentos("ibuprofeno");

        callList.enqueue(new Callback<MedicamentoResponse>() {
            @Override
            public void onResponse(Call<MedicamentoResponse> call, Response<MedicamentoResponse> response) {
                if (response.isSuccessful()) {
                    cargar(response.body().getResultados());

                }else {

                }
            }

            @Override
            public void onFailure(Call<MedicamentoResponse> call, Throwable t) {

            }
        });
    }

    public void cargar(List<Resultado> resultados) {
        setListAdapter(new ArrayAdapter<Resultado>(this, android.R.layout.simple_list_item_1, resultados) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                Resultado resultado = getItem(position);
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
                }
                TextView txtName = convertView.findViewById(android.R.id.text1);
                TextView txtEmail = convertView.findViewById(android.R.id.text2);
                txtName.setText(resultado.getNombre());
                txtEmail.setText(resultado.getDosis());
                return convertView;
            }
        });
    }



}
