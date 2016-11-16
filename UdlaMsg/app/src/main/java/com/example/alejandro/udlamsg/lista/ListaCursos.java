package com.example.alejandro.udlamsg.lista;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.alejandro.udlamsg.R;

import java.util.ArrayList;

public class ListaCursos extends ArrayAdapter<String> {

    LayoutInflater inflater;
    Context context;
    String[] datos1;

    public ListaCursos(Context context, String[] Dato1){
        super(context,R.layout.activity_lista_cursos, Dato1);
        this.context=context;
        this.datos1=Dato1;
    }
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        inflater = LayoutInflater.from(this.context);
        View itemview =inflater.inflate(R.layout.activity_lista_cursos, parent, false);

        TextView nombregrupo;
        nombregrupo =(TextView) itemview.findViewById(R.id.textView_NombreGrupos);
        nombregrupo.setText(datos1[i]);
        return itemview;
    }
}
