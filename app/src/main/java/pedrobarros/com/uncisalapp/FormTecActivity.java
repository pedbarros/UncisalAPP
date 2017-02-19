package pedrobarros.com.uncisalapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Conexao.HttpUrlConn;
import pedrobarros.com.uncisalapp.Extras.MySingleton;
import pedrobarros.com.uncisalapp.Extras.UtilTCM;
import pedrobarros.com.uncisalapp.Model.HistQst;
import pedrobarros.com.uncisalapp.Model.QstRespostas;
/**
 * Created by Pedro Barros
 * Classe responsável por aplicar o questionário dos técnicos
 * Utilizada:
 * Criação: 26/05/2016.
 */
public class FormTecActivity extends AppCompatActivity {

    private static final String codQst = "3"; //Código do questionário de Técnicos
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
    @Bind(R.id.rgPerg1_2)
    RadioGroup rgPerg1_2;
    @Bind(R.id.rgPerg1_3)
    RadioGroup rgPerg1_3;
    @Bind(R.id.rgPerg1_4)
    RadioGroup rgPerg1_4;
    //2 questão
    @Bind(R.id.rgPerg2)
    RadioGroup rgPerg2;
    //3 questão
    @Bind(R.id.rgPerg3)
    RadioGroup rgPerg3;
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
    @Bind(R.id.rgPerg7)
    RadioGroup rgPerg7;
    //8 questão
    @Bind(R.id.rgPerg8)
    RadioGroup rgPerg8;
    //9 questão
    @Bind(R.id.rgPerg9)
    RadioGroup rgPerg9;
    //10 questão
    @Bind(R.id.rgPerg10)
    RadioGroup rgPerg10;
    //11 questão
    @Bind(R.id.rgPerg11)
    RadioGroup rgPerg11;
    //12_1 questão
    @Bind(R.id.rgPerg12_1)
    RadioGroup rgPerg12_1;
    //12_2 questão
    @Bind(R.id.rgPerg12_2)
    RadioGroup rgPerg12_2;
    //13 questão
    @Bind(R.id.rgPerg13)
    RadioGroup rgPerg13;
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
    //18_1 questão
    @Bind(R.id.rgPerg18_1)
    RadioGroup rgPerg18_1;
    //19 questão
    @Bind(R.id.rgPerg19)
    RadioGroup rgPerg19;
    //20 questão
    @Bind(R.id.rgPerg20)
    RadioGroup rgPerg20;
    //21 questão
    @Bind(R.id.rgPerg21)
    RadioGroup rgPerg21;
    //22 questão
    @Bind(R.id.rgPerg22)
    RadioGroup rgPerg22;
    //23 questão
    @Bind(R.id.rgPerg23)
    RadioGroup rgPerg23;
    //24 questão
    @Bind(R.id.rgPerg24)
    RadioGroup rgPerg24;
    //25 questão
    @Bind(R.id.rgPerg25)
    RadioGroup rgPerg25;
    //26 questão
    @Bind(R.id.rgPerg26)
    RadioGroup rgPerg26;
    //27 questão
    @Bind(R.id.rgPerg27)
    RadioGroup rgPerg27;


    @Bind(R.id.layoutRgPerg18)
    LinearLayout layoutRgPerg18;


    String resposta; //Retorno das respostas do Questionário -  1 - (Deu certo) | 0 - (Bixado)

