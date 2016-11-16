package com.example.alejandro.udlamsg.Interfaz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;

import com.example.alejandro.udlamsg.R;
import com.example.alejandro.udlamsg.lista.ListaChats;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class ListaIntegrantes extends AppCompatActivity {


    String[] Nombre = new String[]{
            "Nubia",
            "Camila",
            "Eduardo",
    };

    String[] Estado = new String[]{
            "en linea",
            "Ausente",
            "En linea",
    };


    String[] ultimomensaje = new String[]{
            "hola",
            "amor te quiero",
            "muy bien, saco 5,0",
    };

    String[] hora = new String[]{
            "09:11 am",
            "02:28 pm",
            "04:02 pm",
    };


    Integer[] image = new Integer[]{

            new Integer(R.drawable.logo),
            new Integer(R.drawable.logo),
            new Integer(R.drawable.logo)

    };

    ListView listViewcontactos;
    ListAdapter adaptador;
    TabHost tab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_integrantes);
        Intent i = getIntent();
        Bundle Bun = i.getExtras();
        int numero = (int) Bun.get("codigo");
        listViewcontactos = (ListView) findViewById(R.id.lista_integrantes);
        SoapContactos c = new SoapContactos();
        c.execute(numero);
    }

    private class SoapContactos extends AsyncTask<Integer, ArrayList<Estudiantes>, ArrayList<Estudiantes>> {
        ArrayList<Estudiantes> webrespons;

        @Override
        protected ArrayList<Estudiantes> doInBackground(Integer... params) {
            webrespons = new ArrayList<Estudiantes>();
            try {
                final String NAMESPACE = "http://duban.org/";
                final String URL = "http://52.36.238.241/DUban/Web_Service.asmx";
                final String SOAP_ACTION = "http://duban.org/matriculados";
                final String METHOD_NAME = "matriculados";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                PropertyInfo pi = new PropertyInfo();
                pi.setName("codigo");
                //int j= Integer.parseInt(params[0]);
                pi.setValue(params[0].intValue());
                pi.setType(Integer.class);
                request.addProperty(pi);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                Deserilization oj = new Deserilization();
                androidHttpTransport.call(SOAP_ACTION, envelope);


                Persona person = new Persona((SoapObject) envelope.getResponse());
                ArrayList<Estudiantes> arrayList = new ArrayList<Estudiantes>();
                arrayList = oj.SoapDeserializeArray(Estudiantes.class, (SoapObject) (envelope.getResponse()));
                SoapObject response = (SoapObject) envelope.getResponse();
                webrespons = arrayList;


            } catch (Exception e) {
                String exe = e.toString();
            }
            return webrespons;
        }


        @Override
        protected void onPostExecute(final ArrayList<Estudiantes> resultado) {

            int cantidad = resultado.size();
            String[] nombres = new String[cantidad];
            int[] codigo = new int[cantidad];
            String[] imagenes = new String[cantidad];


            for (int i = 0; i < cantidad; i++) {
                nombres[i] = resultado.get(i).NombreDeEstudiantes;
                codigo[i] = resultado.get(i).Id_estudiantes;
                imagenes[i] = resultado.get(i).Foto;
            }

            adaptador = new ListaAdapterCompañeros(getApplicationContext(), imagenes, nombres);
            listViewcontactos.setAdapter(adaptador);
            listViewcontactos.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    String itemValue = (String) listViewcontactos.getItemAtPosition(position);
                    Intent inten = new Intent(ListaIntegrantes.this,chat.class);
                    inten.putExtra("nombre",Nombre[itemPosition]);
                    inten.putExtra("foto",image[itemPosition]);
                    startActivity(inten);
                }
            });
        }
    }
}



