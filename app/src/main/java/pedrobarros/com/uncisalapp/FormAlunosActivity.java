package pedrobarros.com.uncisalapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.text.Normalizer;
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
 * Classe responsável por aplicar o questionário dos alunos
 * Utilizada:
 * Criação: 15/05/2016.
 */
public class FormAlunosActivity extends AppCompatActivity {
    private static final String codQst = "1"; //Código do questionário de Alunos
    private static final int CONSTANTE_DATA_ATUAL = 0; //Obtem só a data

    @Bind(R.id.toolBar)
    Toolbar mToolbar;
    @Bind(R.id.txtDados)
    TextView txtDados;
    @Bind(R.id.btnSalvar)
    Button btnSalvar;

    //CARREGAR COMPONENTES
    @Bind(R.id.rgPerg1_1)
    RadioGroup rgPerg1_1;
    @Bind(R.id.rgPerg1_2)
    RadioGroup rgPerg1_2;
    @Bind(R.id.rgPerg2_1)
    RadioGroup rgPerg2_1;
    @Bind(R.id.rgPerg2_2)
    RadioGroup rgPerg2_2;
    @Bind(R.id.rgPerg2_3)
    RadioGroup rgPerg2_3;
    @Bind(R.id.rgPerg3_1)
    RadioGroup rgPerg3_1;
    @Bind(R.id.rgPerg3_2)
    RadioGroup rgPerg3_2;
    @Bind(R.id.rgPerg4)
    RadioGroup rgPerg4;
    @Bind(R.id.rgPerg5_1)
    RadioGroup rgPerg5_1;
    @Bind(R.id.rgPerg5_2)
    RadioGroup rgPerg5_2;
    @Bind(R.id.rgPerg5_3)
    RadioGroup rgPerg5_3;
    @Bind(R.id.rgPerg5_4)
    RadioGroup rgPerg5_4;
    @Bind(R.id.rgPerg6_1)
    RadioGroup rgPerg6_1;
    @Bind(R.id.rgPerg6_2)
    RadioGroup rgPerg6_2;
    @Bind(R.id.rgPerg6_3)
    RadioGroup rgPerg6_3;
    @Bind(R.id.rgPerg6_4)
    RadioGroup rgPerg6_4;
    @Bind(R.id.rgPerg6_5)
    RadioGroup rgPerg6_5;
    @Bind(R.id.rgPerg7)
    RadioGroup rgPerg7;
    @Bind(R.id.rgPerg8)
    RadioGroup rgPerg8;
    @Bind(R.id.rgPerg9)
    RadioGroup rgPerg9;
    @Bind(R.id.rgPerg10)
    RadioGroup rgPerg10;
    @Bind(R.id.rgPerg11)
    RadioGroup rgPerg11;
    @Bind(R.id.rgPerg12_1)
    RadioGroup rgPerg12_1;
    @Bind(R.id.rgPerg12_2)
    RadioGroup rgPerg12_2;
    @Bind(R.id.rgPerg12_3)
    RadioGroup rgPerg12_3;
    @Bind(R.id.rgPerg12_4)
    RadioGroup rgPerg12_4;
    @Bind(R.id.rgPerg12_5)
    RadioGroup rgPerg12_5;
    @Bind(R.id.rgPerg13_1)
    RadioGroup rgPerg13_1;
    @Bind(R.id.rgPerg13_2)
    RadioGroup rgPerg13_2;
    @Bind(R.id.rgPerg13_3)
    RadioGroup rgPerg13_3;
    @Bind(R.id.rgPerg14_1)
    RadioGroup rgPerg14_1;
    @Bind(R.id.rgPerg14_2)
    RadioGroup rgPerg14_2;
    @Bind(R.id.rgPerg14_3)
    RadioGroup rgPerg14_3;
    @Bind(R.id.rgPerg14_4)
    RadioGroup rgPerg14_4;
    @Bind(R.id.rgPerg14_5)
    RadioGroup rgPerg14_5;
    @Bind(R.id.rgPerg15_1)
    RadioGroup rgPerg15_1;
    @Bind(R.id.rgPerg15_2)
    RadioGroup rgPerg15_2;
    @Bind(R.id.rgPerg16)
    RadioGroup rgPerg16;
    @Bind(R.id.rgPerg17)
    RadioGroup rgPerg17;
    @Bind(R.id.rgPerg18)
    RadioGroup rgPerg18;
    @Bind(R.id.rgPerg19)
    RadioGroup rgPerg19;
    @Bind(R.id.rgPerg20)
    RadioGroup rgPerg20;
    @Bind(R.id.rgPerg21_1)
    RadioGroup rgPerg21_1;
    @Bind(R.id.rgPerg21_2)
    RadioGroup rgPerg21_2;
    @Bind(R.id.rgPerg21_3)
    RadioGroup rgPerg21_3;
    @Bind(R.id.rgPerg21_4)
    RadioGroup rgPerg21_4;
    @Bind(R.id.rgPerg21_5)
    RadioGroup rgPerg21_5;
    @Bind(R.id.rgPerg21_6)
    RadioGroup rgPerg21_6;

    @Bind(R.id.rgPerg22)
    RadioGroup rgPerg22;
    @Bind(R.id.rgPerg23)
    RadioGroup rgPerg23;
    @Bind(R.id.rgPerg24)
    RadioGroup rgPerg24;
    @Bind(R.id.rgPerg25)
    RadioGroup rgPerg25;


   @Bind(R.id.layoutRgPerg1_2)
   LinearLayout layoutRgPerg1_2;


    String resposta; //Retorno das respostas do Questionário -  1 - (Deu certo) | 0 - (Bixado)

