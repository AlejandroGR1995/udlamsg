package com.example.alejandro.udlamsg.Interfaz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.udlamsg.R;

public class ListaAdapterCompañerosdos extends ArrayAdapter<String> {

    public String[] Nombre;
    public Integer[] Foto;
    public String[] Ultimo;
    public String[] Hora;


    Context context;



    public ListaAdapterCompañerosdos(Context context1, Integer[] fotos, String[] nombres, String[] ultimo_mensaje, String[] hora) {
        super(context1,R.layout.activity_amigodos, nombres);
        this.Nombre=nombres;
        this.Foto=fotos;
        this.Ultimo = ultimo_mensaje;
        this.Hora = hora;

        this.context = context1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


        View rowView=inflater.inflate(R.layout.activity_amigodos,parent, false);

        TextView Nmbre=(TextView)rowView.findViewById(R.id.Nomb);
        TextView ultimo =(TextView)rowView.findViewById(R.id.Ultimo_mensaje);
        TextView hora =(TextView)rowView.findViewById(R.id.hora);
        ImageView imageView=(ImageView)rowView.findViewById(R.id.imagenamigos);



        Nmbre.setText(Nombre[position]);
        imageView.setImageResource(Foto[position].intValue());
        ultimo.setText(Ultimo[position]);
        hora.setText(Hora[position]);
        return rowView;
    }
}
