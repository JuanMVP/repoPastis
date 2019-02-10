package com.st.pillboxapp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments.dummy.DummyContent.DummyItem;
import com.st.pillboxapp.responses.MedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceApiGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.ui.DashboardActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicamentosFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    MyMedicamentosRecyclerViewAdapter adapter;
    private Context ctx;
    RecyclerView recyclerView;
    EditText buscarMedicamentoPorNombre;
    ImageButton btnBuscarMedicamento;

    public MedicamentosFragment() {
    }


    public static MedicamentosFragment newInstance(int columnCount) {
        MedicamentosFragment fragment = new MedicamentosFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicamentos_list, container, false);

        buscarMedicamentoPorNombre = view.findViewById(R.id.findMedicamento);
        btnBuscarMedicamento = view.findViewById(R.id.buttonBuscarMedicamento);

        // Set the adapter
        if (view instanceof ConstraintLayout) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listPersonas);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));

            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            btnBuscarMedicamento.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final ProgressDialog progressDialog = new ProgressDialog(getContext(), R.style.Theme_AppCompat_DayNight_Dialog);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Buscando medicamento...");
                    progressDialog.show();

                    InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(buscarMedicamentoPorNombre.getWindowToken(), 0);
                    String findMedicamento = buscarMedicamentoPorNombre.getText().toString();
                    MedicamentoService medicamentoService = ServiceApiGenerator.createService(MedicamentoService.class);
                    Call<MedicamentoResponse> callMedicamento = medicamentoService.getMedicamentos(findMedicamento);

                    callMedicamento.enqueue(new Callback<MedicamentoResponse>() {
                        @Override
                        public void onResponse(Call<MedicamentoResponse> call, Response<MedicamentoResponse> response) {
                            if(response.isSuccessful()){
                                progressDialog.dismiss();
                                adapter = new MyMedicamentosRecyclerViewAdapter(ctx,R.layout.fragment_medicamentos, response.body().getResultados(),mListener);
                                recyclerView.setAdapter(adapter);
                            }else{
                                Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<MedicamentoResponse> call, Throwable t) {
                            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();

                        }
                    });


                }
            });




        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
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


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DummyItem item);
    }
}
