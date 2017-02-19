package pedrobarros.com.uncisalapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Conexao.HttpUrlConn;
import pedrobarros.com.uncisalapp.Extras.MySingleton;
import pedrobarros.com.uncisalapp.Extras.UtilTCM;
import pedrobarros.com.uncisalapp.Permissao.PermissionUtils;

/**
 * Created by Pedro Barros
 * Classe de Login do app
 * Utilizada:
 * Criação: 30/03/2016.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.input_matricula)
    EditText _matriculaText;
    @Bind(R.id.input_cpf)
    EditText _cpfText;


    MySingleton mDados;

    String resposta; //Retorno do webService (JSON)


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        //Restaura as preferencias gravadas
        SharedPreferences settings = getSharedPreferences("gravarLogin", 0);
        _matriculaText.setText(settings.getString("PrefMatricula", ""));
        _cpfText.setText(settings.getString("PrefCPF", ""));

        // ????  getInstance()
        mDados = MySingleton.getInstance();
    }

    @OnClick(R.id.btn_login)
    public void botaoLogin() {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {//Só obriga permissão se for acima do LOLLIPOP
            // Solicita as permissões
            String[] permissoes = new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.CHANGE_NETWORK_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.WAKE_LOCK,
            };
            PermissionUtils.validate(LoginActivity.this, 0, permissoes);
        }

        //Classe que verifica se existe conexão
        if (UtilTCM.verificaConexao(LoginActivity.this) == true) {
            login();

        } else {
            Toast.makeText(LoginActivity.this, "É necessário se conectar a internet.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Metodo para chamar validação, caso esteja ok ele começa o processo de login
     */
    public void login() {
        if (!validate()) {
            return;
        }
        validarUsuario validarUsuario = new validarUsuario();
        validarUsuario.execute();
    }

    /**
     * Metodo para validar os campos
     */
    public boolean validate() {
        boolean valid = true;


        if (_cpfText.getText().toString().isEmpty()) {
            _cpfText.setError("Coloque um cpf válido!");

            UtilTCM.getAnimation(findViewById(R.id.input_cpf)); //Animação quando entra nessa condição

            valid = false;
        } else {
            _cpfText.setError(null);
        }

        if (_matriculaText.getText().toString().isEmpty()) {
            _matriculaText.setError("Coloque uma matricula válida!");

            UtilTCM.getAnimation(findViewById(R.id.input_matricula)); //Animação quando entra nessa condição

            valid = false;
        } else {
            _matriculaText.setError(null);
        }

        return valid;
    }


    /**
     * Classe responsável para fazer o login em backgroud com AsyncTask
     */
    private class validarUsuario extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);

            dialog.setMessage("Autenticando...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
            parametrosPost.add(new BasicNameValuePair("cpf", _cpfText.getText().toString()));
            parametrosPost.add(new BasicNameValuePair("matricula", _matriculaText.getText().toString()));
            try {

                resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getUrlLogin(), parametrosPost);
                JSONArray jsonArray = new JSONArray(resposta); //Transformando o retorno de String para um Array JSON

                JSONObject jsonObject = jsonArray.getJSONObject(0);

                //Dados armazenados para serem usados em qualquer lugar do app
                mDados.setId(jsonObject.getString("COD_PESSOA"));
                mDados.setMatricula(jsonObject.getString("MATRICULA"));
                mDados.setCpf(jsonObject.getString("CPF"));
                mDados.setNome(jsonObject.getString("NOME_PESSOA").trim());
                mDados.setLotacao(jsonObject.getString("NOME_LOT"));
                mDados.setCodCargo(jsonObject.getString("COD_CAR"));

            } catch (Exception e) {
                e.printStackTrace();
            }

            return resposta;


        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("Script", result);
            // é retornado um JSON, porém se não for encontrado nada ele vem com um Array vazio
            if (!result.equals("0")) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
              //Toast.makeText(LoginActivity.this, mDados.getId() + " " + mDados.getCpf() + " " + mDados.getMatricula() + " " + mDados.getNome() + " " + mDados.getLotacao() + " Logado com sucesso!", Toast.LENGTH_SHORT).show();
            } else
                //Toast.makeText(LoginActivity.this, result + ", não foi possível logar!", Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this, "Usuário ou senha incorreta!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    /**
     * Chamado quando a Activity é encerrada.
     */
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences("gravarLogin", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("PrefMatricula", _matriculaText.getText().toString());
        editor.putString("PrefCPF", _cpfText.getText().toString());

        //Confirma a gravação dos dados
        editor.commit();
    }

}