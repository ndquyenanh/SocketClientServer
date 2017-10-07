package com.qa.myclient;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by sev_user on 28-Feb-15.
 */
public class MyClient implements Runnable {
    Socket mSocket;
    DataInputStream mDataInputStream;
    DataOutputStream mDataOutputStream;
    Context mContext;

    public MyClient(Context context) {
        mContext = context;
    }

    private void connect(){
        try {
            mSocket = new Socket("10.168.68.151",6866);
            mDataInputStream = new DataInputStream(mSocket.getInputStream());
            mDataOutputStream = new DataOutputStream(mSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveData(){
        try {
            String data = mDataInputStream.readUTF();
            Toast.makeText(mContext,"Data from server: " + data,Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
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
