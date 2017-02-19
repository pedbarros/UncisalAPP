package pedrobarros.com.uncisalapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Extras.MySingleton;
import pedrobarros.com.uncisalapp.Extras.UtilTCM;
import pedrobarros.com.uncisalapp.Model.HistQst;
import pedrobarros.com.uncisalapp.Model.QstRespostas;
/**
 * Created by Pedro Barros
 * Classe responsável por aplicar o questionário dos professores
 * Utilizada:
 * Criação: 20/05/2016.
 */
public class FormProfActivity extends AppCompatActivity {

    private static final String codQst = "2"; //Código do questionário de Professor
    private static final int CONSTANTE_DATA_ATUAL = 0; //Obtem só a data

    @Bind(R.id.toolBar)
    Toolbar mToolbar;
    @Bind(R.id.txtDados)
    TextView txtDados;
    @Bind(R.id.btnSalvar)
    Button btnSalvar;


    //CARREGAR COMPONENTES
    //1 questão
    @Bind(R.id.rgPerg1_1)
    RadioGroup rgPerg1_1;

    //2 questão
    @Bind(R.id.rgPerg2)
    RadioGroup rgPerg2;

    //3 questão - ENSINO
    @Bind(R.id.rgPerg3_1)
    RadioGroup rgPerg3_1;
    //3 questão - Pesquisa
    @Bind(R.id.rgPerg3_2)
    RadioGroup rgPerg3_2;
    //3 questão - Assistência
    @Bind(R.id.rgPerg3_3)
    RadioGroup rgPerg3_3;
    //3 questão - Extensão
    @Bind(R.id.rgPerg3_4)
    RadioGroup rgPerg3_4;
    //4 questão
    @Bind(R.id.rgPerg4)
    RadioGroup rgPerg4;
    //5 questão
    @Bind(R.id.rgPerg5)
    RadioGroup rgPerg5;
    //6 questão
    @Bind(R.id.rgPerg6)
    RadioGroup rgPerg6;
    //7 questão
    @Bind(R.id.rgPerg7_1)
    RadioGroup rgPerg7_1;

    //7 questão
    @Bind(R.id.rgPerg7_2)
    RadioGroup rgPerg7_2;


    //8 questão
    @Bind(R.id.rgPerg8)
    RadioGroup rgPerg8;
    //9 questão - Pesquisa
    @Bind(R.id.rgPerg9_1)
    RadioGroup rgPerg9_1;
    //9 questão - Ensino
    @Bind(R.id.rgPerg9_2)
    RadioGroup rgPerg9_2;
    //9 questão - Extensão
    @Bind(R.id.rgPerg9_3)
    RadioGroup rgPerg9_3;
    //9 questão - Assistência
    @Bind(R.id.rgPerg9_4)
    RadioGroup rgPerg9_4;
    //9 questão - Gestão
    @Bind(R.id.rgPerg9_5)
    RadioGroup rgPerg9_5;

    //10 questão
    @Bind(R.id.rgPerg10)
    RadioGroup rgPerg10;
    //11 questão
    @Bind(R.id.rgPerg11)
    RadioGroup rgPerg11;
    //12_1 questão
    @Bind(R.id.rgPerg12)
    RadioGroup rgPerg12;
    //13 questão
    @Bind(R.id.rgPerg13_1)
    RadioGroup rgPerg13_1;
    //13 questão
    @Bind(R.id.rgPerg13_2)
    RadioGroup rgPerg13_2;
    //14 questão
    @Bind(R.id.rgPerg14)
    RadioGroup rgPerg14;
    //15 questão
    @Bind(R.id.rgPerg15)
    RadioGroup rgPerg15;
    //16 questão
    @Bind(R.id.rgPerg16)
    RadioGroup rgPerg16;
    //17 questão
    @Bind(R.id.rgPerg17)
    RadioGroup rgPerg17;
    //18 questão
    @Bind(R.id.rgPerg18)
    RadioGroup rgPerg18;
    //19 questão
    @Bind(R.id.rgPerg19)
    RadioGroup rgPerg19;
    //20 questão - Ensino?
    @Bind(R.id.rgPerg20_1)
    RadioGroup rgPerg20_1;
    //20 questão - Extensão?
    @Bind(R.id.rgPerg20_2)
    RadioGroup rgPerg20_2;
    //20 questão - Pesquisa cientifica?
    @Bind(R.id.rgPerg20_3)
    RadioGroup rgPerg20_3;


