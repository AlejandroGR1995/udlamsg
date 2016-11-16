package com.example.alejandro.udlamsg.Interfaz;

import org.ksoap2.serialization.SoapObject;
import java.util.ArrayList;

public class Cursos {


    public String Materia;
    public int NumeroCurso;
    public int Codigo;


    Deserilization obj=new Deserilization();

    public ArrayList<Cursos> children;


    public Cursos (SoapObject object){
        obj.SoapDeserilize(this,object);
    }


}
