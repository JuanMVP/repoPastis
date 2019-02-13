package com.st.pillboxapp.fragments_list;

import android.content.Context;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.st.pillboxapp.R;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;

import com.st.pillboxapp.models.Resultado;


import java.util.List;

public class MyMedicamentosRecyclerViewAdapter extends RecyclerView.Adapter<MyMedicamentosRecyclerViewAdapter.ViewHolder> {

    private final List<Resultado> mValues;
    private final OnListMedicamentosInteractionListener mListener;
    private Context ctx;

    public MyMedicamentosRecyclerViewAdapter(Context context, int layout, List<Resultado> items, OnListMedicamentosInteractionListener listener) {
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
        if (holder.mItem.getFotos() != null) {

            Glide.with(ctx).load(holder.mItem.getFotos().get(0).getUrl()).into(holder.imagenMedicamemto);

        } else {
            holder.imagenMedicamemto.setImageResource(R.drawable.ic_no_photo);
        }


        //*Para aumentar la imagen del medicamento al hacer click*//
        final ImagePopup imagePopup = new ImagePopup(ctx);
        configurarPopUp(imagePopup);

        imagePopup.initiatePopup(holder.imagenMedicamemto.getDrawable());

        holder.imagenMedicamemto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.viewPopup();

            }
        });

        //*Click para que se abra el DialogFragment (AddMedicamentoFragment)*//
        holder.cardViewMedicamentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListener.onClickMedicamento(holder.mItem);
            }
        });

        //*Click para que se abra el activity de añadir medicamento*//
        holder.btnAddMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickBtnAddMedicamento(holder.mItem.getNregistro());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nombreMedicamento;
        public final ImageView imagenMedicamemto;
        public final CardView cardViewMedicamentos;
        public final ImageButton btnAddMedicamento;
        public Resultado mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombreMedicamento = view.findViewById(R.id.nombreMedicamento);
            imagenMedicamemto = view.findViewById(R.id.imagenMedicamento);
            cardViewMedicamentos = view.findViewById(R.id.cardViewMedicamentos);
            btnAddMedicamento = view.findViewById(R.id.anadirMedicamento);

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

    /**Métodos propios **/

    public void configurarPopUp(ImagePopup imagePopup) {

        imagePopup.setWindowHeight(400);
        imagePopup.setWindowWidth(400);
        imagePopup.setFullScreen(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

    }
}
