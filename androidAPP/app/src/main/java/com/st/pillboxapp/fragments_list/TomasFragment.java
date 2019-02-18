package com.st.pillboxapp.fragments_list;

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
import com.st.pillboxapp.fragments_list.dummy.DummyContent;
import com.st.pillboxapp.fragments_list.dummy.DummyContent.DummyItem;
import com.st.pillboxapp.interfaces.OnListTomasInteractionListener;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.TomasResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.TomasService;
import com.st.pillboxapp.retrofit.services.UserService;
import com.st.pillboxapp.util.Util;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TomasFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListTomasInteractionListener mListener;
    private MyTomasRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TomasFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TomasFragment newInstance(Persona columnCount) {
        TomasFragment fragment = new TomasFragment();
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
        View view = inflater.inflate(R.layout.fragment_tomas_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listaTomas);

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            cargarDatos(recyclerView);

        }
        return view;
    }

    private void cargarDatos(final RecyclerView recyclerView) {
        TomasService tomasService = ServiceGenerator.createService(TomasService.class, Util.getToken(this.getActivity()), TipoAutenticacion.JWT);
        Call<TomasResponse> call = tomasService.getTomas();

        call.enqueue(new Callback<TomasResponse>() {
            @Override
            public void onResponse(Call<TomasResponse> call, Response<TomasResponse> response) {
                if(response.isSuccessful()){

                    adapter = new MyTomasRecyclerViewAdapter(ctx,R.layout.fragment_tomas,response.body().getListaTomas(),mListener);
                    recyclerView.setAdapter(adapter);

                }else{
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_LONG).show();


                }
            }

            @Override
            public void onFailure(Call<TomasResponse> call, Throwable t) {

            }
        });


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListTomasInteractionListener) {
            mListener = (OnListTomasInteractionListener) context;
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


}