    MySingleton mDados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tec);
        ButterKnife.bind(this);


        //TOOLBAR
        mToolbar.setTitle("UncisalQST");
        mToolbar.setSubtitle("Questionário CPA dos Técnicos UNCISAL");
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

                insereFormTec insereFT = new insereFormTec();
                insereFT.execute();

            }
        });

    }

    /**
     * Classe responsável para inserir em background com AsyncTask
     */
    private class insereFormTec extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(FormTecActivity.this);

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(FormTecActivity.this);
            dialog.setMessage("Inserindo...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                //Array onde será armazenada todas as respostas
                List<QstRespostas> listaRespostas = new ArrayList<>();

                //Ensino
                int op1 = rgPerg1_1.getCheckedRadioButtonId();
                if (op1 == R.id.rb1Perg1_1) {
                    QstRespostas QR = new QstRespostas("25", "98", codQst, "");
                    listaRespostas.add(QR);
                } else if (op1 == R.id.rb2Perg1_1) {
                    QstRespostas QR = new QstRespostas("25", "99", codQst, "");
                    listaRespostas.add(QR);
                }

                //Pesquisa
                int op2 = rgPerg1_2.getCheckedRadioButtonId();
                if (op2 == R.id.rb1Perg1_2) {
                    QstRespostas QR = new QstRespostas("25", "100", codQst, "");
                    listaRespostas.add(QR);
                } else if (op2 == R.id.rb2Perg1_2) {
                    QstRespostas QR = new QstRespostas("25", "101", codQst, "");
                    listaRespostas.add(QR);
                }

                //Assistência
                int op3 = rgPerg1_3.getCheckedRadioButtonId();
                if (op3 == R.id.rb1Perg1_3) {
                    QstRespostas QR = new QstRespostas("25", "102", codQst, "");
                    listaRespostas.add(QR);
                } else if (op3 == R.id.rb2Perg1_3) {
                    QstRespostas QR = new QstRespostas("25", "103", codQst, "");
                    listaRespostas.add(QR);
                }

                //Extensão
                int op4 = rgPerg1_4.getCheckedRadioButtonId();
                if (op4 == R.id.rb1Perg1_4) {
                    QstRespostas QR = new QstRespostas("25", "104", codQst, "");
                    listaRespostas.add(QR);
                } else if (op4 == R.id.rb2Perg1_4) {
                    QstRespostas QR = new QstRespostas("25", "105", codQst, "");
                    listaRespostas.add(QR);
                }


                //O PDI foi elaborado baseado nas demandas apontadas pelo relatório da CPA?
                int op5 = rgPerg2.getCheckedRadioButtonId();
                if (op5 == R.id.rb1Perg2) {
                    QstRespostas QR = new QstRespostas("26", "106", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb2Perg2) {
                    QstRespostas QR = new QstRespostas("26", "107", codQst, "");
                    listaRespostas.add(QR);
                } else if (op5 == R.id.rb3Perg2) {
                    QstRespostas QR = new QstRespostas("26", "108", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você considera as ações da Uncisal alinhadas com a sua missão?
                int op6 = rgPerg3.getCheckedRadioButtonId();
                if (op6 == R.id.rb1Perg3) {
                    QstRespostas QR = new QstRespostas("27", "109", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6 == R.id.rb2Perg3) {
                    QstRespostas QR = new QstRespostas("27", "110", codQst, "");
                    listaRespostas.add(QR);
                } else if (op6 == R.id.rb3Perg3) {
                    QstRespostas QR = new QstRespostas("27", "111", codQst, "");
                    listaRespostas.add(QR);
                }


                //A avaliação institucional é utilizada para o planejamento institucional?
                int op7 = rgPerg4.getCheckedRadioButtonId();
                if (op7 == R.id.rb1Perg4) {
                    QstRespostas QR = new QstRespostas("28", "112", codQst, "");
                    listaRespostas.add(QR);
                } else if (op7 == R.id.rb2Perg4) {
                    QstRespostas QR = new QstRespostas("28", "113", codQst, "");
                    listaRespostas.add(QR);
                } else if (op7 == R.id.rb3Perg4) {
                    QstRespostas QR = new QstRespostas("28", "114", codQst, "");
                    listaRespostas.add(QR);
                }


                //As demandas oriundas do relatório da CPA são atendidas?
                int op8 = rgPerg5.getCheckedRadioButtonId();
                if (op8 == R.id.rb1Perg5) {
                    QstRespostas QR = new QstRespostas("29", "115", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb2Perg5) {
                    QstRespostas QR = new QstRespostas("29", "116", codQst, "");
                    listaRespostas.add(QR);
                } else if (op8 == R.id.rb3Perg5) {
                    QstRespostas QR = new QstRespostas("29", "117", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você conhece o PDI da Uncisal?
                int op9 = rgPerg6.getCheckedRadioButtonId();
                if (op9 == R.id.rb1Perg6) {
                    QstRespostas QR = new QstRespostas("30", "118", codQst, "");
                    listaRespostas.add(QR);
                } else if (op9 == R.id.rb2Perg6) {
                    QstRespostas QR = new QstRespostas("30", "119", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você conhece a missão da Uncisal?
                int op10 = rgPerg7.getCheckedRadioButtonId();
                if (op10 == R.id.rb1Perg7) {
                    QstRespostas QR = new QstRespostas("31", "120", codQst, "");
                    listaRespostas.add(QR);
                } else if (op10 == R.id.rb2Perg7) {
                    QstRespostas QR = new QstRespostas("31", "121", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera as ações sociais da Uncisal?
                int op11 = rgPerg8.getCheckedRadioButtonId();
                if (op11 == R.id.rb1Perg8) {
                    QstRespostas QR = new QstRespostas("32", "122", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb2Perg8) {
                    QstRespostas QR = new QstRespostas("32", "123", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb3Perg8) {
                    QstRespostas QR = new QstRespostas("32", "124", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb4Perg8) {
                    QstRespostas QR = new QstRespostas("32", "125", codQst, "");
                    listaRespostas.add(QR);
                } else if (op11 == R.id.rb5Perg8) {
                    QstRespostas QR = new QstRespostas("32", "126", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a comunicação da Universidade com a sociedade?
                int op12 = rgPerg9.getCheckedRadioButtonId();
                if (op12 == R.id.rb1Perg9) {
                    QstRespostas QR = new QstRespostas("33", "127", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb2Perg9) {
                    QstRespostas QR = new QstRespostas("33", "128", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb3Perg9) {
                    QstRespostas QR = new QstRespostas("33", "129", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb4Perg9) {
                    QstRespostas QR = new QstRespostas("33", "130", codQst, "");
                    listaRespostas.add(QR);
                } else if (op12 == R.id.rb5Perg9) {
                    QstRespostas QR = new QstRespostas("33", "131", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a politica de atendimento aos discentes?
                int op13 = rgPerg10.getCheckedRadioButtonId();
                if (op13 == R.id.rb1Perg10) {
                    QstRespostas QR = new QstRespostas("34", "132", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb2Perg10) {
                    QstRespostas QR = new QstRespostas("34", "133", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb3Perg10) {
                    QstRespostas QR = new QstRespostas("34", "135", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb4Perg10) {
                    QstRespostas QR = new QstRespostas("34", "136", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb5Perg10) {
                    QstRespostas QR = new QstRespostas("34", "137", codQst, "");
                    listaRespostas.add(QR);
                } else if (op13 == R.id.rb6Perg10) {
                    QstRespostas QR = new QstRespostas("34", "138", codQst, "");
                    listaRespostas.add(QR);
                }


                //De que forma a estrutura física da UNCISAL interfere na qualidade das políticas acadêmicas?
                int op14 = rgPerg11.getCheckedRadioButtonId();
                if (op14 == R.id.rb1Perg11) {
                    QstRespostas QR = new QstRespostas("35", "139", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb2Perg11) {
                    QstRespostas QR = new QstRespostas("35", "140", codQst, "");
                    listaRespostas.add(QR);
                } else if (op14 == R.id.rb3Perg11) {
                    QstRespostas QR = new QstRespostas("35", "141", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera o site da UNCISAL em relação a layout
                int op15 = rgPerg12_1.getCheckedRadioButtonId();
                if (op15 == R.id.rb1Perg12_1) {
                    QstRespostas QR = new QstRespostas("36", "142", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb2Perg12_1) {
                    QstRespostas QR = new QstRespostas("36", "143", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb3Perg12_1) {
                    QstRespostas QR = new QstRespostas("36", "144", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb4Perg12_1) {
                    QstRespostas QR = new QstRespostas("36", "145", codQst, "");
                    listaRespostas.add(QR);
                } else if (op15 == R.id.rb5Perg12_1) {
                    QstRespostas QR = new QstRespostas("36", "146", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera o site da UNCISAL em relação a conteúdo disponibilizado no portal da Uncisal?
                int op16 = rgPerg12_2.getCheckedRadioButtonId();
                if (op16 == R.id.rb1Perg12_2) {
                    QstRespostas QR = new QstRespostas("37", "147", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb2Perg12_2) {
                    QstRespostas QR = new QstRespostas("37", "148", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb3Perg12_2) {
                    QstRespostas QR = new QstRespostas("37", "149", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb4Perg12_2) {
                    QstRespostas QR = new QstRespostas("37", "150", codQst, "");
                    listaRespostas.add(QR);
                } else if (op16 == R.id.rb5Perg12_2) {
                    QstRespostas QR = new QstRespostas("37", "151", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a capacitação de pessoal (corpo técnico-administrativo) da instituição?
                int op17 = rgPerg13.getCheckedRadioButtonId();
                if (op17 == R.id.rb1Perg13) {
                    QstRespostas QR = new QstRespostas("38", "152", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb2Perg13) {
                    QstRespostas QR = new QstRespostas("38", "153", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb3Perg13) {
                    QstRespostas QR = new QstRespostas("38", "154", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb4Perg13) {
                    QstRespostas QR = new QstRespostas("38", "155", codQst, "");
                    listaRespostas.add(QR);
                } else if (op17 == R.id.rb5Perg13) {
                    QstRespostas QR = new QstRespostas("38", "156", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você considera a gestão da Universidade participativa e transparente?
                int op18 = rgPerg14.getCheckedRadioButtonId();
                if (op18 == R.id.rb1Perg14) {
                    QstRespostas QR = new QstRespostas("39", "157", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb2Perg14) {
                    QstRespostas QR = new QstRespostas("39", "158", codQst, "");
                    listaRespostas.add(QR);
                } else if (op18 == R.id.rb3Perg14) {
                    QstRespostas QR = new QstRespostas("39", "159", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você tem conhecimento de onde os recursos financeiros da UNCISAL são aplicados?
                int op19 = rgPerg15.getCheckedRadioButtonId();
                if (op19 == R.id.rb1Perg15) {
                    QstRespostas QR = new QstRespostas("40", "160", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb2Perg15) {
                    QstRespostas QR = new QstRespostas("40", "161", codQst, "");
                    listaRespostas.add(QR);
                } else if (op19 == R.id.rb3Perg15) {
                    QstRespostas QR = new QstRespostas("40", "162", codQst, "");
                    listaRespostas.add(QR);
                }


                //As tomadas de decisões da gestão são amplamente divulgadas?
                int op20 = rgPerg16.getCheckedRadioButtonId();
                if (op20 == R.id.rb1Perg16) {
                    QstRespostas QR = new QstRespostas("41", "163", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb2Perg16) {
                    QstRespostas QR = new QstRespostas("41", "164", codQst, "");
                    listaRespostas.add(QR);
                } else if (op20 == R.id.rb3Perg16) {
                    QstRespostas QR = new QstRespostas("41", "165", codQst, "");
                    listaRespostas.add(QR);
                }


                //As necessidades de cada setor são atendidas de forma coerente, dentro dos prazos solicitados?
                int op21 = rgPerg17.getCheckedRadioButtonId();
                if (op21 == R.id.rb1Perg17) {
                    QstRespostas QR = new QstRespostas("42", "166", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb2Perg17) {
                    QstRespostas QR = new QstRespostas("42", "167", codQst, "");
                    listaRespostas.add(QR);
                } else if (op21 == R.id.rb3Perg17) {
                    QstRespostas QR = new QstRespostas("42", "168", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você já participou de alguma capacitação promovida pela UNCISAL?
                int op22 = rgPerg18.getCheckedRadioButtonId();
                if (op22 == R.id.rb1Perg18) {
                    QstRespostas QR = new QstRespostas("43", "169", codQst, "");
                    listaRespostas.add(QR);
                } else if (op22 == R.id.rb2Perg18) {
                    QstRespostas QR = new QstRespostas("43", "170", codQst, "");
                    listaRespostas.add(QR);
                }


                //Se sim, com que frequência?
                int op23 = rgPerg18_1.getCheckedRadioButtonId();
                if (op23 == R.id.rb1Perg18_1) {
                    QstRespostas QR = new QstRespostas("43", "171", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb2Perg18_1) {
                    QstRespostas QR = new QstRespostas("43", "172", codQst, "");
                    listaRespostas.add(QR);
                } else if (op23 == R.id.rb3Perg18_1) {
                    QstRespostas QR = new QstRespostas("43", "173", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você se sente representado pelas instancias colegiadas de gestão?
                int op24 = rgPerg19.getCheckedRadioButtonId();
                if (op24 == R.id.rb1Perg19) {
                    QstRespostas QR = new QstRespostas("44", "174", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb2Perg19) {
                    QstRespostas QR = new QstRespostas("44", "175", codQst, "");
                    listaRespostas.add(QR);
                } else if (op24 == R.id.rb3Perg19) {
                    QstRespostas QR = new QstRespostas("44", "176", codQst, "");
                    listaRespostas.add(QR);
                }

                //Você conhece a política de gestão de pessoas da Uncisal?
                int op25 = rgPerg20.getCheckedRadioButtonId();
                if (op25 == R.id.rb1Perg20) {
                    QstRespostas QR = new QstRespostas("45", "177", codQst, "");
                    listaRespostas.add(QR);
                } else if (op25 == R.id.rb2Perg20) {
                    QstRespostas QR = new QstRespostas("45", "178", codQst, "");
                    listaRespostas.add(QR);
                }


                //Você se sente contemplado pelas políticas de Gestão de Pessoas promovidas pela Uncisal?
                int op26 = rgPerg21.getCheckedRadioButtonId();
                if (op26 == R.id.rb1Perg21) {
                    QstRespostas QR = new QstRespostas("46", "179", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb2Perg21) {
                    QstRespostas QR = new QstRespostas("46", "180", codQst, "");
                    listaRespostas.add(QR);
                } else if (op26 == R.id.rb3Perg21) {
                    QstRespostas QR = new QstRespostas("46", "181", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera as ações de valorização do servidor promovidas pela UNCISAL?
                int op27 = rgPerg22.getCheckedRadioButtonId();
                if (op27 == R.id.rb1Perg22) {
                    QstRespostas QR = new QstRespostas("47", "182", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb2Perg22) {
                    QstRespostas QR = new QstRespostas("47", "183", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb3Perg22) {
                    QstRespostas QR = new QstRespostas("47", "184", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb4Perg22) {
                    QstRespostas QR = new QstRespostas("47", "185", codQst, "");
                    listaRespostas.add(QR);
                } else if (op27 == R.id.rb5Perg22) {
                    QstRespostas QR = new QstRespostas("47", "186", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você considera a comunicação interna da Uncisal?
                int op28 = rgPerg23.getCheckedRadioButtonId();
                if (op28 == R.id.rb1Perg23) {
                    QstRespostas QR = new QstRespostas("48", "187", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb2Perg23) {
                    QstRespostas QR = new QstRespostas("48", "188", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb3Perg23) {
                    QstRespostas QR = new QstRespostas("48", "189", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb4Perg23) {
                    QstRespostas QR = new QstRespostas("48", "190", codQst, "");
                    listaRespostas.add(QR);
                } else if (op28 == R.id.rb5Perg23) {
                    QstRespostas QR = new QstRespostas("48", "191", codQst, "");
                    listaRespostas.add(QR);
                }


                //O quão satisfeito você esta com a estrutura física da instituição que você trabalha?
                int op29 = rgPerg24.getCheckedRadioButtonId();
                if (op29 == R.id.rb1Perg24) {
                    QstRespostas QR = new QstRespostas("49", "192", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb2Perg24) {
                    QstRespostas QR = new QstRespostas("49", "193", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb3Perg24) {
                    QstRespostas QR = new QstRespostas("49", "194", codQst, "");
                    listaRespostas.add(QR);
                } else if (op29 == R.id.rb4Perg24) {
                    QstRespostas QR = new QstRespostas("49", "195", codQst, "");
                    listaRespostas.add(QR);
                }


                //A estrutura física está adaptada às necessidades de pessoas portadoras de deficiência?
                int op30 = rgPerg25.getCheckedRadioButtonId();
                if (op30 == R.id.rb1Perg25) {
                    QstRespostas QR = new QstRespostas("50", "196", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb2Perg25) {
                    QstRespostas QR = new QstRespostas("50", "197", codQst, "");
                    listaRespostas.add(QR);
                } else if (op30 == R.id.rb3Perg25) {
                    QstRespostas QR = new QstRespostas("50", "198", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a biblioteca em relação a acervo:
                int op31 = rgPerg26.getCheckedRadioButtonId();
                if (op31 == R.id.rb1Perg26) {
                    QstRespostas QR = new QstRespostas("51", "199", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb2Perg26) {
                    QstRespostas QR = new QstRespostas("51", "200", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb3Perg26) {
                    QstRespostas QR = new QstRespostas("51", "201", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb4Perg26) {
                    QstRespostas QR = new QstRespostas("51", "202", codQst, "");
                    listaRespostas.add(QR);
                } else if (op31 == R.id.rb5Perg26) {
                    QstRespostas QR = new QstRespostas("51", "203", codQst, "");
                    listaRespostas.add(QR);
                }else if (op31 == R.id.rb6Perg26) {
                    QstRespostas QR = new QstRespostas("51", "568", codQst, "");
                    listaRespostas.add(QR);
                }


                //Como você avalia a biblioteca em relação a espaço físico:
                int op32 = rgPerg27.getCheckedRadioButtonId();
                if (op32 == R.id.rb1Perg27) {
                    QstRespostas QR = new QstRespostas("52", "204", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb2Perg27) {
                    QstRespostas QR = new QstRespostas("52", "205", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb3Perg27) {
                    QstRespostas QR = new QstRespostas("52", "206", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb4Perg27) {
                    QstRespostas QR = new QstRespostas("52", "207", codQst, "");
                    listaRespostas.add(QR);
                } else if (op32 == R.id.rb5Perg27) {
                    QstRespostas QR = new QstRespostas("52", "208", codQst, "");
                    listaRespostas.add(QR);
                }else if (op32 == R.id.rb6Perg27) {
                    QstRespostas QR = new QstRespostas("52", "569", codQst, "");
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
                if (listaRespostas.size() >= 31) { //São 31 respostas para ser preenchido todo questionário
                    //Inserindo no objeto e transformando para JSON

                    HistQst histQst = new HistQst();
                    histQst.setUn_data_hist(UtilTCM.getDate(CONSTANTE_DATA_ATUAL));
                    histQst.setUn_desc_hist("Aplicação do Formulário CPA dos Técnicos da Uncisal");
                    histQst.setUn_cod_hist_qst(codQst);
                    histQst.setUn_cod_hist_pes(String.valueOf(mDados.getId()));

                    String jsonHistQst = histQst.histQstToJSON(histQst); //JSON para ser salvo no Histórico do Questionário Aplicado

                    QstRespostas qstJson = new QstRespostas();
                    String jsonForm = qstJson.answersToJSON(listaRespostas); // Resposnsável por empacotar os dados em um JSON e armazenar na variavel

                    ArrayList<NameValuePair> parametrosPostQstResp = new ArrayList<>();
                    parametrosPostQstResp.add(new BasicNameValuePair("acao", "1"));
                    parametrosPostQstResp.add(new BasicNameValuePair("jsonForm", jsonForm));
                    parametrosPostQstResp.add(new BasicNameValuePair("jsonHistQst", jsonHistQst));

                    Log.i("Script", jsonForm + " - " + jsonHistQst);
                    resposta =  ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getInsereRemove(), parametrosPostQstResp);
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
                Toast.makeText(FormTecActivity.this, "Inserido com sucesso!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            } else {
                Toast.makeText(FormTecActivity.this, result, Toast.LENGTH_SHORT).show();
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
        if (rgPerg18.getCheckedRadioButtonId() == R.id.rb1Perg18) {
            layoutRgPerg18.setVisibility(0);//set visibility to false on create
        } else {
            layoutRgPerg18.setVisibility(4);
            rgPerg18_1.clearCheck();//Clears the selection.
        }

    }

}

