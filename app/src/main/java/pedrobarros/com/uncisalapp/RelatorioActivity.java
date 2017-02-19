package pedrobarros.com.uncisalapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pedrobarros.com.uncisalapp.Async.DownloadTask;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Extras.MySingleton;
import pedrobarros.com.uncisalapp.Model.Cargo;
import pedrobarros.com.uncisalapp.Model.Lotacao;

/**
 * Created by Pedro Barros.
 * Classe de relatório interno da Uncisal
 * Utilizada:
 * Criação: 30/05/2016.
 */
public class RelatorioActivity extends AppCompatActivity {

    String[] relatorios = {"Por cargo", "Por lotação"};
    //String[] relatorios2 = {"Alunos", "Professores", "Técnicos"};

    @Bind(R.id.toolBar)
    Toolbar mToolbar;
    @Bind(R.id.txtUsuMain)
    TextView mTxtUsuMain;
    @Bind(R.id.txtRelatorio)
    TextView mTxtRelatorio;
    @Bind(R.id.spnRelatorio)
    Spinner spnRelatorio;

    MySingleton mDados;

    String resposta; //retorno webservice

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        ButterKnife.bind(this);

        //TOOLBAR
        mToolbar.setTitle("UncisalQST");
        mToolbar.setSubtitle("Relatórios");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setLogo(R.drawable.logouncisallittle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recuperação de dados
        mDados = MySingleton.getInstance();
        mTxtUsuMain.setText(mDados.getNome());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, relatorios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnRelatorio.setAdapter(adapter);

        //Método do Spinner para capturar o item selecionado
        spnRelatorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {

                ArrayList<NameValuePair> parametros = new ArrayList<>();
                switch (posicao) {
                    case 0: //Por cargo
                        parametros.add(new BasicNameValuePair("acao", "5"));
                        break;
                    case 1: //Por lotação
                        parametros.add(new BasicNameValuePair("acao", "4"));
                        break;
                    /*case 2: //Carrega estatística de respostas por questão  tem que puxar por segmentos
                        parametros.add(new BasicNameValuePair("acao", "3"));
                        parametros.add(new BasicNameValuePair("cod", "2"));
                        break;*/
                }

                try {
                    resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getCarregaDados(), parametros);
                    JSONArray jsonArray = new JSONArray(resposta); //Transformando o retorno de String para um Array JSON
                    Log.i("Script", resposta);

                    StringBuilder respGeral = new StringBuilder();

                    for (int x = 0; x < jsonArray.length(); x++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(x);
                        switch (posicao) {
                            case 0:
                                respGeral.append(String.valueOf(x + 1) + " - ")
                                        .append(jsonObject.getString("CARGO") + " \n ")
                                        .append(jsonObject.getString("QTD") + "\n\n");
                                break;
                            case 1:
                                respGeral.append(String.valueOf(x + 1) + " - ")
                                        .append(jsonObject.getString("LOTACAO") + " \n ")
                                        .append(jsonObject.getString("QTD") + "\n\n");
                                break;

                            /*case 2:
                                respGeral.append(String.valueOf(x + 1) + " - ")
                                        .append(jsonObject.getString("DESC_PERG") + " \n ")
                                        .append(jsonObject.getString("ESTATISTICA") + "\n\n");
                                break;*/
                        }

                    }

                    mTxtRelatorio.setText(respGeral);


                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("=========================== caiu no catch");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
