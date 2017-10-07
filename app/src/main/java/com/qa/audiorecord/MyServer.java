package com.qa.audiorecord;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by sev_user on 28-Feb-15.
 */
public class MyServer implements Runnable{
    ServerSocket mServerSocket;
    Socket mSocket;

    DataInputStream mDataInputStream;
    DataOutputStream mDataOutputStream;

    String data="";
    Context mContext;

    public MyServer(Context context) {
        try {
            Toast.makeText(context,"Server is starting...",Toast.LENGTH_LONG).show();
            mContext = context;
            this.mServerSocket = new ServerSocket(6866);
        } catch (IOException e) {
            Log.e("MyServer==>","qnv96: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void connect(){
        try {
            Toast.makeText(mContext,"Server is listening...",Toast.LENGTH_LONG).show();
            mSocket = mServerSocket.accept();
            mDataInputStream = new DataInputStream(mSocket.getInputStream());
            mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
        } catch (IOException e) {
            Log.e("MyServer==>","qnv96: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void readData(){
        try {
            data = mDataInputStream.readUTF();
            Toast.makeText(mContext, "Data: " + data, Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("MyServer==>","qnv96: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void sendData(){
        try {
            mDataOutputStream.writeUTF("Data send to client");
            mDataOutputStream.flush();
        } catch (IOException e) {
            Log.e("MyServer==>","qnv96: " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            mDataOutputStream.close();
            mDataInputStream.close();

            mSocket.close();
            mServerSocket.close();
        } catch (IOException e) {
            Log.e("MyServer==>","qnv96: " + e.getLocalizedMessage());
            e.printStackTrace();
        }catch (Exception ex){
            Log.e("MyServer==>","qnv96: " + ex.getLocalizedMessage());
        }
    }

    /**
     * Starts executing the active part of the class' code. This method is
     * called when a thread is started that has been created with a class which
     * implements {@code Runnable}.
     */
    @Override
    public void run() {
        Looper.prepare();
        connect();
    }
}
