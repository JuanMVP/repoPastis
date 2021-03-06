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
import com.st.pillboxapp.adapter.MyTomasRecyclerViewAdapter;
import com.st.pillboxapp.interfaces.OnListTomasInteractionListener;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.ResponseContainer;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.models.Tomas;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.responses.TomasResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.retrofit.services.TomasService;
import com.st.pillboxapp.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TomasFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_NOMBRE_INFO = "nombre";
    private int mColumnCount = 1;
    private OnListTomasInteractionListener mListener;
    private MyTomasRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipe;

    private String argNombrePersona;

    public TomasFragment() { }

    public static TomasFragment newInstance(Persona p) {
        TomasFragment fragment = new TomasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE_INFO, p.getNombre());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            argNombrePersona = getArguments().getString(ARG_NOMBRE_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tomas_list, container, false);

        swipe = view.findViewById(R.id.swipeTomas);
        swipe.setColorSchemeResources(R.color.azulSwipe, R.color.rojoSwipe);

        if (view instanceof SwipeRefreshLayout) {
            Context context = view.getContext();

            recyclerView = view.findViewById(R.id.listaTomas);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));

            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            //*Pticion a nuestra API*//
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

    private void cargarDatos(final RecyclerView recyclerView) {
        TomasService service = ServiceGenerator.createService(TomasService.class, Util.getToken(this.getActivity()), TipoAutenticacion.JWT);
        Call<ResponseContainer<Tomas>> call = service.getTomas();

        call.enqueue(new Callback<ResponseContainer<Tomas>>() {
            @Override
            public void onResponse(Call<ResponseContainer<Tomas>> call, Response<ResponseContainer<Tomas>> response) {
                if(response.isSuccessful()){

                    adapter = new MyTomasRecyclerViewAdapter(ctx, R.layout.fragment_tomas, response.body().getRows(), mListener);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<Tomas>> call, Throwable t) {

                Toast.makeText(getContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void onAttach(Context context) {
        ctx = context;
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

    public void actualizarDatos(){
        cargarDatos(recyclerView);
    }

}
