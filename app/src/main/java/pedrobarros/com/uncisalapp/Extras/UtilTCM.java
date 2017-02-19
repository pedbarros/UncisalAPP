package pedrobarros.com.uncisalapp.Extras;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pedrobarros.com.uncisalapp.R;

/**
 * Created by Pedro Barros
 * Alguns metodos uteis para todo o APP
 * Utilizada:
 * Criação:  30/04/2016.
 *
 */
public class UtilTCM {
    private static final String TAG = "LoginActivity";
    private static String dataFormatada;

    //Verificar conexão de internet
    public static boolean verificaConexao(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected() && netInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    //Ligar Wifi
    public static void ligarWifi(Context context){
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifi.setWifiEnabled(true); // Liga o WiFi
        if (wifi.isWifiEnabled()) {
            // WiFi está ligado
        }

       /* <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"></uses-permission>*/
    }

    //Formatar data
    public static String getDate(int type) {
        switch (type) {
            case 0:
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                dataFormatada = dateFormat.format(new Date());
                break;
            case 1:
                DateFormat dateFormat2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                dataFormatada = dateFormat2.format(new Date());
                break;
        }

        return dataFormatada;

    }


    //Animaçoes nas views
    public static void getAnimation(View v) {
        YoYo.with(Techniques.Tada)
                .duration(700)
                .playOn(v);
    }



}