    @Bind(R.id.layoutRgPerg15)
    LinearLayout layoutRgPerg15;


    String resposta; //Retorno das respostas do Questionário -  1 - (Deu certo) | 0 - (Bixado)

    MySingleton mDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_prof);
        ButterKnife.bind(this);

        //TOOLBAR
        mToolbar.setTitle("UncisalQST");
        mToolbar.setSubtitle("Questionário CPA dos Professores UNCISAL");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setLogo(R.drawable.logouncisallittle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Recuperação de dados
        mDados = MySingleton.getInstance();
        txtDados.setText(mDados.getNome());

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                insereFormProf insereFT = new insereFormProf();
                insereFT.execute();

            }
        });
    }

    /**
     * Classe responsável para inserir em background com AsyncTask
     */
    private class insereFormProf extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(FormProfActivity.this);

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FormProfActivity.this);
            dialog.setMessage("Inserindo...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //Array onde será armazenada todas as respostas
                List<QstRespostas> listaRespostas = new ArrayList<>();

                //A avaliação institucional é utilizada para o planejamento institucional?
                int op1 = rgPerg1_1.getCheckedRadioButtonId();
                if (op1 == R.id.rb1Perg1_1) {
                    QstRespostas QR = new QstRespostas("53", "209", codQst, "");
                    listaRespostas.add(QR);
                } else if (op1 == R.id.rb2Perg1_1) {
                    QstRespostas QR = new QstRespostas("53", "210", codQst, "");
                    listaRespostas.add(QR);
                } else if (op1 == R.id.rb3Perg1_1) {
                    QstRespostas QR = new QstRespostas("53", "211", codQst, "");
                    listaRespostas.add(QR);
                }


                //As demandas oriundas do relatório da CPA são atendidas?
                int op2 = rgPerg2.getCheckedRadioButtonId();
                if (op2 == R.id.rb1Perg2) {
                    QstRespostas QR = new QstRespostas("54", "212", codQst, "");
                    listaRespostas.add(QR);
                } else if (op2 == R.id.rb2Perg2) {
                    QstRespostas QR = new QstRespostas("54", "213", codQst, "");
                    listaRespostas.add(QR);
                } else if (op2 == R.id.rb3Perg2) {
                    QstRespostas QR = new QstRespostas("54", "214", codQst, "");
                    listaRespostas.add(QR);
                }


                //O planejamento desenvolvido pela Uncisal pode ser considerado satisfatório, no tocante ao:
                //ENSINO
                int op3 = rgPerg3_1.getCheckedRadioButtonId();
                if (op3 == R.id.rb1Perg3_1) {
                    QstRespostas QR = new QstRespostas("55", "215", codQst, "");
                    listaRespostas.add(QR);
                } else if (op3 == R.id.rb2Perg3_1) {
                    QstRespostas QR = new QstRespostas("55", "216", codQst, "");
                    listaRespostas.add(QR);
                }else if (op3 == R.id.rb3Perg3_1) {
                    QstRespostas QR = new QstRespostas("55", "551", codQst, "");
                    listaRespostas.add(QR);
                }else if (op3 == R.id.rb4Perg3_1) {
                    QstRespostas QR = new QstRespostas("55", "552", codQst, "");
                    listaRespostas.add(QR);
                }


                //O planejamento desenvolvido pela Uncisal pode ser considerado satisfatório, no tocante ao:
                //PESQUISA
                int op4 = rgPerg3_2.getCheckedRadioButtonId();
                if (op4 == R.id.rb1Perg3_2) {
                    QstRespostas QR = new QstRespostas("55", "217", codQst, "");
                    listaRespostas.add(QR);
                } else if (op4 == R.id.rb2Perg3_2) {
                    QstRespostas QR = new QstRespostas("55", "218", codQst, "");
                    listaRespostas.add(QR);
                }else if (op4 == R.id.rb3Perg3_2) {
                    QstRespostas QR = new QstRespostas("55", "553", codQst, "");
                    listaRespostas.add(QR);
                }else if (op4 == R.id.rb4Perg3_2) {
                    QstRespostas QR = new QstRespostas("55", "554", codQst, "");
                    listaRespostas.add(QR);
                }


                //O planejamento desenvolvido pela Uncisal pode ser considerado satisfatório, no tocante ao:
                //Assistência
                int op5 = rgPerg3_3.getCheckedRadioButtonId();
                if (op5 == R.id.rb1Perg3_3) {
                    QstRespostas QR = new QstRespostas("55", "219", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb2Perg3_3) {
                    QstRespostas QR = new QstRespostas("55", "220", codQst, "");
                    listaRespostas.add(QR);
                }else if (op5 == R.id.rb3Perg3_3) {
                    QstRespostas QR = new QstRespostas("55", "555", codQst, "");
                    listaRespostas.add(QR);
                }else if (op5 == R.id.rb4Perg3_3) {
                    QstRespostas QR = new QstRespostas("55", "556", codQst, "");
                    listaRespostas.add(QR);
                }


                //O planejamento desenvolvido pela Uncisal pode ser considerado satisfatório, no tocante ao:
                //Extensão
                int op6 = rgPerg3_4.getCheckedRadioButtonId();
                if (op6 == R.id.rb1Perg3_4) {
                    QstRespostas QR = new QstRespostas("55", "221", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6 == R.id.rb2Perg3_4) {
                    QstRespostas QR = new QstRespostas("55", "222", codQst, "");
                    listaRespostas.add(QR);
                }else if (op6 == R.id.rb3Perg3_4) {
                    QstRespostas QR = new QstRespostas("55", "557", codQst, "");
                    listaRespostas.add(QR);
                }else if (op6 == R.id.rb4Perg3_4) {
                    QstRespostas QR = new QstRespostas("55", "558", codQst, "");
                    listaRespostas.add(QR);
                }


                //O PDI foi elaborado baseado nas demandas apontadas pelo relatório da CPA?
                int op7 = rgPerg4.getCheckedRadioButtonId();
                if (op7 == R.id.rb1Perg4) {
                    QstRespostas QR = new QstRespostas("56", "223", codQst, "");
                    listaRespostas.add(QR);
                } else if (op7 == R.id.rb2Perg4) {
                    QstRespostas QR = new QstRespostas("56", "224", codQst, "");
                    listaRespostas.add(QR);
                } else if (op7 == R.id.rb3Perg4) {
                    QstRespostas QR = new QstRespostas("56", "225", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você considera as ações da Uncisal alinhadas com a sua missão?
                int op8 = rgPerg5.getCheckedRadioButtonId();
                if (op8 == R.id.rb1Perg5) {
                    QstRespostas QR = new QstRespostas("57", "226", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb2Perg5) {
                    QstRespostas QR = new QstRespostas("57", "227", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb3Perg5) {
                    QstRespostas QR = new QstRespostas("57", "228", codQst, "");
                    listaRespostas.add(QR);
                }


                //As atividades que você desenvolve na IES estão contempladas  no PDI?
                int op9 = rgPerg6.getCheckedRadioButtonId();
                if (op9 == R.id.rb1Perg6) {
                    QstRespostas QR = new QstRespostas("58", "229", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb2Perg6) {
                    QstRespostas QR = new QstRespostas("58", "230", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb3Perg6) {
                    QstRespostas QR = new QstRespostas("58", "231", codQst, "");
                    listaRespostas.add(QR);
                }


                //A missão da instituição é amplamente divulgada com a comunidade interna?
                int op10 = rgPerg7_1.getCheckedRadioButtonId();
                if (op10 == R.id.rb1Perg7_1) {
                    QstRespostas QR = new QstRespostas("59", "232", codQst, "");
                    listaRespostas.add(QR);
                } else if (op10 == R.id.rb2Perg7_1) {
                    QstRespostas QR = new QstRespostas("59", "233", codQst, "");
                    listaRespostas.add(QR);
                }


                //A missão da instituição é amplamente divulgada com a comunidade externa?
                int op11 = rgPerg7_2.getCheckedRadioButtonId();
                if (op11 == R.id.rb1Perg7_2) {
                    QstRespostas QR = new QstRespostas("60", "234", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb2Perg7_2) {
                    QstRespostas QR = new QstRespostas("60", "235", codQst, "");
                    listaRespostas.add(QR);
                }


                //A Uncisal atende as demandas de auxilio para docente em evento científico/acadêmico?
                int op12 = rgPerg8.getCheckedRadioButtonId();
                if (op12 == R.id.rb1Perg8) {
                    QstRespostas QR = new QstRespostas("61", "236", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb2Perg8) {
                    QstRespostas QR = new QstRespostas("61", "237", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb3Perg8) {
                    QstRespostas QR = new QstRespostas("61", "238", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe coerência entre o PDI e as atividades:
                //PESQUISA
                int op13 = rgPerg9_1.getCheckedRadioButtonId();
                if (op13 == R.id.rb1Perg9_1) {
                    QstRespostas QR = new QstRespostas("62", "239", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb2Perg9_1) {
                    QstRespostas QR = new QstRespostas("62", "240", codQst, "");
                    listaRespostas.add(QR);
                }else if (op13 == R.id.rb3Perg9_1) {
                    QstRespostas QR = new QstRespostas("62", "559", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe coerência entre o PDI e as atividades:
                //ENSINO
                int op14 = rgPerg9_2.getCheckedRadioButtonId();
                if (op14 == R.id.rb1Perg9_2) {
                    QstRespostas QR = new QstRespostas("62", "241", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb2Perg9_2) {
                    QstRespostas QR = new QstRespostas("62", "242", codQst, "");
                    listaRespostas.add(QR);
                }else if (op13 == R.id.rb3Perg9_2) {
                    QstRespostas QR = new QstRespostas("62", "560", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe coerência entre o PDI e as atividades:
                //EXTENSÃO
                int op15 = rgPerg9_3.getCheckedRadioButtonId();
                if (op15 == R.id.rb1Perg9_3) {
                    QstRespostas QR = new QstRespostas("62", "243", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb2Perg9_3) {
                    QstRespostas QR = new QstRespostas("62", "244", codQst, "");
                    listaRespostas.add(QR);
                }else if (op15 == R.id.rb3Perg9_3) {
                    QstRespostas QR = new QstRespostas("62", "561", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe coerência entre o PDI e as atividades:
                //ASSISTÊNCIA
                int op16 = rgPerg9_4.getCheckedRadioButtonId();
                if (op16 == R.id.rb1Perg9_4) {
                    QstRespostas QR = new QstRespostas("62", "245", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb2Perg9_4) {
                    QstRespostas QR = new QstRespostas("62", "246", codQst, "");
                    listaRespostas.add(QR);
                }else if (op16 == R.id.rb3Perg9_4) {
                    QstRespostas QR = new QstRespostas("62", "562", codQst, "");
                    listaRespostas.add(QR);
                }

                //Existe coerência entre o PDI e as atividades:
                //GESTÃO
                int op16_2 = rgPerg9_5.getCheckedRadioButtonId();
                if (op16_2 == R.id.rb1Perg9_5) {
                    QstRespostas QR = new QstRespostas("62", "563", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16_2 == R.id.rb2Perg9_5) {
                    QstRespostas QR = new QstRespostas("62", "564", codQst, "");
                    listaRespostas.add(QR);
                }else if (op16_2 == R.id.rb3Perg9_5) {
                    QstRespostas QR = new QstRespostas("62", "565", codQst, "");
                    listaRespostas.add(QR);
                }


                //As ações institucionais estimulam a indissociabilidade entre ensino, pesquisa e extensão?
                int op17 = rgPerg10.getCheckedRadioButtonId();
                if (op17 == R.id.rb1Perg10) {
                    QstRespostas QR = new QstRespostas("63", "247", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb2Perg10) {
                    QstRespostas QR = new QstRespostas("63", "248", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb3Perg10) {
                    QstRespostas QR = new QstRespostas("63", "249", codQst, "");
                    listaRespostas.add(QR);
                }else if (op17 == R.id.rb4Perg10) {
                    QstRespostas QR = new QstRespostas("63", "566", codQst, "");
                    listaRespostas.add(QR);
                }


                //A política acadêmica atende a necessidade do entorno da Uncisal?
                int op18 = rgPerg11.getCheckedRadioButtonId();
                if (op18 == R.id.rb1Perg11) {
                    QstRespostas QR = new QstRespostas("64", "250", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb2Perg11) {
                    QstRespostas QR = new QstRespostas("64", "251", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb3Perg11) {
                    QstRespostas QR = new QstRespostas("64", "252", codQst, "");
                    listaRespostas.add(QR);
                }


                //Na IES existem políticas relacionadas a inovação tecnológica e propriedade intelectual voltadas a comunidade interna e externa?
                int op19 = rgPerg12.getCheckedRadioButtonId();
                if (op19 == R.id.rb1Perg12) {
                    QstRespostas QR = new QstRespostas("65", "253", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb2Perg12) {
                    QstRespostas QR = new QstRespostas("65", "254", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb3Perg12) {
                    QstRespostas QR = new QstRespostas("65", "255", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera o site da UNCISAL em relação a Layout:
                int op20 = rgPerg13_1.getCheckedRadioButtonId();
                if (op20 == R.id.rb1Perg13_1) {
                    QstRespostas QR = new QstRespostas("66", "256", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb2Perg13_1) {
                    QstRespostas QR = new QstRespostas("66", "257", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb3Perg13_1) {
                    QstRespostas QR = new QstRespostas("66", "258", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb4Perg13_1) {
                    QstRespostas QR = new QstRespostas("66", "259", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb5Perg13_1) {
                    QstRespostas QR = new QstRespostas("66", "260", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera o site da UNCISAL em relação a conteúdo disponibilizado no portal da Uncisal?:
                int op21 = rgPerg13_2.getCheckedRadioButtonId();
                if (op21 == R.id.rb1Perg13_2) {
                    QstRespostas QR = new QstRespostas("67", "261", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb2Perg13_2) {
                    QstRespostas QR = new QstRespostas("67", "262", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb3Perg13_2) {
                    QstRespostas QR = new QstRespostas("67", "263", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb4Perg13_2) {
                    QstRespostas QR = new QstRespostas("67", "264", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb5Perg13_2) {
                    QstRespostas QR = new QstRespostas("67", "265", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe na IES programa de sustentabilidade financeira para subsidiar custeio de suas necessidades acadêmicas?
                int op22 = rgPerg14.getCheckedRadioButtonId();
                if (op22 == R.id.rb1Perg14) {
                    QstRespostas QR = new QstRespostas("68", "266", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb2Perg14) {
                    QstRespostas QR = new QstRespostas("68", "267", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb3Perg14) {
                    QstRespostas QR = new QstRespostas("68", "268", codQst, "");
                    listaRespostas.add(QR);
                }else if (op22 == R.id.rb4Perg14) {
                    QstRespostas QR = new QstRespostas("68", "567", codQst, "");
                    listaRespostas.add(QR);
                }


                //Com que frequência a Uncisal oferta capacitação docente?
                int op23 = rgPerg15.getCheckedRadioButtonId();
                if (op23 == R.id.rb1Perg15) {
                    QstRespostas QR = new QstRespostas("69", "269", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb2Perg15) {
                    QstRespostas QR = new QstRespostas("69", "270", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb3Perg15) {
                    QstRespostas QR = new QstRespostas("69", "271", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb4Perg15) {
                    QstRespostas QR = new QstRespostas("69", "272", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a capacitação do corpo docente da instituição?
                int op24 = rgPerg16.getCheckedRadioButtonId();
                if (op24 == R.id.rb1Perg16) {
                    QstRespostas QR = new QstRespostas("70", "273", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb2Perg16) {
                    QstRespostas QR = new QstRespostas("70", "274", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb3Perg16) {
                    QstRespostas QR = new QstRespostas("70", "275", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb4Perg16) {
                    QstRespostas QR = new QstRespostas("70", "276", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb5Perg16) {
                    QstRespostas QR = new QstRespostas("70", "277", codQst, "");
                    listaRespostas.add(QR);
                }


                //Há compatibilidade entre os cursos ofertados e as instalações disponíveis?
                int op25 = rgPerg17.getCheckedRadioButtonId();
                if (op25 == R.id.rb1Perg17) {
                    QstRespostas QR = new QstRespostas("71", "278", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb2Perg17) {
                    QstRespostas QR = new QstRespostas("71", "279", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb3Perg17) {
                    QstRespostas QR = new QstRespostas("71", "280", codQst, "");
                    listaRespostas.add(QR);
                }


                //O espaço físico da biblioteca é adequado às necessidades da comunidade acadêmica?
                int op26 = rgPerg18.getCheckedRadioButtonId();
                if (op26 == R.id.rb1Perg18) {
                    QstRespostas QR = new QstRespostas("72", "281", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb2Perg18) {
                    QstRespostas QR = new QstRespostas("72", "282", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb3Perg18) {
                    QstRespostas QR = new QstRespostas("72", "283", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe restaurante universitário que contemple as necessidades da comunidade interna e externa?
                int op27 = rgPerg19.getCheckedRadioButtonId();
                if (op27 == R.id.rb1Perg19) {
                    QstRespostas QR = new QstRespostas("73", "284", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb2Perg19) {
                    QstRespostas QR = new QstRespostas("73", "285", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb3Perg19) {
                    QstRespostas QR = new QstRespostas("73", "286", codQst, "");
                    listaRespostas.add(QR);
                }


                //Os laboratórios existentes na IES viabilizam a realização de:
                // ENSINO
                int op28 = rgPerg20_1.getCheckedRadioButtonId();
                if (op28 == R.id.rb1Perg20_1) {
                    QstRespostas QR = new QstRespostas("74", "287", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb2Perg20_1) {
                    QstRespostas QR = new QstRespostas("74", "288", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb3Perg20_1) {
                    QstRespostas QR = new QstRespostas("74", "289", codQst, "");
                    listaRespostas.add(QR);
                }


                //Os laboratórios existentes na IES viabilizam a realização de:
                // Extensão
                int op29 = rgPerg20_2.getCheckedRadioButtonId();
                if (op29 == R.id.rb1Perg20_2) {
                    QstRespostas QR = new QstRespostas("75", "290", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb2Perg20_2) {
                    QstRespostas QR = new QstRespostas("75", "291", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb3Perg20_2) {
                    QstRespostas QR = new QstRespostas("75", "292", codQst, "");
                    listaRespostas.add(QR);
                }


                //Os laboratórios existentes na IES viabilizam a realização de:
                // Pesquisa Cientifica?
                int op30 = rgPerg20_3.getCheckedRadioButtonId();
                if (op30 == R.id.rb1Perg20_3) {
                    QstRespostas QR = new QstRespostas("76", "293", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb2Perg20_3) {
                    QstRespostas QR = new QstRespostas("76", "294", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb3Perg20_3) {
                    QstRespostas QR = new QstRespostas("76", "295", codQst, "");
                    listaRespostas.add(QR);
                }


                //Qual a sua opinião sobre a UNCISAL?
                EditText edtPerg11 = (EditText) findViewById(R.id.edt1Perg11);
                if (!edtPerg11.getText().toString().equals("")) {
                    QstRespostas QR = new QstRespostas("129", "550", codQst, edtPerg11.getText().toString());
                    listaRespostas.add(QR);
                }

                /*
                 * Retorno do WebService
                 * @resposta
                 */
                Log.i("script", String.valueOf(listaRespostas.size()));
                if (listaRespostas.size() >= 29) { //São 29 respostas para ser preenchido todo questionário
                    //Inserindo no objeto e transformando para JSON
                    HistQst histQst = new HistQst();
                    histQst.setUn_data_hist(UtilTCM.getDate(CONSTANTE_DATA_ATUAL));
                    histQst.setUn_desc_hist("Aplicação do Formulário CPA dos Professores da Uncisal");
                    histQst.setUn_cod_hist_qst(codQst);
                    histQst.setUn_cod_hist_pes(String.valueOf(mDados.getId()));

                    String jsonHistQst = histQst.histQstToJSON(histQst);  //JSON para ser salvo no Histórico do Questionário Aplicado

                    QstRespostas qstJson = new QstRespostas();
                    String jsonForm = qstJson.answersToJSON(listaRespostas); //Resposnsável por empacotar os dados em um JSON e armazenar na variavel

                    ArrayList<NameValuePair> parametrosPostQstResp = new ArrayList<>();
                    parametrosPostQstResp.add(new BasicNameValuePair("acao",  "1"));
                    parametrosPostQstResp.add(new BasicNameValuePair("jsonForm", jsonForm));
                    parametrosPostQstResp.add(new BasicNameValuePair("jsonHistQst", jsonHistQst));
                    resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getInsereRemove(), parametrosPostQstResp);
                    Log.i("Script", resposta);
                } else {
                    resposta = "Preencha todos os campos do questionário!"; //Se for 0 aconteceu algum erro e não foi inserido
                }

            } catch (Exception e) {
                resposta = "Ocorreu um erro na inserção do questionário!"; //Se for 0 aconteceu algum erro e não foi inserido
            }


            return resposta;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("1")) {
                Toast.makeText(FormProfActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            } else {
                Toast.makeText(FormProfActivity.this, result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

        }//close onPostExecute

    }// close validateUserTask


    public void verGlossario(View view) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("GLOSSÁRIO!");
        alertDialog.setMessage("Acervo Virtual:\n" +
                "Acervo virtual  é o conteúdo de uma coleção privada ou pública, podendo  ser de caráter bibliográfico, artístico, fotográfico, científico, histórico,  documental  ou misto e  com acesso universal  via internet.\n" +
                "\n" +
                "Acessibilidade: \n" +
                "Condição para utilização, com segurança e autonomia, total ou assistida, dos espaços, mobiliários e equipamentos urbanos, das edificações, dos serviços de transporte e dos dispositivos, sistemas e meios de comunicação e informação, por pessoa com deficiência ou com mobilidade reduzida. No âmbito educacional, a acessibilidade pressupõe não só a eliminação de barreiras arquitetônicas, mas a promoção plena de condições para acesso e permanência na educação superior para necessidades educacionais especiais.\n" +
                "\n" +
                "Acessibilidade digital:\n" +
                "Condição de utilização, com autonomia total ou assistida, de recursos tecnológicos.\n" +
                "\n" +
                "Acessibilidade Arquitetônica:\n" +
                "Condição para utilização,  com segurança e autonomia, total ou assistida, dos espaços, mobiliários e equipamentos urbanos, das edificações, dos serviços de transporte dos dispositivos, sistemas e meios de comunicação e informação, por pessoa com deficiência ou com mobilidade reduzida.\n" +
                "\n" +
                "Acessibilidade Atitudinal:\n" +
                "Refere-se à percepção do outro, sem preconceitos, estigmas, estereótipos e discriminações. Todos os demais tipos de acessibilidade estão relacionados a essa, pois é a atitude da pessoa que impulsiona a remoção de barreiras.\n" +
                "\n" +
                "Acessibilidade Pedagógica:\n" +
                "Ausência de barreiras nas metodologias e técnicas de estudo. Está relacionada diretamente à concepção subjacente à atuação docente: a forma como os professores concebem conhecimento, aprendizagem, avaliação e inclusão educacional determinará, ou não, a remoção das barreiras pedagógicas.\n" +
                "\n" +
                "Acessibilidade nas comunicações:\n" +
                "Eliminação de barreiras na comunicação interpessoal (face a face, língua de sinais), escrita (jornal, revista, livro, carta, apostila etc. ,incluindo textos em Braille, grafia ampliada, uso do computador portátil, site institucional em linguagem acessível em todos os módulos)e virtual (acessibilidade digital).\n" +
                "\n" +
                "Inovação tecnológica, inovação social, propriedade intelectual:\n" +
                "Componentes curriculares relacionados à inovação, à propriedade intelectual, patentes e produtos nas diversas áreas de conhecimento; programas de pesquisa, cursos ou ações de extensão que incluam a temática; incubadoras.\n" +
                "\n" +
                "Políticas institucionais:\n" +
                "Políticas desenvolvidas no âmbito institucional com o propósito de seguir missão proposta pela IES, buscando atender ao Plano de Desenvolvimento Institucional (PDI).\n" +
                "\n" +
                "Programa de acessibilidade:\n" +
                "Desenvolvimento de ações e projetos institucionais que tenham o objetivo de assegurar o acesso e a permanência, com sucesso, de todos os estudantes, em especial os que apresentam deficiência ou necessidades educacionais especiais, nas instituições de educação superior.\n" +
                "\n" +
                "Responsabilidade social da IES:\n" +
                "A responsabilidade social refere-se às ações da instituição (com ou sem parceria) que contribuem para uma sociedade mais justa e sustentável. Nesse sentido, deverão ser verificados trabalhos, ações, atividades projetos e programas desenvolvidos com e para a comunidade, objetivando a inclusão social, o desenvolvimento econômico, a melhoria da qualidade de vida, da infraestrutura urbana/local e a inovação social.\n" +
                "\n" +
                "Sustentabilidade socioambiental:\n" +
                "A dimensão socioambiental, nas atividades de ensino, pesquisa, extensão e gestão, destina-se à conservação, recuperação e melhoria das condições ambientais, sociais e existenciais, promovendo a participação de toda a comunidade da IES, no delineamento, planejamento, implantação e avaliação das atividades e dos seus indicadores, que devem constar no seu PDI.\n" +
                "\n");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        alertDialog.show();
    }

    public void verificaClique(View v) {
        if (rgPerg14.getCheckedRadioButtonId() == R.id.rb1Perg14) {
            layoutRgPerg15.setVisibility(0);//set visibility to false on create
        } else {
            layoutRgPerg15.setVisibility(4);
            rgPerg15.clearCheck();//Clears the selection.
        }

    }
}
