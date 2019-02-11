package com.st.pillboxapp.interfaces;

import com.st.pillboxapp.models.Persona;

public interface OnListPersonasInteractionListener {

    public void onDeleteBtnClick(String id, String nombre);

    public void onEditPersonaClick(Persona p);
}
