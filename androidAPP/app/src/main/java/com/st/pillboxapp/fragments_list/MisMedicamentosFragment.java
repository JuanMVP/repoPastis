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
import android.widget.EditText;
import android.widget.ImageButton;

import com.st.pillboxapp.R;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;

public class MisMedicamentosFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListMedicamentosInteractionListener mListener;
    private MyMisMedicamentosRecyclerViewAdapter adapter;
    private Context ctx;
    private RecyclerView recyclerView;
    private EditText nombreMisMedicamento;
    private ImageButton btnMisMedicamento;
    private SwipeRefreshLayout swipe;


    public MisMedicamentosFragment() {
    }

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

        nombreMisMedicamento = view.findViewById(R.id.nombreMisMedicamento);
        btnMisMedicamento = view.findViewById(R.id.anadirMisMedicamento);

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

            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // actualizar datos
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

}
