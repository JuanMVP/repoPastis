package com.st.pillboxapp.fragments_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.st.pillboxapp.R;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.models.Resultado;

import java.util.List;

public class MyMisMedicamentosRecyclerViewAdapter extends RecyclerView.Adapter<MyMisMedicamentosRecyclerViewAdapter.ViewHolder> {

    private final List<Resultado> mValues;
    private final OnListMedicamentosInteractionListener mListener;
    private Context ctx;

    public MyMisMedicamentosRecyclerViewAdapter(Context context, int layaout, List<Resultado> items, OnListMedicamentosInteractionListener listener) {
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
        if (holder.mItem.getFotos() != null) {

            Glide.with(ctx).load(holder.mItem.getFotos().get(0).getUrl()).into(holder.imagenMisMedicamento);

        } else {
            holder.imagenMisMedicamento.setImageResource(R.drawable.ic_no_photo);
        }

        //*Para aumentar la imagen del medicamento al hacer click*//
        final ImagePopup imagePopup = new ImagePopup(ctx);
        configurarPopUp(imagePopup);

        imagePopup.initiatePopup(holder.imagenMisMedicamento.getDrawable());

        holder.imagenMisMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePopup.viewPopup();

            }
        });

        //*Click para que se abra el activity de añadir medicamento*//
        holder.btnAddMedicamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "Se ha añadido correctamente", Toast.LENGTH_SHORT).show();
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
        public final ImageButton btnAddMedicamento;
        public Resultado mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nombreMisMedicamento =  view.findViewById(R.id.nombreMisMedicamento);
            imagenMisMedicamento =  view.findViewById(R.id.imagenMisMedicamento);
            btnAddMedicamento =  view.findViewById(R.id.anadirMisMedicamento);
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

    /**
     * Métodos propios
     **/

    public void configurarPopUp(ImagePopup imagePopup) {

        imagePopup.setWindowHeight(400);
        imagePopup.setWindowWidth(400);
        imagePopup.setFullScreen(true);
        imagePopup.setHideCloseIcon(true);
        imagePopup.setImageOnClickClose(true);

    }
}
