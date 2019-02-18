package com.st.pillboxapp.fragments_list;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.st.pillboxapp.R;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.PersonaConMed;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.OneUserResponse;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;
import com.st.pillboxapp.retrofit.services.UserService;
import com.st.pillboxapp.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InfoPersonaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class InfoPersonaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_NOMBRE = "nombre";
    private static final String ARG_FECHA_NACIMIENTO = "fecha_nacimiento";
    private EditText nombreInfoPersona,fechaInfoPersona;
    private View view;
    private Spinner spinnerMedicamentos;
    private String argNombrePersona, argFechaPersona;
    private List<Resultado> listaMedicamentos;
    private ArrayAdapter<Resultado> medicamentos;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public InfoPersonaFragment() {
        // Required empty public constructor
    }

    public static InfoPersonaFragment newInstance(Persona p) {
        InfoPersonaFragment fragment = new InfoPersonaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NOMBRE, p.getNombre());
        args.putString(ARG_FECHA_NACIMIENTO, p.getFecha_nacimiento());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            argNombrePersona = getArguments().getString(ARG_NOMBRE);
            argFechaPersona = getArguments().getString(ARG_FECHA_NACIMIENTO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_info_persona, container, false);
        nombreInfoPersona = view.findViewById(R.id.nombreInfoPersona);
        fechaInfoPersona = view.findViewById(R.id.fechaInfoPersona);
        spinnerMedicamentos = view.findViewById(R.id.spinnerMedicamentosInfoPersona);

        nombreInfoPersona.setText(argNombrePersona);
        fechaInfoPersona.setText(argFechaPersona);
        cargarSpinner();



        // Inflate the layout for this fragment
        return view;
    }

    private void cargarSpinner() {

        PersonaService personaService = ServiceGenerator.createService(PersonaService.class, Util.getToken(this.getActivity()), TipoAutenticacion.JWT);
        Call<PersonaResponse> call = personaService.findOne(Util.getUserId(this.getActivity()));

        call.enqueue(new Callback<PersonaResponse>() {
            @Override
            public void onResponse(Call<PersonaResponse> call, Response<PersonaResponse> response) {
                if(response.isSuccessful()){
                    listaMedicamentos = new ArrayList<Resultado>(response.body().getMedicamentos());
                    medicamentos = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,listaMedicamentos);
                    spinnerMedicamentos.setAdapter(medicamentos);
                }else{
                    Toast.makeText(getContext(), "Error al cargar los medicamentos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PersonaResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error de conexión", Toast.LENGTH_LONG).show();


            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
