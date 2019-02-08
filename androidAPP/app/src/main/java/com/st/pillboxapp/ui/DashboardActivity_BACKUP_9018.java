package com.st.pillboxapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.st.pillboxapp.fragments.MedicamentosFragment;
import com.st.pillboxapp.fragments.PersonasFragment;
import com.st.pillboxapp.fragments.dummy.DummyContent;
import com.st.pillboxapp.responses.OneUserResponse;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, PersonasFragment.OnListFragmentInteractionListener, MedicamentosFragment.OnListFragmentInteractionListener {

<<<<<<< HEAD
    Fragment f;
    FloatingActionButton fab;

=======
    private Toolbar toolbar;
>>>>>>> c214f6a364a34a136af56880693fced844e23ab3
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mis Personas");

        setSupportActionBar(toolbar);
        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

       fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f instanceof PersonasFragment) {

                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);

        ImageView iv =  headerView.findViewById(R.id.picture);
        TextView name = headerView.findViewById(R.id.userName);
        TextView email = headerView.findViewById(R.id.emailUser);

        name.setText(prefs.getString("nombreUser","").substring(0,1).toUpperCase()+ prefs.getString("nombreUser", "").substring(1));
        email.setText(prefs.getString("emailUser",""));
        Glide.with(this).load(prefs.getString("fotoUser","")).apply(RequestOptions.circleCropTransform()).into(iv);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contenedor, new PersonasFragment())
                .commit();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        f = null;

        int id = item.getItemId();

        if (id == R.id.nav_misPersonas) {

            f = new PersonasFragment();
<<<<<<< HEAD
            fab.show();

        } else if (id == R.id.nav_slideshow) {

            f = new MedicamentosFragment();
            fab.hide();

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

=======
            toolbar.setTitle("Mis Personas");
        } else if (id == R.id.nav_buscarMedicamento) {

            f = new MedicamentosFragment();
            toolbar.setTitle("Buscar Medicamentos");

        } else if(id == R.id.nav_logout){
            Intent i = new Intent(DashboardActivity.this, LoginActivity.class);

            SharedPreferences prefs =
                    getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.commit();
            startActivity(i);
            finish();
>>>>>>> c214f6a364a34a136af56880693fced844e23ab3
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        if(f != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, f).commit();
            return true;
        }
        return true;
    }

    @Override
    public void onListFragmentInteraction(OneUserResponse item) {

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}