package com.example.alejandro.udlamsg.Interfaz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.alejandro.udlamsg.R;
import com.example.alejandro.udlamsg.lista.ListaChats;
import com.example.alejandro.udlamsg.lista.ListaCursos;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import android.content.Intent;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar barra = (Toolbar) findViewById(R.id.toolb);
        setSupportActionBar(barra);
        setTitle("");
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),getApplicationContext(),Principal.this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id){
            case R.id.opcion1:
                Intent intentooo=new Intent(Principal.this,MainActivity.class);
                startActivity(intentooo);
                break;
            case R.id.opcion2:
                Intent intentooo2=new Intent(Principal.this,creditos.class);
                startActivity(intentooo2);
                break;
            case R.id.opcion3:
                Intent intentooo3=new Intent(Principal.this,Iniciarsesion.class);
                startActivity(intentooo3);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        public static Context Contexto;
        public static ListView Chats,Cursos;
        public static Activity Interfaz;
        ListView listView_lista_de_amigos;
        ListAdapter adaptador;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, Context contexto, Activity interfaz) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            Contexto=contexto;
            Interfaz=interfaz;
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                View rootView = inflater.inflate(R.layout.fragment_chats, container, false);
                Chats=(ListView) rootView.findViewById(R.id.listView_chats);
                CargarChats(Chats);
                return rootView;
            }else {
                View rootView = inflater.inflate(R.layout.fragment_fragmento__cursos, container, false);
               // Intent i = new Intent() ;
               // Bundle Bun = i.getExtras();
                //int numero = (int) Bun.get("logueoResult");

                listView_lista_de_amigos = (ListView) rootView.findViewById(R.id.listView_curso);
                //tes = (TextView) findViewById(R.id.tex);
               // new SoapCURSOS().execute(1);
                return rootView;
            }

        }
        public void CargarChats(final ListView chat){
            ListaChats listachat;
            ArrayList<String> contactos=new ArrayList<>();
            contactos.add("Cristian Cañon");
            contactos.add("Camila Castro");
            contactos.add("Brenda Palacios");
            contactos.add("Johanna Castro");
            contactos.add("Edwin Millan");
            contactos.add("Jesus Collazos");
            contactos.add("Nataly Osorio");
            ArrayList<String> conver=new ArrayList<>();
            conver.add("a que horas es la clase");
            conver.add("no tenemos cti 2");
            conver.add("hoy es la entrega del proyecto");
            conver.add("mas tarde voy a su casa mor");
            conver.add("ingeniero tenia una duda");
            conver.add("a que horas es la clase");
            conver.add("no tenemos cti 2");
            listachat=new ListaChats(Contexto,contactos,conver);
            chat.setAdapter(listachat);
            chat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intento2=new Intent(Interfaz, com.example.alejandro.udlamsg.Interfaz.chat.class);
                    startActivity(intento2);
                }
            });
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Context contexto;
        Activity Interfaz;
        public SectionsPagerAdapter(FragmentManager fm,Context contexto,Activity interfaz) {
            super(fm);
            this.contexto=contexto;
            this.Interfaz=interfaz;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,contexto, Interfaz);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CHATS";
                case 1:
                    return "GRUPOS";
            }
            return null;
        }
    }
}
