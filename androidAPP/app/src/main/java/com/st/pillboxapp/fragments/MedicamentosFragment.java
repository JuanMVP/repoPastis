package com.st.pillboxapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments.dummy.DummyContent;
import com.st.pillboxapp.fragments.dummy.DummyContent.DummyItem;
import com.st.pillboxapp.responses.MedicamentoResponse;
import com.st.pillboxapp.retrofit.generator.ServiceApiGenerator;
import com.st.pillboxapp.retrofit.services.MedicamentoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MedicamentosFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    MyMedicamentosRecyclerViewAdapter adapter;
    private Context ctx;
    RecyclerView recyclerView;


    public MedicamentosFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
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

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.list);
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            MedicamentoService medicamentoService = ServiceApiGenerator.createService(MedicamentoService.class);
            Call<MedicamentoResponse> callMedicamento = medicamentoService.getMedicamentos("paracetamol");

            callMedicamento.enqueue(new Callback<MedicamentoResponse>() {
                @Override
                public void onResponse(Call<MedicamentoResponse> call, Response<MedicamentoResponse> response) {
                    if(response.isSuccessful()){
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
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }
}
