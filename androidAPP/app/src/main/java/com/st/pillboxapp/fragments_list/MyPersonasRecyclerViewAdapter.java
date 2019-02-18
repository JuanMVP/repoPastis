package com.st.pillboxapp.fragments_list;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.st.pillboxapp.R;

import com.st.pillboxapp.interfaces.OnListPersonasInteractionListener;
import com.st.pillboxapp.models.Persona;


import java.util.List;


public class MyPersonasRecyclerViewAdapter extends RecyclerView.Adapter<MyPersonasRecyclerViewAdapter.ViewHolder> {

    private final List<Persona> mValues;
    private final OnListPersonasInteractionListener mListener;
    private Context ctx;

    public MyPersonasRecyclerViewAdapter(Context ctx, int layout, List<Persona> items, OnListPersonasInteractionListener listener) {
        mValues = items;
        mListener = listener;
        this.ctx = ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_personas, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nombrePersona.setText(holder.mItem.getNombre());
        holder.fechaNacPersona.setText(holder.mItem.getFecha_nacimiento());

        //*Click para borrar una persona*//
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeleteBtnClick(holder.mItem.getId(), holder.mItem.getNombre());
            }
        });

        //*Click para editar una persona*//
        holder.btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditPersonaClick(holder.mItem);
            }
        });

        //*Click corto para ver info una persona*//
        holder.infoPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickPersona(holder.mItem);
            }

        });

    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombrePersona;
        public final TextView fechaNacPersona;
        public Persona mItem;
        public final Button btnDelete;
        public final CardView infoPersona;
        public final Button btnEditar;
        //public final CardView elementoPersona;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombrePersona = view.findViewById(R.id.addNombrePersona);
            fechaNacPersona = view.findViewById(R.id.addFechaNacPersona);
            btnDelete = view.findViewById(R.id.btnDeletePersona);
            infoPersona = view.findViewById(R.id.cardViewPersonas);
            btnEditar = view.findViewById(R.id.btnEditPersona);
            //elementoPersona = view.findViewById(R.id.cardView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nombrePersona.getText() + "'";
        }
    }
}

