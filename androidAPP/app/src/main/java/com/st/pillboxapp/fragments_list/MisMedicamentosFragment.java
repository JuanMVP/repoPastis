package com.st.pillboxapp.fragments_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.adapter.MyMisMedicamentosRecyclerViewAdapter;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.interfaces.OnListMyMedicamentosInteractionListener;
import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.ResponseContainer;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.MyMedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;
import com.st.pillboxapp.util.Util;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisMedicamentosFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListMyMedicamentosInteractionListener mListener;
    private MyMisMedicamentosRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;


    public MisMedicamentosFragment() { }

    public static MisMedicamentosFragment newInstance(int columnCount) {
        MisMedicamentosFragment fragment = new MisMedicamentosFragment();
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
        View view = inflater.inflate(R.layout.fragment_mis_medicamentos_list, container, false);

        swipe = view.findViewById(R.id.swipeMedicamentos);
        swipe.setColorSchemeResources(R.color.azulSwipe, R.color.rojoSwipe);

        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();

            recyclerView = view.findViewById(R.id.listMedicamentos);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            //*Peticion a nuestra API*//
            cargarDatos(recyclerView);
        }

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

        return view;
    }

    public void cargarDatos(final RecyclerView recyclerView){
        MedicamentoService medicamentoService = ServiceGenerator.createService(MedicamentoService.class, Util.getToken(this.getActivity()), TipoAutenticacion.JWT);
        Call<ResponseContainer<Medicamento>> call = medicamentoService.getMisMedicamentos();

        call.enqueue(new Callback<ResponseContainer<Medicamento>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Medicamento>> call, Response<ResponseContainer<Medicamento>> response) {
                if(response.isSuccessful()){

                    adapter = new MyMisMedicamentosRecyclerViewAdapter(ctx, R.layout.fragment_mis_medicamentos, response.body().getRows(), mListener);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Medicamento>> call, Throwable t) {

                Toast.makeText(getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListMyMedicamentosInteractionListener) {
            mListener = (OnListMyMedicamentosInteractionListener) context;
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

    public void actualizarDatos(){
        cargarDatos(recyclerView);
    }


}
