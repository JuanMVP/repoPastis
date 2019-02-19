package com.st.pillboxapp.interfaces;

import com.st.pillboxapp.models.Persona;

public interface OnListPersonasInteractionListener {

    public void onDeletePersonaClick(String id, String nombre);

    public void onEditPersonaClick(Persona p);

    public void onAddPersonaClick(Persona p);

    public void onClickPersona(Persona p);

}
