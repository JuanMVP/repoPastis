package com.st.pillboxapp.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.st.pillboxapp.R;
import com.st.pillboxapp.fragments.AddMedicamentoFragment;
import com.st.pillboxapp.fragments.EditPersonaFragment;
import com.st.pillboxapp.fragments.MedicamentosFragment;
import com.st.pillboxapp.fragments.PersonasFragment;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.interfaces.OnListPersonasInteractionListener;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.models.TipoAutenticacion;
import com.st.pillboxapp.responses.PersonaResponse;
import com.st.pillboxapp.retrofit.generator.ServiceGenerator;
import com.st.pillboxapp.retrofit.services.PersonaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListPersonasInteractionListener, OnListMedicamentosInteractionListener {


    Fragment f;
    FloatingActionButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mis Personas");

        setSupportActionBar(toolbar);
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (f instanceof PersonasFragment) {

                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);

        ImageView iv = headerView.findViewById(R.id.picture);
        TextView name = headerView.findViewById(R.id.userName);
        TextView email = headerView.findViewById(R.id.emailUser);

        name.setText(prefs.getString("nombreUser", "").substring(0, 1).toUpperCase() + prefs.getString("nombreUser", "").substring(1));
        email.setText(prefs.getString("emailUser", ""));
        Glide.with(this).load(prefs.getString("fotoUser", "")).apply(RequestOptions.circleCropTransform()).into(iv);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new PersonasFragment())
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, AddPersonaActivity.class);
                startActivity(i);
                Toast.makeText(DashboardActivity.this, "Entro", Toast.LENGTH_LONG);
            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        f = null;

        int id = item.getItemId();

        if (id == R.id.nav_misPersonas) {
            fab = findViewById(R.id.fab);

            f = new PersonasFragment();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(DashboardActivity.this, AddPersonaActivity.class);
                    startActivity(i);
                    Toast.makeText(DashboardActivity.this, "Entro", Toast.LENGTH_LONG);
                }
            });
            fab.show();

            toolbar.setTitle("Mis Personas");

        } else if (id == R.id.nav_buscarMedicamento) {

            f = new MedicamentosFragment();
            fab.hide();
            toolbar.setTitle("Buscar Medicamentos");

        } else if (id == R.id.nav_logout) {
            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);

            SharedPreferences prefs =
                    getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            startActivity(i);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, f).commit();
            return true;
        }
        return true;
    }

    @Override
    public void onEditPersonaClick(Persona p) {

        EditPersonaFragment f = EditPersonaFragment.newInstance(p);
        FragmentManager fm = getSupportFragmentManager();
        f.show(fm, "EditarPersona");

    }


    @Override
    public void onDeleteBtnClick(String id, String nombre) {

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        PersonaService service = ServiceGenerator.createService(PersonaService.class, prefs.getString("token", ""), TipoAutenticacion.JWT);

        final Call<PersonaResponse> call = service.deleteOne(id);


        AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
        builder.setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
            public void onClick(final DialogInterface dialog, int id) {
                // User clicked OK button

                call.enqueue(new Callback<PersonaResponse>() {
                    @Override
                    public void onResponse(Call<PersonaResponse> call, Response<PersonaResponse> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            finish();
                            startActivity(getIntent());
                        } else {

                            dialog.dismiss();
                            Toast.makeText(DashboardActivity.this, "Error al borrar", Toast.LENGTH_LONG);
                        }
                    }

                    @Override
                    public void onFailure(Call<PersonaResponse> call, Throwable t) {

                    }
                });


            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog

                dialog.dismiss();


            }
        });

        builder.setTitle("¿Seguro que quiere borrar a " + nombre);

        AlertDialog dialog = builder.create();

        dialog.show();


    }

    /*@Override
    public void onClickMedicamento(String nregistro) {

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("nregistro", nregistro);
        editor.commit();

        startActivity(new Intent(DashboardActivity.this, AddMedicamentoActivity.class));
    }*/

    @Override
    public void onClickMedicamento(Resultado resultado) {

        AddMedicamentoFragment f = AddMedicamentoFragment.newInstance(resultado);
        FragmentManager fm = getSupportFragmentManager();
        f.show(fm, "AñadirMedicamento");


    }
}
