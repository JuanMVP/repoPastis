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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.st.pillboxapp.R;

import com.st.pillboxapp.interfaces.OnListPersonasInteractionListener;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.OneUserResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.UserService;
import com.st.pillboxapp.util.Util;

import java.io.Console;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PersonasFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListPersonasInteractionListener mListener;
    private MyPersonasRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    public PersonasFragment() { }

    public static PersonasFragment newInstance(int columnCount) {
        PersonasFragment fragment = new PersonasFragment();
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
        View view = inflater.inflate(R.layout.fragment_personas_list, container, false);

        swipe = view.findViewById(R.id.swipePersonas);
        swipe.setColorSchemeResources(R.color.azulSwipe, R.color.rojoSwipe);

        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();
            recyclerView = view.findViewById(R.id.listPersonas);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //*Petición a nuestra API*//
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

    public void cargarDatos(final RecyclerView recyclerView) {
        UserService userService = ServiceGenerator.createService(UserService.class, Util.getToken(this.getActivity()), TipoAutenticacion.JWT);
        Call<OneUserResponse> call = userService.oneUserById(Util.getUserId(this.getActivity()));

        call.enqueue(new Callback<OneUserResponse>() {
            @Override
            public void onResponse(Call<OneUserResponse> call, Response<OneUserResponse> response) {
                if (response.isSuccessful()) {
                    adapter = new MyPersonasRecyclerViewAdapter(ctx, R.layout.fragment_personas, response.body().getPersonas(), mListener);
                    recyclerView.setAdapter(adapter);
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

    @Override
    public void onAttach(Context context) {
        ctx = context;
        super.onAttach(context);
        if (context instanceof OnListPersonasInteractionListener) {
            mListener = (OnListPersonasInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListPersonasInteractionListener");
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
