package com.st.pillboxapp.fragments_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.st.pillboxapp.R;

import com.st.pillboxapp.fragments_list.dummy.DummyContent.DummyItem;
import com.st.pillboxapp.interfaces.OnListTomasInteractionListener;
import com.st.pillboxapp.models.Tomas;

import java.util.List;


public class MyTomasRecyclerViewAdapter extends RecyclerView.Adapter<MyTomasRecyclerViewAdapter.ViewHolder> {

    private final List<Tomas> mValues;
    private final OnListTomasInteractionListener mListener;
    private Context ctx;

    public MyTomasRecyclerViewAdapter(Context ctx, int layout, List<Tomas> items, OnListTomasInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tomas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombreMedicamentoInfo.setText(holder.mItem.getMedicamento().getNombre());
        holder.diaSemanaToma.setText(holder.mItem.getDia_semana());
        holder.horaToma.setText(holder.mItem.getHora_toma());


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombreMedicamentoInfo;
        public final TextView diaSemanaToma;
        public final TextView horaToma;
        public Tomas mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombreMedicamentoInfo = view.findViewById(R.id.nombreMedicamento);
            diaSemanaToma = view.findViewById(R.id.diaSemana);
            horaToma = view.findViewById(R.id.horaToma);
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mView=" + mView +
                    ", nombreMedicamentoInfo=" + nombreMedicamentoInfo +
                    ", diaSemanaToma=" + diaSemanaToma +
                    ", horaToma=" + horaToma +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}
