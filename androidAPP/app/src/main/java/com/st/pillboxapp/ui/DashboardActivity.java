package com.st.pillboxapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.st.pillboxapp.R;
import com.st.pillboxapp.fragment_dialog.AddMedicamentoFragment;
import com.st.pillboxapp.fragment_dialog.AddPersonaFragment;
import com.st.pillboxapp.fragment_dialog.DeletePersonaFragment;
import com.st.pillboxapp.fragment_dialog.EditPersonaFragment;
import com.st.pillboxapp.fragments_list.InfoPersonaFragment;
import com.st.pillboxapp.fragments_list.MedicamentosFragment;
import com.st.pillboxapp.fragments_list.PersonasFragment;
import com.st.pillboxapp.interfaces.OnListMedicamentosInteractionListener;
import com.st.pillboxapp.interfaces.OnListPersonasInteractionListener;
import com.st.pillboxapp.models.Persona;
import com.st.pillboxapp.models.Resultado;
import com.st.pillboxapp.util.Util;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnListPersonasInteractionListener, OnListMedicamentosInteractionListener, InfoPersonaFragment.OnFragmentInteractionListener {


    private Fragment f;
    private FloatingActionButton fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.fab);

        toolbar.setTitle("Mis Personas");
        setSupportActionBar(toolbar);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new PersonasFragment(), "mainFragment")
                .commit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPersonaFragment f = AddPersonaFragment.newInstance();
                f.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("mainFragment");
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.detach(currentFragment);
                        fragmentTransaction.attach(currentFragment);
                        fragmentTransaction.commit();
                    }
                });
                FragmentManager fm = getSupportFragmentManager();
                f.show(fm, "AñadirPersona");
            }
        });

        mostrarInfoUsuarioMenu();

    }

    /**
     * Métodos propios
     **/

    //*Método para mostrar el nombre, email y foto del usuario logueado en el menú lateral*//
    public void mostrarInfoUsuarioMenu() {

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

        name.setText(Util.getNombreUser(DashboardActivity.this).substring(0, 1).toUpperCase() + Util.getNombreUser(DashboardActivity.this).substring(1));
        email.setText(Util.getEmailUser(DashboardActivity.this));
        Glide.with(this).load(Util.getPhotoUser(DashboardActivity.this)).apply(RequestOptions.circleCropTransform()).into(iv);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Opciones del menú lateral
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        f = null;

        if (id == R.id.nav_misPersonas) {

            f = new PersonasFragment();
            fab.show();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddPersonaFragment f = AddPersonaFragment.newInstance();
                    Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("mainFragment");
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.detach(currentFragment);
                    fragmentTransaction.attach(currentFragment);
                    fragmentTransaction.commit();
                    FragmentManager fm = getSupportFragmentManager();
                    f.show(fm, "mainFragment");
                }
            });

            toolbar.setTitle("Mis Personas");

        } else if (id == R.id.nav_buscarMedicamento) {

            f = new MedicamentosFragment();
            fab.hide();
            toolbar.setTitle("Buscar Medicamentos");

        } else if (id == R.id.nav_logout) {
            Util.clearSharedPreferences(DashboardActivity.this);

            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, f, "mainFragment").commit();
            return true;
        }
        return true;
    }

    // Métodos onClick
    @Override
    public void onEditPersonaClick(Persona p) {

        EditPersonaFragment f = EditPersonaFragment.newInstance(p);
        f.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("mainFragment");
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        f.show(fm, "EditarPersona");

    }

    @Override
    public void onAddPersonaClick(Persona p) {
        AddPersonaFragment f = AddPersonaFragment.newInstance();
        f.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("mainFragment");
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        f.show(fm, "AñadirPersona");
    }

    @Override
    public void onClickPersona(Persona p) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, InfoPersonaFragment.newInstance(p))
                .commit();

    }

    @Override
    public void onDeleteBtnClick(String id, String nombre) {

        DeletePersonaFragment f = DeletePersonaFragment.newInstance(id, nombre);
        f.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentByTag("mainFragment");
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.detach(currentFragment);
                fragmentTransaction.attach(currentFragment);
                fragmentTransaction.commit();
            }
        });
        FragmentManager fm = getSupportFragmentManager();
        f.show(fm, "DeletePersona");

    }

    @Override
    public void onClickBtnAddMedicamento(Resultado resultado) {
        AddMedicamentoFragment f = AddMedicamentoFragment.newInstance(resultado);
        FragmentManager fm = getSupportFragmentManager();
        f.show(fm, "AñadirMedicamento");

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
