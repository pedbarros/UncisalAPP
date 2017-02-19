package pedrobarros.com.uncisalapp.Conexao;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import pedrobarros.com.uncisalapp.Extras.UtilTCM;


public class ConexaoHttpClient {
    public static final int HTTP_TIMEOUT = 30 * 1000; //1000
    private static HttpClient httpClient;

    private static HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient();
            final HttpParams httpParamns = httpClient.getParams();
            HttpConnectionParams.setConnectionTimeout(httpParamns, HTTP_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParamns, HTTP_TIMEOUT);
            ConnManagerParams.setTimeout(httpParamns, HTTP_TIMEOUT);
        }
        return httpClient;
    }

    public static String executaHttpPost(String url, ArrayList<NameValuePair> parametrosPost) throws Exception {

        BufferedReader bufferedReader = null;
        try {

            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            HttpClient client = getHttpClient();
            HttpPost httpPost = new HttpPost(url);
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parametrosPost, "UTF-8");
            httpPost.setEntity(formEntity);
            HttpResponse httpResponse = client.execute(httpPost);
            bufferedReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            StringBuffer stringBuffer = new StringBuffer("");
            String line = "";
            //String LS = System.getProperty("line.separator"); // \s
            while ((line = bufferedReader.readLine()) != null) {
//                stringBuffer.append(line + LS);
                stringBuffer.append(line);
            }
            bufferedReader.close();

            String resultado = stringBuffer.toString();
            return resultado;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String getConsultaDados() {
        return "http://www.appcpa.uncisal.edu.br/ConsultaDados.php";
    }



    //PADRÃO MVC

    public static String getCarregaDados() {
        return "http://appcpa.uncisal.edu.br/Controller/CarregaDadosController.php";
    }

    public static String getInsereRemove() {
       return "http://appcpa.uncisal.edu.br/Controller/InsereDadosController.php";
    }

    public static String getAtualizaDados() {
        return "http://appcpa.uncisal.edu.br/Controller/AtualizaDadosController.php";
    }

   /* public static String getRelatorio() {
        return "http://www.androidstudy.esy.es/UncisalAPP/Controller/AtualizaDadosController.php";
    }*/

    public static String getUrlLogin(){
        return "http://appcpa.uncisal.edu.br/Controller/LoginController.php";
    }

    public static String getUrlQst(){
        return "http://appcpa.uncisal.edu.br/Controller/QstController.php";
    }

    public static String getConsultaRelatorioPDF(String acao) {
        return "http://appcpa.uncisal.edu.br/ConsultaRelatorioPDF.php?acao="+acao;
    }
}

