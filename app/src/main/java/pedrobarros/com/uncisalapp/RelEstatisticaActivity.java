package pedrobarros.com.uncisalapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Extras.MySingleton;

/**
 * Created by Pedro Barros
 * Classe responsável por exibir relatórios de estastiticas direto no app
 * Utilizada:
 * Criação: 10/07/2016.
 */
public class RelEstatisticaActivity extends AppCompatActivity {

    String[] relatorios = {"Alunos", "Professores", "Técnicos"};


    @Bind(R.id.toolBar)
    Toolbar mToolbar;
    @Bind(R.id.txtUsuMain)
    TextView mTxtUsuMain;
    @Bind(R.id.txtRelatorio)
    TextView mTxtRelatorio;
    @Bind(R.id.spnRelEst)
    Spinner spnRelEst;

    StringBuilder respGeral;

    MySingleton mDados;

    String resposta; //retorno webservice



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rel_estatistica);
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

        spnRelEst.setAdapter(adapter);

        //Método do Spinner para capturar o item selecionado
        spnRelEst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {

                ArrayList<NameValuePair> parametros = new ArrayList<>();
                parametros.add(new BasicNameValuePair("acao", "3"));

                switch (posicao) {
                    case 0: //Alunos
                        parametros.add(new BasicNameValuePair("cod", "1"));
                        break;
                    case 1: //Professores
                        parametros.add(new BasicNameValuePair("cod", "2"));
                        break;
                    case 2: //Técnicos
                        parametros.add(new BasicNameValuePair("cod", "3"));
                        break;
                }

                try {
                    resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getCarregaDados(), parametros);

                    Log.i("Script", resposta);

                    if (!resposta.equals("0")) {
                        JSONArray jsonArray = new JSONArray(resposta); //Transformando o retorno de String para um Array JSON
                        respGeral = new StringBuilder();

                        for (int x = 0; x < jsonArray.length(); x++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            respGeral.append(String.valueOf(x + 1) + " - ")
                                    .append(jsonObject.getString("DESC_PERG") + " \n ")
                                    .append(jsonObject.getString("ESTATISTICA") + "\n\n");

                        }
                        mTxtRelatorio.setText(respGeral);
                    } else {
                        System.out.println("=========================== a resposta não contem");
                        mTxtRelatorio.setText("Sem dados");
                    }


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
