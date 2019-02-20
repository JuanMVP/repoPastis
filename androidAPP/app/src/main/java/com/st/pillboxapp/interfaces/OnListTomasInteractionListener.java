package com.st.pillboxapp.interfaces;

import com.st.pillboxapp.models.Medicamento;
import com.st.pillboxapp.models.Persona;

public interface OnListTomasInteractionListener {

    public void onClickPersonaToma(Persona p);

    public void onAddToma(Medicamento medicamento);


}
