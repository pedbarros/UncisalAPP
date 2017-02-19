package pedrobarros.com.uncisalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Extras.MySingleton;

/**
 * Created by Pedro Barros
 * Classe responsável por mostrar as respostas aplicado ao questionário
 * Utilizada:
 * Criação: 30/05/2016.
 */
public class VerRespQstActivity extends AppCompatActivity {
    @Bind(R.id.toolBar)
    Toolbar mToolbar;
    @Bind(R.id.tvPergResp)
    TextView tvPergResp;

    private MySingleton mDados;

    private String resposta; //Retorno do webService (JSON)

    private StringBuilder respGeral; //Resposta do questionario da pessoa

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_resp_qst);
        ButterKnife.bind(this);


        //Recuperação de dados
        mDados = MySingleton.getInstance();

        //TOOLBAR
        mToolbar.setTitle("UncisalQST");
        mToolbar.setSubtitle("Respostas do Qst CPA");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setLogo(R.drawable.logouncisallittle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //ASYNC TASK
        respGeral = new StringBuilder(); // Perguntas e Respostas concatenadas para serem impressas na nova activity
        ArrayList<NameValuePair> parametrosPost2 = new ArrayList<>();
        parametrosPost2.add(new BasicNameValuePair("acao", "2"));
        parametrosPost2.add(new BasicNameValuePair("codPessoa", mDados.getId()));
        try {
            resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getUrlQst(), parametrosPost2);
            Log.i("Script", resposta);
            if (!resposta.equals("0")) {

                JSONArray jo = new JSONArray(resposta);

                for (int i = 0; i < jo.length(); i++) {
                    JSONObject jsonObject = jo.getJSONObject(i);
                    respGeral.append(String.valueOf(i + 1) + " - ")
                            .append(jsonObject.getString("DESC_PERG") + " \n ")
                            .append(jsonObject.getString("DESC_RESP") + " \n ")
                            .append(jsonObject.getString("RESP_TXT") + " \n ");

                }

                tvPergResp.setText(respGeral);
            } else {
                Toast.makeText(VerRespQstActivity.this, "Sem dados", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(VerRespQstActivity.this, "Erro na pesquisa!" + e, Toast.LENGTH_LONG).show();
        }

    }
}

