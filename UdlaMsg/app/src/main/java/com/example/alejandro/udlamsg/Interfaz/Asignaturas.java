package com.example.alejandro.udlamsg.Interfaz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.example.alejandro.udlamsg.R;
import com.example.alejandro.udlamsg.Websocketclient;
import com.example.alejandro.udlamsg.lista.ListaCursos;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;


public class Asignaturas extends AppCompatActivity {
    ListView listView_lista_de_amigos;
    ListAdapter adaptador;
    ImageView fotografia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignaturas);
        Intent i =getIntent();
        Bundle Bun = i.getExtras();
        int numero = (int) Bun.get("logueoResult");
        listView_lista_de_amigos = (ListView) findViewById(R.id.lista_asignaturas);
        //tes = (TextView) findViewById(R.id.tex);
        new SoapCURSOS().execute(numero);
        new Soapconeccion().connectWebSocket();

    }
    public static class Soapconeccion extends Activity {
        final Websocketclient globalwebsocket=(Websocketclient)getApplicationContext();
        private WebSocketClient mWebSocketClient;
        private void connectWebSocket() {
            URI uri;
            try {
                uri = new URI("ws://192.168.1.5:2012");
            } catch (URISyntaxException e) {
                e.printStackTrace();
                return;
            }

            mWebSocketClient = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    Gson data = new Gson();
                    Message send = new Message();
                    send.setCodeEmisor("01");
                    send.setCodeReceptor("02");
                    send.setType("conected");
                    send.setNick("carito21");
                    String Datamessage = data.toJson(send);

                    mWebSocketClient.send(Datamessage);

                }

                @Override
                public void onMessage(String s) {


                    Gson data = new GsonBuilder().create();
                    final Message datasend =  data.fromJson(s, Message.class );

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView textView = (TextView)findViewById(R.id.messages);
                            textView.setText(textView.getText() + "\n" + datasend.getCodeEmisor()  + ":" + datasend.getSend());
                        }
                    });
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    Log.i("Websocket", "Closed " + s);
                    TextView textView = (TextView)findViewById(R.id.messages);
                    textView.setText("desconectado del servidor");
                }

                @Override
                public void onError(Exception e) {
                    Log.i("Websocket", "Error " + e.getMessage());
                    TextView textView = (TextView)findViewById(R.id.messages);
                    textView.setText("desconectado del servidor");
                }
            };
            mWebSocketClient.connect();
            globalwebsocket.setmWebSocketClient(mWebSocketClient);
        }
    }

    private class SoapCURSOS extends AsyncTask<Integer, ArrayList<Cursos>, ArrayList<Cursos>> {
        ArrayList<Cursos> webrespons;

        @Override
        protected ArrayList<Cursos> doInBackground(Integer... params) {
            webrespons = new ArrayList<Cursos>();
            try {
                final String NAMESPACE = "http://duban.org/";
                final String URL = "http://52.36.238.241/DUban/Web_Service.asmx";
                final String SOAP_ACTION = "http://duban.org/materias_matriculadas";
                final String METHOD_NAME = "materias_matriculadas";

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                PropertyInfo pi = new PropertyInfo();
                pi.setName("id");
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

                Cursos curso = new Cursos((SoapObject) envelope.getResponse());
                ArrayList<Cursos> arrayList = new ArrayList<Cursos>();
                arrayList = oj.SoapDeserializeArray(Cursos.class, (SoapObject) (envelope.getResponse()));
                SoapObject response = (SoapObject) envelope.getResponse();
                int r = response.getPropertyCount();
                for (int i = 0; i < r; i++) {

                }
                webrespons=arrayList;

            } catch (Exception e) {
                String exe = e.toString();
            }
            return webrespons;
        }

        @Override
        protected void onPostExecute(final ArrayList<Cursos> resultado) {

            int cantidad = resultado.size();
            final String[]  amigos = new String[cantidad];
            int[]  n = new int[cantidad];


            for (int i = 0; i < cantidad; i++) {
                amigos[i] = resultado.get(i).Materia;
                n[i] = resultado.get(i).NumeroCurso;

            }

            adaptador = new ListaCursos(getApplicationContext(), amigos);
            listView_lista_de_amigos.setAdapter(adaptador);
            listView_lista_de_amigos.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int itemPosition = position;
                    String itemValue = (String) listView_lista_de_amigos .getItemAtPosition(position);
                    Intent inten = new Intent(Asignaturas.this,ListaIntegrantes.class);
                    inten.putExtra("Materia",amigos[itemPosition]);
                    inten.putExtra("codigo", resultado.get(itemPosition).Codigo );

                    startActivity(inten);
                }
            });
        }
    }
}
