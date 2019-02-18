package com.st.pillboxapp.responses;

import com.st.pillboxapp.models.Tomas;

import java.util.List;

public class TomasResponse {

    private String id;
    private List<Tomas> listaTomas;


    public TomasResponse(){

    }


    public TomasResponse(String id, List<Tomas> listaTomas) {
        this.id = id;
        this.listaTomas = listaTomas;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Tomas> getListaTomas() {
        return listaTomas;
    }

    public void setListaTomas(List<Tomas> listaTomas) {
        this.listaTomas = listaTomas;
    }


    @Override
    public String toString() {
        return "TomasResponse{" +
                "id='" + id + '\'' +
                ", listaTomas=" + listaTomas +
                '}';
    }
}
