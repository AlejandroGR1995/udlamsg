package com.example.alejandro.udlamsg;

/**
 * Created by ALEJANDRO on 15/11/2016.
 */
import android.app.Application;
import org.java_websocket.client.WebSocketClient;


public class Websocketclient extends Application {

    public WebSocketClient mWebSocketClient;

    public void setmWebSocketClient(WebSocketClient mWebSocketClient) {
        this.mWebSocketClient = mWebSocketClient;
    }

    public WebSocketClient getmWebSocketClient() {
        return mWebSocketClient;
    }





}
