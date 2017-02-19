package pedrobarros.com.uncisalapp.Extras;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Pedro Barros
 * Classe responsável por baixar o APK do servidor e instalar
 * Utilizada:
 * Criação: 16/05/2016.
 */
public class Atualiza extends Activity {
    ProgressDialog dlDialog;
    String path = Environment.getExternalStorageDirectory()+ "/"; // Path to where you want to save the file
    String inet = "http://androidstudy.esy.es/Conex%C3%A3oOracleJava.zip"; //"http://www.google.com/test.apk"; // Internet path to the file
    String cachedir = "";
    String filename = "myAPP.apk";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView webview = new TextView(this);
        setContentView(webview);


        File getcache = this.getCacheDir();
        cachedir = getcache.getAbsolutePath();

        dlDialog = new ProgressDialog(Atualiza.this);
        dlDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dlDialog.setTitle("Baixando...");
        dlDialog.setMessage("...");
        dlDialog.show();

        new Thread(new Runnable() {

            public void run() {

                String filePath = path;

                InputStream is = null;
                OutputStream os = null;
                URLConnection URLConn = null;

                try {
                    URL fileUrl;
                    byte[] buf;
                    int ByteRead = 0;
                    int ByteWritten = 0;
                    fileUrl = new URL(inet);

                    URLConn = fileUrl.openConnection();

                    is = URLConn.getInputStream();

                    String fileName = inet.substring(inet.lastIndexOf("/") + 1);

                    File f = new File(filePath);
                    f.mkdirs();
                    String abs = filePath + fileName;
                    f = new File(abs);


                    os = new BufferedOutputStream(new FileOutputStream(abs));

                    buf = new byte[1024];

                /*
                 * This loop reads the bytes and updates a progressdialog
                 */
                    while ((ByteRead = is.read(buf)) != -1) {

                        os.write(buf, 0, ByteRead);
                        ByteWritten += ByteRead;

                        final int tmpWritten = ByteWritten;
                        runOnUiThread(new Runnable() {

                            public void run() {
                                dlDialog.setMessage(""+tmpWritten+" Bytes");
                            }

                        });
                    }

                    runOnUiThread(new Runnable() {

                        public void run() {
                            dlDialog.setTitle("Startar");
                        }

                    });
                    is.close();
                    os.flush();
                    os.close();


                    Thread.sleep(200);

                    dlDialog.dismiss();

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(abs)),
                            "application/vnd.android.package-archive");
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }).start();

    }

    public void Atualizar(){


    }
}
