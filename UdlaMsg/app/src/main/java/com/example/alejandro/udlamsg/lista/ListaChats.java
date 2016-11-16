package com.example.alejandro.udlamsg.lista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.udlamsg.R;

import java.util.ArrayList;

public class ListaChats extends BaseAdapter {

    LayoutInflater inflado;
    Context contexto;
    ArrayList<String> Chat;
    ArrayList<String> Mensaje;

    public ListaChats(Context context, ArrayList<String> chats,ArrayList<String> mensaje){
        this.contexto=context;
        this.Chat=chats;
        this.Mensaje=mensaje;
    }
    @Override
    public int getCount() {
        return this.Chat.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        inflado = LayoutInflater.from(this.contexto);
        View vista =inflado.inflate(R.layout.activity_lista_chats, parent, false);

        TextView nombrechatpersona,horachat ,ultmensaje;
        ImageView fotoperfil;
        nombrechatpersona =(TextView) vista.findViewById(R.id.textView_nombre);
        horachat =(TextView)vista.findViewById(R.id.textView_horamens);
        ultmensaje=(TextView)vista.findViewById(R.id.textView_Ultimomens);

        nombrechatpersona.setText(Chat.get(i));
        horachat.setText("3:00pm");
        ultmensaje.setText(Mensaje.get(i));
        return vista;
    }
}
