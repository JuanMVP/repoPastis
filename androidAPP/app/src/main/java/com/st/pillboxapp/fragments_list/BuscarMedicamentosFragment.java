package com.st.pillboxapp.fragments_list;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.adapter.MyBuscarMedicamentosRecyclerViewAdapter;
import com.st.pillboxapp.fragment_dialog.AddMedicamentoFragment;
import com.st.pillboxapp.fragment_dialog.AddPersonaFragment;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MyMedicamentoResponse;
import com.st.pillboxapp.responses.ResultadoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceApiGenerator;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BuscarMedicamentosFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListMedicamentosInteractionListener mListener;
    private MyBuscarMedicamentosRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private EditText buscarMedicamentoPorNombre;
    private ImageButton btnBuscarMedicamento, addMedicamento;
    private SwipeRefreshLayout swipe;

    public BuscarMedicamentosFragment() { }

    public static BuscarMedicamentosFragment newInstance(int columnCount) {
        BuscarMedicamentosFragment fragment = new BuscarMedicamentosFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar_medicamentos_list, container, false);

        buscarMedicamentoPorNombre = view.findViewById(R.id.findMedicamento);
        btnBuscarMedicamento = view.findViewById(R.id.buttonBuscarMedicamento);

        swipe = view.findViewById(R.id.swipeBuscarMedicamentos);
        swipe.setColorSchemeResources(R.color.azulSwipe, R.color.rojoSwipe);

        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listBuscarMedicamentos);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            buscarMedicamento();

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            actualizarDatos();
                            swipe.setRefreshing(false);
                        }
                    }, 3000);
                }


            });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListMedicamentosInteractionListener) {
            mListener = (OnListMedicamentosInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**Métodos propios **/

    public void buscarMedicamento() {

        btnBuscarMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String findMedicamento = buscarMedicamentoPorNombre.getText().toString().trim();

                final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Buscando medicamento...");
                progressDialog.show();

                //*Para ocultar automáticamente el teclado del móvil cuando se le da al botón de Buscar*//
                InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(buscarMedicamentoPorNombre.getWindowToken(), 0);


                //*Petición al API de medicamentos*//
                MedicamentoService medicamentoService = ServiceApiGenerator.createService(MedicamentoService.class);
                Call<ResultadoResponse> callMedicamento = medicamentoService.getMedicamentos(findMedicamento);

                callMedicamento.enqueue(new Callback<ResultadoResponse>() {
                    @Override
                    public void onResponse(Call<ResultadoResponse> call, Response<ResultadoResponse> response) {
                        if (response.isSuccessful()) {
                            progressDialog.dismiss();
                            adapter = new MyBuscarMedicamentosRecyclerViewAdapter(ctx, R.layout.fragment_buscar_medicamentos, response.body().getResultados(), mListener);
                            recyclerView.setAdapter(adapter);
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Error al obtener los datos", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResultadoResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error de conexión.", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });

    }

    public void actualizarDatos() {

        String findMedicamento = buscarMedicamentoPorNombre.getText().toString().trim();
        swipe.setColorSchemeResources(R.color.azulSwipe, R.color.rojoSwipe);

        //*Para ocultar automáticamente el teclado del móvil cuando se le da al botón de Buscar*//
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(buscarMedicamentoPorNombre.getWindowToken(), 0);

        //*Petición a nuestra API*//
        MedicamentoService medicamentoService = ServiceApiGenerator.createService(MedicamentoService.class);
        Call<ResultadoResponse> callMedicamento = medicamentoService.getMedicamentos(findMedicamento);

        callMedicamento.enqueue(new Callback<ResultadoResponse>() {
            @Override
            public void onResponse(Call<ResultadoResponse> call, Response<ResultadoResponse> response) {
                if (response.isSuccessful()) {
                    adapter = new MyBuscarMedicamentosRecyclerViewAdapter(ctx, R.layout.fragment_buscar_medicamentos, response.body().getResultados(), mListener);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error. Intenta de nuevo.", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<ResultadoResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_LONG).show();

            }
        });


    }
}
