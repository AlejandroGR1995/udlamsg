package com.example.alejandro.udlamsg.Interfaz;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;

public class Persona {

    public int IdPersona;
    public String nombre;
    public String foto;

    Deserilization obj=new Deserilization();

    public ArrayList<Persona> children;


    public Persona(SoapObject object){
        obj.SoapDeserilize(this,object);
    }

}
