package com.qa.audiorecord;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * Created by sev_user on 28-Feb-15.
 */
public class Uitls {

    public static String getIpAddress(boolean isIpv4){
        String ip = null;

            try {

                List<NetworkInterface> networkInterfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
                for (NetworkInterface networkInterface: networkInterfaces) {

                    List<InetAddress>addresses = Collections.list(networkInterface.getInetAddresses());
                    for (InetAddress address : addresses) {

                        if (!address.isLoopbackAddress()){
                            ip = address.getHostAddress().toUpperCase();
                            if (InetAddressUtils.isIPv4Address(ip)){
                                return ip;
                            }
                        }
                    }
                }
            } catch (SocketException e) {
                e.printStackTrace();
            }

        return ip;
    }

    public static String wifiAddress(Context context){

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        return ip;
    }
}
