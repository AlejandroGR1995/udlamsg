package com.example.alejandro.udlamsg.Interfaz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alejandro.udlamsg.R;

public class ListaAdapterCompañeros extends ArrayAdapter<String> {

    public String[] Nombre;
    public String[] Foto;
    public  String[] Estado;
    Context context;

    public ListaAdapterCompañeros(Context context1, String[] fotos, String[] nombres ){
        super(context1,R.layout.activity_amigo, nombres);
        this.Nombre=nombres;
        this.Foto=fotos;
        this.context = context1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View rowView=inflater.inflate(R.layout.activity_amigo,parent, false);
        TextView nombre=(TextView)rowView.findViewById(R.id.Nomb);
        TextView  estado = (TextView)rowView.findViewById(R.id.Esta);
        ImageView imageView=(ImageView)rowView.findViewById(R.id.imagenamigos);
        try {
            byte[]   imagen = Base64.decode(Foto[position],Base64.DEFAULT);
            Bitmap m = BitmapFactory.decodeByteArray(imagen, 0, imagen.length);
            imageView.setImageBitmap(m);

        }catch (Exception e){
        }
        nombre.setText(Nombre[position]);
        return rowView;
    }
}