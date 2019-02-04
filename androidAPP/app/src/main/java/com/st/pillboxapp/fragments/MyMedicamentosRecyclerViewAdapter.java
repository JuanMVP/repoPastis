package com.st.pillboxapp.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments.MedicamentosFragment.OnListFragmentInteractionListener;
import com.st.pillboxapp.fragments.dummy.DummyContent.DummyItem;
import com.st.pillboxapp.models.Medicamento;

import java.util.List;

public class MyMedicamentosRecyclerViewAdapter extends RecyclerView.Adapter<MyMedicamentosRecyclerViewAdapter.ViewHolder> {

    private final List<Medicamento> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context ctx;

    public MyMedicamentosRecyclerViewAdapter(Context context, int layout, List<Medicamento> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_medicamentos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombreMedicamento.setText(holder.mItem.getNombre());
        Glide.with(ctx).load(holder.mItem.getImagenUrl()).into(holder.imagenMedicamemto);


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombreMedicamento;
        public final ImageView imagenMedicamemto;
        public Medicamento mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombreMedicamento = view.findViewById(R.id.nombreMedicamento);
            imagenMedicamemto = view.findViewById(R.id.imagenMedicamento);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", nombreMedicamento=" + nombreMedicamento +
                    ", imagenMedicamemto=" + imagenMedicamemto +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}
