package com.example.alejandro.udlamsg.Interfaz;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alejandro.udlamsg.R;
import com.example.alejandro.udlamsg.Websocketclient;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;

public class chat extends AppCompatActivity {
    ListView list_message;
    ImageView photo;
    ListaIntegrantes inte = new ListaIntegrantes();
    static WebSocketClient nWebSocketClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final Websocketclient globalwebsocket = (Websocketclient) getApplicationContext();
        nWebSocketClient = globalwebsocket.getmWebSocketClient();


    }

    private static class Soapconeccion extends Activity {
        EditText mensaje=(EditText) findViewById(R.id.editText_Escribirmensaje);
        ImageButton  enviarmensaje=(ImageButton)findViewById(R.id.button_enviarmensaje);
        TextView mensa = (TextView)findViewById(R.id.textview_mensaje);

        private void sendMessage(View view) {
            Gson data = new Gson();

            Message send = new Message();
            send.setCodeEmisor("1111");
            send.setType("message");
            send.setGroup("01");
            send.setNick("carito21");
            send.setSend(mensaje.getText().toString());

            String Datamessage = data.toJson(send);
            nWebSocketClient.send(Datamessage);
            mensa.setText(mensa.getText() + "\n" + send.getCodeEmisor() + ":" + send.getSend());
            mensa.setText("");

        }

    }
}