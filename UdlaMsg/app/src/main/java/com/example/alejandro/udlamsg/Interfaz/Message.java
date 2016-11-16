package com.example.alejandro.udlamsg.Interfaz;

/**
 * Created by ALEJANDRO on 15/11/2016.
 */

public class Message {

    private String type;
    private String group;
    private String codeEmisor;
    private String codeReceptor;
    private String nick;
    private String send;
    private String file;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getCodeEmisor() {
        return codeEmisor;
    }

    public void setCodeEmisor(String codeEmisor) {
        this.codeEmisor = codeEmisor;
    }

    public String getCodeReceptor() {
        return codeReceptor;
    }

    public void setCodeReceptor(String codeReceptor) {
        this.codeReceptor = codeReceptor;
    }
}
