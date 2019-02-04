package com.st.pillboxapp.ui;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments.PersonasFragment;
import com.st.pillboxapp.responses.OneUserResponse;

public class MenuActivity extends AppCompatActivity implements PersonasFragment.OnListFragmentInteractionListener {

    private ImageButton buttonListarPersonas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        buttonListarPersonas = findViewById(R.id.buttonListarPersonas);



    }

    @Override
    public void onListFragmentInteraction(OneUserResponse item) {

    }
}