    MySingleton mDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_alunos);
        ButterKnife.bind(this);

        //TOOLBAR
        mToolbar.setTitle("UncisalQST");
        mToolbar.setSubtitle("Questionário CPA dos Alunos UNCISAL");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        mToolbar.setLogo(R.drawable.logouncisallittle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recuperação de dados
        mDados = MySingleton.getInstance();
        txtDados.setText(mDados.getNome());


        btnSalvar.setOnClickListener(new View.OnClickListener() {
            /**
             * 4 = View.INVISIBLE
             * 0 = View.VISIBLE
             */
            public void onClick(View v) {

                insereFormAlunos insereFT = new insereFormAlunos();
                insereFT.execute();

            }
        });
    }

    /**
     * Classe responsável para inserir em background com AsyncTask
     */
    private class insereFormAlunos extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(FormAlunosActivity.this);

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FormAlunosActivity.this);
            dialog.setMessage("Inserindo...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //Array onde será armazenada todas as respostas
                List<QstRespostas> listaRespostas = new ArrayList<>();

                //Existe um documento em que conste a Missão e o Plano de Desenvolvimento Institucional?
                int op1 = rgPerg1_1.getCheckedRadioButtonId();
                if (op1 == R.id.rb1Perg1_1) {
                    QstRespostas QR = new QstRespostas("77", "312", codQst, "");
                    listaRespostas.add(QR);
                } else if (op1 == R.id.rb2Perg1_1) {
                    QstRespostas QR = new QstRespostas("77", "313", codQst, "");
                    listaRespostas.add(QR);
                } else if (op1 == R.id.rb3Perg1_1) {
                    QstRespostas QR = new QstRespostas("77", "314", codQst, "");
                    listaRespostas.add(QR);
                }

                //Caso a pergunta (1) for SIM, marcar as opções abaixo, é divulgado e você tem conhecimento:
                int op2 = rgPerg1_2.getCheckedRadioButtonId();
                if (op2 == R.id.rb1Perg1_2) {
                    QstRespostas QR = new QstRespostas("78", "315", codQst, "");
                    listaRespostas.add(QR);
                } else if (op2 == R.id.rb2Perg1_2) {
                    QstRespostas QR = new QstRespostas("78", "316", codQst, "");
                    listaRespostas.add(QR);
                } else if (op2 == R.id.rb3Perg1_2) {
                    QstRespostas QR = new QstRespostas("78", "317", codQst, "");
                    listaRespostas.add(QR);
                } else if (op2 == R.id.rb4Perg1_2) {
                    QstRespostas QR = new QstRespostas("78", "319", codQst, "");
                    listaRespostas.add(QR);
                }

                //Como você avalia a política institucional no que diz respeito a:
                //ENSINO
                int op3 = rgPerg2_1.getCheckedRadioButtonId();
                if (op3 == R.id.rb1Perg2_1) {
                    QstRespostas QR = new QstRespostas("79", "320", codQst, "");
                    listaRespostas.add(QR);
                } else if (op3 == R.id.rb2Perg2_1) {
                    QstRespostas QR = new QstRespostas("79", "321", codQst, "");
                    listaRespostas.add(QR);
                } else if (op3 == R.id.rb3Perg2_1) {
                    QstRespostas QR = new QstRespostas("79", "322", codQst, "");
                    listaRespostas.add(QR);
                } else if (op3 == R.id.rb4Perg2_1) {
                    QstRespostas QR = new QstRespostas("79", "323", codQst, "");
                    listaRespostas.add(QR);
                } else if (op3 == R.id.rb5Perg2_1) {
                    QstRespostas QR = new QstRespostas("79", "324", codQst, "");
                    listaRespostas.add(QR);
                }

//                RadioGroup rgPerg2_2 = (RadioGroup) findViewById(R.id.rgPerg2_2);
                //PESQUISA
                int op4 = rgPerg2_2.getCheckedRadioButtonId();
                if (op4 == R.id.rb1Perg2_2) {
                    QstRespostas QR = new QstRespostas("80", "325", codQst, "");
                    listaRespostas.add(QR);
                } else if (op4 == R.id.rb2Perg2_2) {
                    QstRespostas QR = new QstRespostas("80", "326", codQst, "");
                    listaRespostas.add(QR);
                } else if (op4 == R.id.rb3Perg2_2) {
                    QstRespostas QR = new QstRespostas("80", "327", codQst, "");
                    listaRespostas.add(QR);
                } else if (op4 == R.id.rb4Perg2_2) {
                    QstRespostas QR = new QstRespostas("80", "328", codQst, "");
                    listaRespostas.add(QR);
                } else if (op4 == R.id.rb5Perg2_2) {
                    QstRespostas QR = new QstRespostas("80", "329", codQst, "");
                    listaRespostas.add(QR);
                }


                //EXTENSÃO
                int op5 = rgPerg2_3.getCheckedRadioButtonId();
                if (op5 == R.id.rb1Perg2_3) {
                    QstRespostas QR = new QstRespostas("81", "330", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb2Perg2_3) {
                    QstRespostas QR = new QstRespostas("81", "331", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb3Perg2_3) {
                    QstRespostas QR = new QstRespostas("81", "332", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb4Perg2_3) {
                    QstRespostas QR = new QstRespostas("81", "333", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb5Perg2_3) {
                    QstRespostas QR = new QstRespostas("81", "334", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você considera que a Interdisciplinaridade entre os cursos é praticada na UNCISAL?
                int op6 = rgPerg3_1.getCheckedRadioButtonId();
                if (op6 == R.id.rb1Perg3_1) {
                    QstRespostas QR = new QstRespostas("82", "335", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6 == R.id.rb2Perg3_1) {
                    QstRespostas QR = new QstRespostas("82", "336", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6 == R.id.rb3Perg3_1) {
                    QstRespostas QR = new QstRespostas("82", "337", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6 == R.id.rb4Perg3_1) {
                    QstRespostas QR = new QstRespostas("82", "338", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você considera que a Interdisciplinaridade entre as disciplina e módulos do seu curso é praticada na UNCISAL?
                int op6_2 = rgPerg3_1.getCheckedRadioButtonId();
                if (op6_2 == R.id.rb1Perg3_2) {
                    QstRespostas QR = new QstRespostas("83", "339", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6_2 == R.id.rb2Perg3_2) {
                    QstRespostas QR = new QstRespostas("83", "340", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6_2 == R.id.rb3Perg3_2) {
                    QstRespostas QR = new QstRespostas("83", "341", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6_2 == R.id.rb4Perg3_2) {
                    QstRespostas QR = new QstRespostas("83", "342", codQst, "");
                    listaRespostas.add(QR);
                }


                //O curso incentiva a pesquisa e produção científica?
                int op7 = rgPerg4.getCheckedRadioButtonId();
                if (op7 == R.id.rb1Perg4) {
                    QstRespostas QR = new QstRespostas("84", "343", codQst, "");
                    listaRespostas.add(QR);
                } else if (op7 == R.id.rb2Perg4) {
                    QstRespostas QR = new QstRespostas("84", "344", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe Bolsa-permanência na instituição?
                int op8 = rgPerg5_1.getCheckedRadioButtonId();
                if (op8 == R.id.rb1Perg5_1) {
                    QstRespostas QR = new QstRespostas("85", "345", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb2Perg5_1) {
                    QstRespostas QR = new QstRespostas("85", "346", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb3Perg5_1) {
                    QstRespostas QR = new QstRespostas("85", "347", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb4Perg5_1) {
                    QstRespostas QR = new QstRespostas("85", "348", codQst, "");
                    listaRespostas.add(QR);
                }


                // Existe bolsa-monitoria na instituição?
                int op8_1 = rgPerg5_2.getCheckedRadioButtonId();

                if (op8_1 == R.id.rb1Perg5_2) {
                    QstRespostas QR = new QstRespostas("86", "349", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8_1 == R.id.rb2Perg5_2) {
                    QstRespostas QR = new QstRespostas("86", "350", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8_1 == R.id.rb3Perg5_2) {
                    QstRespostas QR = new QstRespostas("86", "351", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8_1 == R.id.rb4Perg5_2) {
                    QstRespostas QR = new QstRespostas("86", "352", codQst, "");
                    listaRespostas.add(QR);
                }


                //Existe bolsa-indigena na instituição?
                int op9 = rgPerg5_3.getCheckedRadioButtonId();
                if (op9 == R.id.rb1Perg5_3) {
                    QstRespostas QR = new QstRespostas("87", "353", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb2Perg5_3) {
                    QstRespostas QR = new QstRespostas("87", "354", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb3Perg5_3) {
                    QstRespostas QR = new QstRespostas("87", "355", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb4Perg5_3) {
                    QstRespostas QR = new QstRespostas("87", "356", codQst, "");
                    listaRespostas.add(QR);
                }


                // Existe bolsa-Iniciação Cientifíca na instituição?
                int op9_1 = rgPerg5_4.getCheckedRadioButtonId();
                if (op9_1 == R.id.rb1Perg5_4) { //SIM
                    QstRespostas QR = new QstRespostas("88", "357", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb2Perg5_4) {
                    QstRespostas QR = new QstRespostas("88", "358", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb3Perg5_4) {
                    QstRespostas QR = new QstRespostas("88", "359", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb4Perg5_4) {
                    QstRespostas QR = new QstRespostas("88", "360", codQst, "");
                    listaRespostas.add(QR);
                }


                //Qual o seu conhecimento quanto à prática da responsabilidade social pela Uncisal nos seguintes aspectos da inclusão social:
                int op10 = rgPerg6_1.getCheckedRadioButtonId();
                if (op10 == R.id.rb1Perg6_1) {
                    QstRespostas QR = new QstRespostas("89", "361", codQst, "");
                    listaRespostas.add(QR);
                } else if (op10 == R.id.rb2Perg6_1) {
                    QstRespostas QR = new QstRespostas("89", "362", codQst, "");
                    listaRespostas.add(QR);
                } else if (op10 == R.id.rb3Perg6_1) {
                    QstRespostas QR = new QstRespostas("89", "363", codQst, "");
                    listaRespostas.add(QR);
                } else if (op10 == R.id.rb4Perg6_1) {
                    QstRespostas QR = new QstRespostas("89", "364", codQst, "");
                    listaRespostas.add(QR);
                } else if (op10 == R.id.rb5Perg6_1) {
                    QstRespostas QR = new QstRespostas("89", "365", codQst, "");
                    listaRespostas.add(QR);
                }


                //Qual o seu conhecimento quanto à prática da responsabilidade social pela Uncisal nos seguintes aspectos do desenvolvimento econômico.:
                int op11 = rgPerg6_2.getCheckedRadioButtonId();
                if (op11 == R.id.rb1Perg6_2) {
                    QstRespostas QR = new QstRespostas("90", "366", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb2Perg6_2) {
                    QstRespostas QR = new QstRespostas("90", "367", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb3Perg6_2) {
                    QstRespostas QR = new QstRespostas("90", "368", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb4Perg6_2) {
                    QstRespostas QR = new QstRespostas("90", "369", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb5Perg6_2) {
                    QstRespostas QR = new QstRespostas("90", "370", codQst, "");
                    listaRespostas.add(QR);
                }


                //Qual o seu conhecimento quanto à prática da responsabilidade social pela Uncisal nos seguintes aspectos da melhoria da qualidade de vida
                int op12 = rgPerg6_3.getCheckedRadioButtonId();
                if (op12 == R.id.rb1Perg6_3) {
                    QstRespostas QR = new QstRespostas("91", "371", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb2Perg6_3) {
                    QstRespostas QR = new QstRespostas("91", "372", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb3Perg6_3) {
                    QstRespostas QR = new QstRespostas("91", "373", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb4Perg6_3) {
                    QstRespostas QR = new QstRespostas("91", "374", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb5Perg6_3) {
                    QstRespostas QR = new QstRespostas("91", "375", codQst, "");
                    listaRespostas.add(QR);
                }


                //Qual o seu conhecimento quanto à prática da responsabilidade social pela Uncisal nos seguintes aspectos da Infraestrutura urbana/local
                int op13 = rgPerg6_4.getCheckedRadioButtonId();
                if (op13 == R.id.rb1Perg6_4) {
                    QstRespostas QR = new QstRespostas("92", "376", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb2Perg6_4) {
                    QstRespostas QR = new QstRespostas("92", "377", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb3Perg6_4) {
                    QstRespostas QR = new QstRespostas("92", "378", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb4Perg6_4) {
                    QstRespostas QR = new QstRespostas("92", "379", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb5Perg6_4) {
                    QstRespostas QR = new QstRespostas("92", "380", codQst, "");
                    listaRespostas.add(QR);
                }


                //Qual o seu conhecimento quanto à prática da responsabilidade social pela Uncisal nos seguintes aspectos da Inovação social
                int op14 = rgPerg6_5.getCheckedRadioButtonId();
                if (op14 == R.id.rb1Perg6_5) {
                    QstRespostas QR = new QstRespostas("93", "381", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb2Perg6_5) {
                    QstRespostas QR = new QstRespostas("93", "382", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb3Perg6_5) {
                    QstRespostas QR = new QstRespostas("93", "383", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb4Perg6_5) {
                    QstRespostas QR = new QstRespostas("93", "384", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb5Perg6_5) {
                    QstRespostas QR = new QstRespostas("93", "385", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a comunicação da Uncisal com o público externo?
                int op15 = rgPerg7.getCheckedRadioButtonId();
                if (op15 == R.id.rb1Perg7) {
                    QstRespostas QR = new QstRespostas("94", "386", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb2Perg7) {
                    QstRespostas QR = new QstRespostas("94", "387", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb3Perg7) {
                    QstRespostas QR = new QstRespostas("94", "388", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb4Perg7) {
                    QstRespostas QR = new QstRespostas("94", "389", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb5Perg7) {
                    QstRespostas QR = new QstRespostas("94", "390", codQst, "");
                    listaRespostas.add(QR);
                }

                //Como você avalia a transparência das ações na Uncisal.
                int op16 = rgPerg8.getCheckedRadioButtonId();
                if (op16 == R.id.rb1Perg8) {
                    QstRespostas QR = new QstRespostas("95", "391", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb2Perg8) {
                    QstRespostas QR = new QstRespostas("95", "392", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb3Perg8) {
                    QstRespostas QR = new QstRespostas("95", "393", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb4Perg8) {
                    QstRespostas QR = new QstRespostas("95", "394", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb5Perg8) {
                    QstRespostas QR = new QstRespostas("95", "395", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você tem conhecimento dos órgãos de gestão e colegiados da Uncisal?
                int op17 = rgPerg9.getCheckedRadioButtonId();
                if (op17 == R.id.rb1Perg9) {
                    QstRespostas QR = new QstRespostas("96", "396", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb2Perg9) {
                    QstRespostas QR = new QstRespostas("96", "397", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a representatividade dos discentes nos órgãos colegiados e de gestão?
                int op18 = rgPerg10.getCheckedRadioButtonId();
                if (op18 == R.id.rb1Perg10) {
                    QstRespostas QR = new QstRespostas("97", "398", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb2Perg10) {
                    QstRespostas QR = new QstRespostas("97", "399", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb3Perg10) {
                    QstRespostas QR = new QstRespostas("97", "400", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb4Perg10) {
                    QstRespostas QR = new QstRespostas("97", "401", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb5Perg10) {
                    QstRespostas QR = new QstRespostas("97", "402", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a atuação dos representantes discentes nos órgãos colegiados e de gestão?
                int op19 = rgPerg11.getCheckedRadioButtonId();
                if (op19 == R.id.rb1Perg11) {
                    QstRespostas QR = new QstRespostas("98", "403", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb2Perg11) {
                    QstRespostas QR = new QstRespostas("98", "404", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb3Perg11) {
                    QstRespostas QR = new QstRespostas("98", "405", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb4Perg11) {
                    QstRespostas QR = new QstRespostas("98", "406", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb5Perg11) {
                    QstRespostas QR = new QstRespostas("98", "407", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia os espaços de convivência e lazer
                int op20 = rgPerg12_1.getCheckedRadioButtonId();
                if (op20 == R.id.rb1Perg12_1) {
                    QstRespostas QR = new QstRespostas("99", "408", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb2Perg12_1) {
                    QstRespostas QR = new QstRespostas("99", "409", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb3Perg12_1) {
                    QstRespostas QR = new QstRespostas("99", "410", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb4Perg12_1) {
                    QstRespostas QR = new QstRespostas("99", "411", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb5Perg12_1) {
                    QstRespostas QR = new QstRespostas("99", "412", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia as condições das salas de aula
                int op21 = rgPerg12_2.getCheckedRadioButtonId();
                if (op21 == R.id.rb1Perg12_2) {
                    QstRespostas QR = new QstRespostas("100", "413", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb2Perg12_2) {
                    QstRespostas QR = new QstRespostas("100", "414", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb3Perg12_2) {
                    QstRespostas QR = new QstRespostas("100", "415", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb4Perg12_2) {
                    QstRespostas QR = new QstRespostas("100", "416", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb5Perg12_2) {
                    QstRespostas QR = new QstRespostas("100", "417", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia os equipamentos de mídia disponíveis nas salas de aula
                int op22 = rgPerg12_3.getCheckedRadioButtonId();
                if (op22 == R.id.rb1Perg12_3) {
                    QstRespostas QR = new QstRespostas("101", "418", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb2Perg12_3) {
                    QstRespostas QR = new QstRespostas("101", "419", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb3Perg12_3) {
                    QstRespostas QR = new QstRespostas("101", "420", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb4Perg12_3) {
                    QstRespostas QR = new QstRespostas("101", "421", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb5Perg12_3) {
                    QstRespostas QR = new QstRespostas("101", "422", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia as cadeiras disponíveis nas salas de aula:
                int op23 = rgPerg12_4.getCheckedRadioButtonId();
                if (op23 == R.id.rb1Perg12_4) {
                    QstRespostas QR = new QstRespostas("102", "423", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb2Perg12_4) {
                    QstRespostas QR = new QstRespostas("102", "424", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb3Perg12_4) {
                    QstRespostas QR = new QstRespostas("102", "425", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb4Perg12_4) {
                    QstRespostas QR = new QstRespostas("102", "426", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb5Perg12_4) {
                    QstRespostas QR = new QstRespostas("102", "427", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia o espaço físico para refeições:
                int op24 = rgPerg12_5.getCheckedRadioButtonId();
                if (op24 == R.id.rb1Perg12_5) {
                    QstRespostas QR = new QstRespostas("103", "428", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb2Perg12_5) {
                    QstRespostas QR = new QstRespostas("103", "429", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb3Perg12_5) {
                    QstRespostas QR = new QstRespostas("103", "430", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb4Perg12_5) {
                    QstRespostas QR = new QstRespostas("103", "431", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb5Perg12_5) {
                    QstRespostas QR = new QstRespostas("103", "432", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a biblioteca em relação a Acervo Físico
                int op25 = rgPerg13_1.getCheckedRadioButtonId();
                if (op25 == R.id.rb1Perg13_1) {
                    QstRespostas QR = new QstRespostas("104", "433", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb2Perg13_1) {
                    QstRespostas QR = new QstRespostas("104", "434", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb3Perg13_1) {
                    QstRespostas QR = new QstRespostas("104", "435", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb4Perg13_1) {
                    QstRespostas QR = new QstRespostas("104", "436", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb5Perg13_1) {
                    QstRespostas QR = new QstRespostas("104", "437", codQst, "");
                    listaRespostas.add(QR);
                }else if (op25 == R.id.rb6Perg13_1) {
                    QstRespostas QR = new QstRespostas("104", "570", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a biblioteca em relação a Acervo virtual
                int op26 = rgPerg13_2.getCheckedRadioButtonId();
                if (op26 == R.id.rb1Perg13_2) {
                    QstRespostas QR = new QstRespostas("105", "438", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb2Perg13_2) {
                    QstRespostas QR = new QstRespostas("105", "439", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb3Perg13_2) {
                    QstRespostas QR = new QstRespostas("105", "440", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb4Perg13_2) {
                    QstRespostas QR = new QstRespostas("105", "441", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb5Perg13_2) {
                    QstRespostas QR = new QstRespostas("105", "442", codQst, "");
                    listaRespostas.add(QR);
                }else if (op26 == R.id.rb6Perg13_2) {
                    QstRespostas QR = new QstRespostas("105", "571", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a biblioteca em relação a Espaço físico
                int op27 = rgPerg13_3.getCheckedRadioButtonId();
                if (op27 == R.id.rb1Perg13_3) {
                    QstRespostas QR = new QstRespostas("106", "443", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb2Perg13_3) {
                    QstRespostas QR = new QstRespostas("106", "444", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb3Perg13_3) {
                    QstRespostas QR = new QstRespostas("106", "445", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb4Perg13_3) {
                    QstRespostas QR = new QstRespostas("106", "446", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb5Perg13_3) {
                    QstRespostas QR = new QstRespostas("106", "447", codQst, "");
                    listaRespostas.add(QR);
                }else if (op27 == R.id.rb6Perg13_3) {
                    QstRespostas QR = new QstRespostas("106", "572", codQst, "");
                    listaRespostas.add(QR);
                }

                //Como você classifica a acessibilidade da instituição no que concerne a Acessibilidade Digital
                int op28 = rgPerg14_1.getCheckedRadioButtonId();
                if (op28 == R.id.rb1Perg14_1) {
                    QstRespostas QR = new QstRespostas("107", "448", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb2Perg14_1) {
                    QstRespostas QR = new QstRespostas("107", "449", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb3Perg14_1) {
                    QstRespostas QR = new QstRespostas("107", "450", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb4Perg14_1) {
                    QstRespostas QR = new QstRespostas("107", "451", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb5Perg14_1) {
                    QstRespostas QR = new QstRespostas("107", "452", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você classifica a acessibilidade da instituição no que concerne a Acessibilidade Arquitetônica
                int op29 = rgPerg14_2.getCheckedRadioButtonId();
                if (op29 == R.id.rb1Perg14_2) {
                    QstRespostas QR = new QstRespostas("108", "453", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb2Perg14_2) {
                    QstRespostas QR = new QstRespostas("108", "454", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb3Perg14_2) {
                    QstRespostas QR = new QstRespostas("108", "455", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb4Perg14_2) {
                    QstRespostas QR = new QstRespostas("108", "456", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb5Perg14_2) {
                    QstRespostas QR = new QstRespostas("108", "457", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você classifica a acessibilidade da instituição no que concerne a Acessibilidade Atitudinal
                int op30 = rgPerg14_3.getCheckedRadioButtonId();
                if (op30 == R.id.rb1Perg14_3) {
                    QstRespostas QR = new QstRespostas("109", "458", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb2Perg14_3) {
                    QstRespostas QR = new QstRespostas("109", "459", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb3Perg14_3) {
                    QstRespostas QR = new QstRespostas("109", "460", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb4Perg14_3) {
                    QstRespostas QR = new QstRespostas("109", "461", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb5Perg14_3) {
                    QstRespostas QR = new QstRespostas("109", "462", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você classifica a acessibilidade da instituição no que concerne a Acessibilidade Pedagógica
                int op31 = rgPerg14_4.getCheckedRadioButtonId();
                if (op31 == R.id.rb1Perg14_4) {
                    QstRespostas QR = new QstRespostas("110", "463", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb2Perg14_4) {
                    QstRespostas QR = new QstRespostas("110", "464", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb3Perg14_4) {
                    QstRespostas QR = new QstRespostas("110", "465", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb4Perg14_4) {
                    QstRespostas QR = new QstRespostas("110", "466", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb5Perg14_4) {
                    QstRespostas QR = new QstRespostas("110", "467", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você classifica a acessibilidade da instituição no que concerne a Acessibilidade nas comunicações
                int op32 = rgPerg14_5.getCheckedRadioButtonId();
                if (op32 == R.id.rb1Perg14_5) {
                    QstRespostas QR = new QstRespostas("111", "468", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb2Perg14_5) {
                    QstRespostas QR = new QstRespostas("111", "469", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb3Perg14_5) {
                    QstRespostas QR = new QstRespostas("111", "470", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb4Perg14_5) {
                    QstRespostas QR = new QstRespostas("111", "471", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb5Perg14_5) {
                    QstRespostas QR = new QstRespostas("111", "472", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia o laboratório de informática em relação à quantidade dos equipamentos
                int op33 = rgPerg15_1.getCheckedRadioButtonId();
                if (op33 == R.id.rb1Perg15_1) {
                    QstRespostas QR = new QstRespostas("112", "473", codQst, "");
                    listaRespostas.add(QR);
                } else if (op33 == R.id.rb2Perg15_1) {
                    QstRespostas QR = new QstRespostas("112", "474", codQst, "");
                    listaRespostas.add(QR);
                } else if (op33 == R.id.rb3Perg15_1) {
                    QstRespostas QR = new QstRespostas("112", "475", codQst, "");
                    listaRespostas.add(QR);
                } else if (op33 == R.id.rb4Perg15_1) {
                    QstRespostas QR = new QstRespostas("112", "476", codQst, "");
                    listaRespostas.add(QR);
                } else if (op33 == R.id.rb5Perg15_1) {
                    QstRespostas QR = new QstRespostas("112", "477", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia o laboratório de informática em relação à Qualidade dos equipamentos
                int op34 = rgPerg15_2.getCheckedRadioButtonId();
                if (op34 == R.id.rb1Perg15_2) {
                    QstRespostas QR = new QstRespostas("113", "478", codQst, "");
                    listaRespostas.add(QR);
                } else if (op34 == R.id.rb2Perg15_2) {
                    QstRespostas QR = new QstRespostas("113", "479", codQst, "");
                    listaRespostas.add(QR);
                } else if (op34 == R.id.rb3Perg15_2) {
                    QstRespostas QR = new QstRespostas("113", "480", codQst, "");
                    listaRespostas.add(QR);
                } else if (op34 == R.id.rb4Perg15_2) {
                    QstRespostas QR = new QstRespostas("113", "481", codQst, "");
                    listaRespostas.add(QR);
                } else if (op34 == R.id.rb5Perg15_2) {
                    QstRespostas QR = new QstRespostas("113", "482", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia o laboratório de ensino do seu curso?
                int op35 = rgPerg16.getCheckedRadioButtonId();
                if (op35 == R.id.rb1Perg16) {
                    QstRespostas QR = new QstRespostas("114", "483", codQst, "");
                    listaRespostas.add(QR);
                } else if (op35 == R.id.rb2Perg16) {
                    QstRespostas QR = new QstRespostas("114", "484", codQst, "");
                    listaRespostas.add(QR);
                } else if (op35 == R.id.rb3Perg16) {
                    QstRespostas QR = new QstRespostas("114", "485", codQst, "");
                    listaRespostas.add(QR);
                } else if (op35 == R.id.rb4Perg16) {
                    QstRespostas QR = new QstRespostas("114", "486", codQst, "");
                    listaRespostas.add(QR);
                } else if (op35 == R.id.rb5Perg16) {
                    QstRespostas QR = new QstRespostas("114", "487", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você está satisfeito com as regras de estacionamento?
                int op36 = rgPerg17.getCheckedRadioButtonId();
                if (op36 == R.id.rb1Perg17) {
                    QstRespostas QR = new QstRespostas("115", "488", codQst, "");
                    listaRespostas.add(QR);
                } else if (op36 == R.id.rb2Perg17) {
                    QstRespostas QR = new QstRespostas("115", "489", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você está satisfeito com a disponibilidade de vagas no estacionamento?
                int op37 = rgPerg18.getCheckedRadioButtonId();
                if (op37 == R.id.rb1Perg18) {
                    QstRespostas QR = new QstRespostas("116", "490", codQst, "");
                    listaRespostas.add(QR);
                } else if (op37 == R.id.rb2Perg18) {
                    QstRespostas QR = new QstRespostas("116", "491", codQst, "");
                    listaRespostas.add(QR);
                }

                //Como você avalia o cumprimento das normativas relativas ao processo de avaliação da aprendizagem (Reavaliação, Provas, Notas por Unidades Programadas).
                int op38 = rgPerg19.getCheckedRadioButtonId();
                if (op38 == R.id.rb1Perg19) {
                    QstRespostas QR = new QstRespostas("117", "492", codQst, "");
                    listaRespostas.add(QR);
                } else if (op38 == R.id.rb2Perg19) {
                    QstRespostas QR = new QstRespostas("117", "493", codQst, "");
                    listaRespostas.add(QR);
                } else if (op38 == R.id.rb3Perg19) {
                    QstRespostas QR = new QstRespostas("117", "494", codQst, "");
                    listaRespostas.add(QR);
                } else if (op38 == R.id.rb4Perg19) {
                    QstRespostas QR = new QstRespostas("117", "495", codQst, "");
                    listaRespostas.add(QR);
                } else if (op38 == R.id.rb5Perg19) {
                    QstRespostas QR = new QstRespostas("117", "496", codQst, "");
                    listaRespostas.add(QR);
                }

                //Você está satisfeito com as normativas relativas ao processo de avaliação da aprendizagem (Reavaliação, Provas, Notas por Unidades Programadas)?
                int op39 = rgPerg20.getCheckedRadioButtonId();
                if (op39 == R.id.rb1Perg20) {
                    QstRespostas QR = new QstRespostas("118", "497", codQst, "");
                    listaRespostas.add(QR);
                } else if (op39 == R.id.rb2Perg20) {
                    QstRespostas QR = new QstRespostas("118", "498", codQst, "");
                    listaRespostas.add(QR);
                }



                //Como você avalia a relação da coordenação do curso com os discentes:
                int op40 = rgPerg21_1.getCheckedRadioButtonId();
                if (op40 == R.id.rb1Perg21_1) {
                    QstRespostas QR = new QstRespostas("119", "499", codQst, "");
                    listaRespostas.add(QR);
                } else if (op40 == R.id.rb2Perg21_1) {
                    QstRespostas QR = new QstRespostas("119", "500", codQst, "");
                    listaRespostas.add(QR);
                } else if (op40 == R.id.rb3Perg21_1) {
                    QstRespostas QR = new QstRespostas("119", "501", codQst, "");
                    listaRespostas.add(QR);
                } else if (op40 == R.id.rb4Perg21_1) {
                    QstRespostas QR = new QstRespostas("119", "502", codQst, "");
                    listaRespostas.add(QR);
                } else if (op40 == R.id.rb5Perg21_1) {
                    QstRespostas QR = new QstRespostas("119", "503", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a relação da pró-reitoria de ensino com os discentes:
                int op41 = rgPerg21_2.getCheckedRadioButtonId();
                if (op41 == R.id.rb1Perg21_2) {
                    QstRespostas QR = new QstRespostas("120", "504", codQst, "");
                    listaRespostas.add(QR);
                } else if (op41 == R.id.rb2Perg21_2) {
                    QstRespostas QR = new QstRespostas("120", "505", codQst, "");
                    listaRespostas.add(QR);
                } else if (op41 == R.id.rb3Perg21_2) {
                    QstRespostas QR = new QstRespostas("120", "506", codQst, "");
                    listaRespostas.add(QR);
                } else if (op41 == R.id.rb4Perg21_2) {
                    QstRespostas QR = new QstRespostas("120", "507", codQst, "");
                    listaRespostas.add(QR);
                } else if (op41 == R.id.rb5Perg21_2) {
                    QstRespostas QR = new QstRespostas("120", "508", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a relação da pró-reitoria de Extensão com os discentes:
                int op42 = rgPerg21_3.getCheckedRadioButtonId();
                if (op42 == R.id.rb1Perg21_3) {
                    QstRespostas QR = new QstRespostas("121", "509", codQst, "");
                    listaRespostas.add(QR);
                } else if (op42 == R.id.rb2Perg21_3) {
                    QstRespostas QR = new QstRespostas("121", "510", codQst, "");
                    listaRespostas.add(QR);
                } else if (op42 == R.id.rb3Perg21_3) {
                    QstRespostas QR = new QstRespostas("121", "511", codQst, "");
                    listaRespostas.add(QR);
                } else if (op42 == R.id.rb4Perg21_3) {
                    QstRespostas QR = new QstRespostas("121", "512", codQst, "");
                    listaRespostas.add(QR);
                } else if (op42 == R.id.rb5Perg21_3) {
                    QstRespostas QR = new QstRespostas("121", "513", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a relação da pró-reitoria de Gestão de Pessoas com os discentes:
                int op43 = rgPerg21_4.getCheckedRadioButtonId();
                if (op43 == R.id.rb1Perg21_4) {
                    QstRespostas QR = new QstRespostas("122", "514", codQst, "");
                    listaRespostas.add(QR);
                } else if (op43 == R.id.rb2Perg21_4) {
                    QstRespostas QR = new QstRespostas("122", "515", codQst, "");
                    listaRespostas.add(QR);
                } else if (op43 == R.id.rb3Perg21_4) {
                    QstRespostas QR = new QstRespostas("122", "516", codQst, "");
                    listaRespostas.add(QR);
                } else if (op43 == R.id.rb4Perg21_4) {
                    QstRespostas QR = new QstRespostas("122", "517", codQst, "");
                    listaRespostas.add(QR);
                } else if (op43 == R.id.rb5Perg21_4) {
                    QstRespostas QR = new QstRespostas("122", "518", codQst, "");
                    listaRespostas.add(QR);
                }



                //Como você avalia a relação da pró-reitoria estudantil com os discentes:
                int op44 = rgPerg21_5.getCheckedRadioButtonId();
                if (op44 == R.id.rb1Perg21_5) {
                    QstRespostas QR = new QstRespostas("123", "519", codQst, "");
                    listaRespostas.add(QR);
                } else if (op44 == R.id.rb2Perg21_5) {
                    QstRespostas QR = new QstRespostas("123", "520", codQst, "");
                    listaRespostas.add(QR);
                } else if (op44 == R.id.rb3Perg21_5) {
                    QstRespostas QR = new QstRespostas("123", "521", codQst, "");
                    listaRespostas.add(QR);
                } else if (op44 == R.id.rb4Perg21_5) {
                    QstRespostas QR = new QstRespostas("123", "522", codQst, "");
                    listaRespostas.add(QR);
                } else if (op44 == R.id.rb5Perg21_5) {
                    QstRespostas QR = new QstRespostas("123", "523", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a relação da pró-reitoria de pesquisa e pós-graduação com os discentes:
                int op45 = rgPerg21_6.getCheckedRadioButtonId();
                if (op45 == R.id.rb1Perg21_6) {
                    QstRespostas QR = new QstRespostas("124", "524", codQst, "");
                    listaRespostas.add(QR);
                } else if (op45 == R.id.rb2Perg21_6) {
                    QstRespostas QR = new QstRespostas("124", "525", codQst, "");
                    listaRespostas.add(QR);
                } else if (op45 == R.id.rb3Perg21_6) {
                    QstRespostas QR = new QstRespostas("124", "526", codQst, "");
                    listaRespostas.add(QR);
                } else if (op45 == R.id.rb4Perg21_6) {
                    QstRespostas QR = new QstRespostas("124", "527", codQst, "");
                    listaRespostas.add(QR);
                } else if (op45 == R.id.rb5Perg21_6) {
                    QstRespostas QR = new QstRespostas("124", "528", codQst, "");
                    listaRespostas.add(QR);
                }


                //Para você há necessidade da oferta de uma Residência Universitária (moradia) pela Uncisal?
                int op46 = rgPerg22.getCheckedRadioButtonId();
                if (op46 == R.id.rb1Perg22) {
                    QstRespostas QR = new QstRespostas("125", "529", codQst, "");
                    listaRespostas.add(QR);
                } else if (op46 == R.id.rb2Perg22) {
                    QstRespostas QR = new QstRespostas("125", "530", codQst, "");
                    listaRespostas.add(QR);
                }


                //Na sua instituição existe uma ouvidoria?
                int op47 = rgPerg23.getCheckedRadioButtonId();
                if (op47 == R.id.rb1Perg23) {
                    QstRespostas QR = new QstRespostas("126", "531", codQst, "");
                    listaRespostas.add(QR);
                } else if (op47 == R.id.rb2Perg23) {
                    QstRespostas QR = new QstRespostas("126", "532", codQst, "");
                    listaRespostas.add(QR);
                } else if (op47 == R.id.rb3Perg23) {
                    QstRespostas QR = new QstRespostas("126", "533", codQst, "");
                    listaRespostas.add(QR);
                } else if (op47 == R.id.rb4Perg23) {
                    QstRespostas QR = new QstRespostas("126", "534", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a sustentabilidade financeira da instituição?
                int op48 = rgPerg24.getCheckedRadioButtonId();
                if (op48 == R.id.rb1Perg24) {
                    QstRespostas QR = new QstRespostas("127", "535", codQst, "");
                    listaRespostas.add(QR);
                } else if (op48 == R.id.rb2Perg24) {
                    QstRespostas QR = new QstRespostas("127", "536", codQst, "");
                    listaRespostas.add(QR);
                } else if (op48 == R.id.rb3Perg24) {
                    QstRespostas QR = new QstRespostas("127", "537", codQst, "");
                    listaRespostas.add(QR);
                } else if (op48 == R.id.rb4Perg24) {
                    QstRespostas QR = new QstRespostas("127", "538", codQst, "");
                    listaRespostas.add(QR);
                } else if (op48 == R.id.rb5Perg24) {
                    QstRespostas QR = new QstRespostas("127", "539", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia os recursos demandados para as políticas de atendimento ao discente.
                int op49 = rgPerg25.getCheckedRadioButtonId();
                if (op49 == R.id.rb1Perg25) {
                    QstRespostas QR = new QstRespostas("128", "545", codQst, "");
                    listaRespostas.add(QR);
                } else if (op49 == R.id.rb2Perg25) {
                    QstRespostas QR = new QstRespostas("128", "546", codQst, "");
                    listaRespostas.add(QR);
                } else if (op49 == R.id.rb3Perg25) {
                    QstRespostas QR = new QstRespostas("128", "547", codQst, "");
                    listaRespostas.add(QR);
                } else if (op49 == R.id.rb4Perg25) {
                    QstRespostas QR = new QstRespostas("128", "548", codQst, "");
                    listaRespostas.add(QR);
                } else if (op49 == R.id.rb5Perg25) {
                    QstRespostas QR = new QstRespostas("128", "549", codQst, "");
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
                if (listaRespostas.size() >= 48) { //São 50 respostas para ser preenchido todo questionário
                    //Inserindo no objeto e transformando para JSON
                    HistQst histQst = new HistQst();
                    histQst.setUn_data_hist(UtilTCM.getDate(CONSTANTE_DATA_ATUAL));
                    histQst.setUn_desc_hist("Aplicação do Formulário CPA de Alunos da Uncisal");
                    histQst.setUn_cod_hist_qst(codQst);
                    histQst.setUn_cod_hist_pes(String.valueOf(mDados.getId()));

                    String jsonHistQst = histQst.histQstToJSON(histQst); //JSON para ser salvo no Histórico do Questionário Aplicado

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
                Toast.makeText(FormAlunosActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            } else {
                Toast.makeText(FormAlunosActivity.this, result, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

        }
    }


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
        if (rgPerg1_1.getCheckedRadioButtonId() == R.id.rb1Perg1_1) {
            layoutRgPerg1_2.setVisibility(0);//
        } else {
            layoutRgPerg1_2.setVisibility(4);
            rgPerg1_2.clearCheck();//
        }
    }

}
