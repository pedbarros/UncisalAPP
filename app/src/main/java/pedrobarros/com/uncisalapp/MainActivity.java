package pedrobarros.com.uncisalapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import pedrobarros.com.uncisalapp.Adapter.ListViewAdapter;
import pedrobarros.com.uncisalapp.Async.DownloadTask;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Conexao.HttpUrlConn;
import pedrobarros.com.uncisalapp.Extras.MySingleton;
import pedrobarros.com.uncisalapp.Fragment.AlertDialogFragment;
import pedrobarros.com.uncisalapp.Model.Principal;
import pedrobarros.com.uncisalapp.Permissao.PermissionUtils;

/**
 * Created by Pedro Barros
 * Classe responsável de menu principal
 * Utilizada:
 * Criação: 15/04/2016.
 */
public class MainActivity extends AppCompatActivity {
    @Bind(R.id.toolBar)
    Toolbar mToolbar;
    @Bind(R.id.listViewMain)
    ListView mLista;
    @Bind(R.id.txtUsuMain)
    TextView mTxtUsuMain;

    MySingleton mDados;

    private ArrayList<Principal> items; //Array de objetos que vão está dentro do listview
    private ListViewAdapter listViewAdapter; //Adapter para deixar listView personalizado da minha maneira
    private String resposta; //Retorno do webService (JSON)

    String caminho = null; //Caminho responsável onde o relatório gerado em pdf vai ser salvo
    String acao = null; //acao sera setada para saber qual relatorio vai ser baixado do php

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //TOOLBAR
        mToolbar.setTitle("UncisalQST");
        mToolbar.setSubtitle("Inicio");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setLogo(R.drawable.logouncisallittle);
        setSupportActionBar(mToolbar);

        //Recuperação de dados
        mDados = MySingleton.getInstance();
        mTxtUsuMain.setText(mDados.getNome());

        //verificando se é a primeira vez que tá entrando
        if (mDados.getLotacao().equals("null")) {
            dialogLotacao();
        }

        //ITEMS LISTVIEW
        items = new ArrayList<>();
        items.add(new Principal("Meus Dados", "Algumas informações"));
        items.add(new Principal("Responda o questionário", "Inserção do questionário"));
        items.add(new Principal("Confira suas Respostas", "Leitura do questionário"));
        items.add(new Principal("Relatórios", "Relatórios de quantidade e estatística"));
        items.add(new Principal("Baixar relatório", "Baixar os relatórios em pdf"));
        items.add(new Principal("Sair", "Sairá para tela de login"));

        listViewAdapter = new ListViewAdapter(this, items);
        mLista.setAdapter(listViewAdapter);

        mLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0: //DIALOGO COM AS INFORMAÇÕES PESSOAIS
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle("Informações pessoais");
                        alertDialog.setMessage("ID: " + mDados.getId() + "\nCPF: " + mDados.getCpf() +
                                "\nMatricula: " + mDados.getMatricula() +
                                "\nNome: " + mDados.getNome() + "\nLotação: " + mDados.getLotacao());
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        alertDialog.show();
                        break;
                    case 1:
                        // Faz uma verificação se já foi inserido o quesitonário, senão foi ele abre, se já foi ele dá um alerta informando
                        ArrayList<NameValuePair> parametrosPost1 = new ArrayList<>();
                        parametrosPost1.add(new BasicNameValuePair("acao", "1"));
                        parametrosPost1.add(new BasicNameValuePair("codPessoa", mDados.getId()));
                        try {
                            resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getUrlQst(), parametrosPost1);
                            Log.i("Script", resposta);
                            if (resposta.equals("0")) {
                                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                                alerta.setCancelable(false);
                                alerta.setTitle("Caro respondente!");
                                alerta.setMessage("Prezado respondente\n" +
                                        "\n" +
                                        "O presente questionário tem o objetivo de avaliar a UNCISAL sob variados aspectos, que juntos englobam toda a estrutura e função da Universidade.\n" +
                                        "\n" +
                                        "Essas respostas servirão de base para o relatório anual da Comissão Própria de Avaliação (CPA), que será entregue a Gestão da Universidade. Esta, por sua vez deverá contemplar respostas aos problemas apontados no relatório, por meio do Documento Institucional (Relato Institucional), que será avaliado oportunamente pelo órgão regulador, quando da avaliação externa da Instituição.\n" +
                                        "\n" +
                                        "Dessa forma, é por demais relevante a participação e o comprometimento com respostas isentas e que exprimam a real opinião de cada um de nós que compomos a comunidade acadêmica.");
                                alerta.setPositiveButton("Vamos ao questionário!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        switch (mDados.getCodCargo()) { //abre o questionário de acordo com qual cargo ele for
                                            case "1":
                                                startActivity(new Intent(MainActivity.this, FormAlunosActivity.class));
                                                break;
                                            case "2":
                                                startActivity(new Intent(MainActivity.this, FormProfActivity.class));
                                                break;
                                            case "3":
                                                startActivity(new Intent(MainActivity.this, FormTecActivity.class));
                                                break;
                                        }
                                    }
                                });
                                alerta.show();
                            } else
                                Toast.makeText(MainActivity.this, "Você já inseriu um questionário!", Toast.LENGTH_SHORT).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, VerRespQstActivity.class));
                        break;
                    case 3://Relatório por estatistica, lotação e cargo
                        if (mDados.getCodCargo().equals("4")) {//verificando se é a primeira vez que tá entrando -> 4 = CARGO ADMINISTRADOR
                            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                            b.setTitle("Relatórios");
                            String[] types = {"Quantidades", "Estatísticas"};
                            b.setItems(types, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    switch (which) {
                                        case 0:
                                            startActivity(new Intent(MainActivity.this, RelatorioActivity.class));
                                            break;
                                        case 1:
                                            startActivity(new Intent(MainActivity.this, RelEstatisticaActivity.class));
                                            break;
                                    }

                                }

                            });

                            b.show();
                        }else{
                            Toast.makeText(MainActivity.this, "Você não tem permissão para visualizar os relatórios!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 4:

                        if (mDados.getCodCargo().equals("4")) {//verificando se é a primeira vez que tá entrando -> 4 = CARGO ADMINISTRADOR

                            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                            b.setTitle("Relatórios");
                            String[] types = {"Lotacao", "Cargo", "Estatisticas"};
                            b.setItems(types, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    switch (which) {
                                        /*case 0:
                                            caminho = "relatoriosPessoas";
                                            acao = "1";
                                            break;*/
                                        case 0:
                                            caminho = "relatoriosLotacao";
                                            acao = "2";
                                            break;
                                        case 1:
                                            caminho = "relatoriosCargo";
                                            acao = "3";
                                            break;
                                        case 2:
                                            caminho = "relatoriosEstatisticas";
                                            acao = "4";
                                            break;
                                    }

                                    try {
                                        final DownloadTask downloadTask = new DownloadTask(MainActivity.this);
                                        downloadTask.execute(ConexaoHttpClient.getConsultaRelatorioPDF(acao), "/sdcard/" + caminho + ".pdf");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            });

                            b.show();


                        }else{
                            Toast.makeText(MainActivity.this, "Você não tem permissão para baixar os relatórios!", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 5://fecha o app para tela de login
                        finish();
                        break;

                }
            }
        });
    }

    //Método para mostrar a caixa de dialogo da lotação de acordo com o cargo
    public void dialogLotacao() {
        FragmentManager fm = getSupportFragmentManager();
        AlertDialogFragment dialogo = new AlertDialogFragment();
        dialogo.setCancelable(false);
        dialogo.show(fm, "Teste");
    }


    //Resultado do que o usuário permitou do app utilizar (versão 6.0+)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada
                    dialogFinish();
                return;
            }
        }

    }

    private void dialogFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name)
                    .setMessage("Para utilizar esta opção você precisa aceitar as permissões.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();

        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("Script", "onConfigurationChanged");

    }
}
