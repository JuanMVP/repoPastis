package com.st.pillboxapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.st.pillboxapp.R;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.interfaces.OnListMyMedicamentosInteractionListener;
import com.st.pillboxapp.interfaces.OnListTomasInteractionListener;
import com.st.pillboxapp.models.Medicamento;

import java.util.List;

public class MyMisMedicamentosRecyclerViewAdapter extends RecyclerView.Adapter<MyMisMedicamentosRecyclerViewAdapter.ViewHolder> {

    private final List<Medicamento> mValues;
    private final OnListMyMedicamentosInteractionListener mListener;
    private Context ctx;

    public MyMisMedicamentosRecyclerViewAdapter(Context context, int layaout, List<Medicamento> items, OnListMyMedicamentosInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mis_medicamentos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombreMisMedicamento.setText(holder.mItem.getNombre());

        Glide.with(ctx).load(holder.mItem.getImagenUrl()).into(holder.imagenMisMedicamento);

        //*Click para añadir tratamiento*//
        holder.btnAddMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAddTratamientoClick(holder.mItem);
            }
        });

        //*Click para borrar un tratamiento*//
        holder.btnDeleteMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteMisMedicamentosClick(holder.mItem.getNombre());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombreMisMedicamento;
        public final ImageView imagenMisMedicamento;
        public final ImageButton btnAddMedicamento, btnDeleteMedicamento;
        public Medicamento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombreMisMedicamento =  view.findViewById(R.id.nombreMisMedicamento);
            imagenMisMedicamento =  view.findViewById(R.id.imagenMisMedicamento);
            btnAddMedicamento =  view.findViewById(R.id.anadirMisMedicamento);
            btnDeleteMedicamento = view.findViewById(R.id.deleteMisMedicamentos);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", nombreMedicamento=" + nombreMisMedicamento +
                    ", imagenMedicamemto=" + imagenMisMedicamento +
                    ", mItem=" + mItem +
                    '}';
        }
    }

}
