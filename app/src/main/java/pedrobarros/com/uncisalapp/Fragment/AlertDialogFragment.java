package pedrobarros.com.uncisalapp.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pedrobarros.com.uncisalapp.Conexao.ConexaoHttpClient;
import pedrobarros.com.uncisalapp.Extras.MySingleton;
import pedrobarros.com.uncisalapp.Extras.UtilTCM;
import pedrobarros.com.uncisalapp.FormAlunosActivity;
import pedrobarros.com.uncisalapp.Model.Cargo;
import pedrobarros.com.uncisalapp.Model.Lotacao;
import pedrobarros.com.uncisalapp.R;

/**
 * Created by Pedro Barros on 09/05/2016.
 * Classe com input de dialog customizado para selecionar qual lotacao e cargo a pessoa está
 */
public class AlertDialogFragment extends DialogFragment {
    //COMPONENTES VIEW
    private Spinner spnLotacao;
    private Spinner spnCargo;
    private Button btnSalvar;

    //ARRAYLISTS
    private ArrayList<Cargo> cargos = new ArrayList();
    private ArrayList<Lotacao> lotacoes = new ArrayList();

    //SINGLETON
    MySingleton mDados;

    //OBJETO SELECIONADO LISTA
    private Lotacao lotacao;
    private Cargo cargo;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // ????  getInstance()
        mDados = MySingleton.getInstance();

        ArrayList<NameValuePair> parametrosCargo = new ArrayList<>();
        //parametrosCargo.add(new BasicNameValuePair("acao", "carregaCargo"));
        parametrosCargo.add(new BasicNameValuePair("acao", "1"));
        try {
            String resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getCarregaDados(), parametrosCargo);
            Log.i("Script", resposta);
            JSONArray jsonArray = new JSONArray(resposta); //Transformando o retorno de String para um Array JSON
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                Cargo cargo = new Cargo();
                cargo.setId(jsonObject.getString("COD_CAR"));
                cargo.setCargo(jsonObject.getString("NOME_CARGO"));
                cargos.add(cargo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // Cria o objeto para configurar o AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        builder.setTitle("Salve sua lotação!");


        spnCargo = (Spinner) dialogView.findViewById(R.id.spnCargo);
        spnLotacao = (Spinner) dialogView.findViewById(R.id.spnLot);
        btnSalvar = (Button) dialogView.findViewById(R.id.btnSalvar);

        ArrayAdapter<Cargo> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, cargos);

        spnCargo.setAdapter(adapter);

        //Método do Spinner para capturar o item selecionado
        spnCargo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                cargo = (Cargo) parent.getSelectedItem();
                // String inf = "Id: " + c.getId() + ", Cargo: " + c.getCargo();

                ArrayList<NameValuePair> parametrosLot = new ArrayList<>();
                parametrosLot.add(new BasicNameValuePair("acao", "2"));
                parametrosLot.add(new BasicNameValuePair("cod", cargo.getId()));
                try {
                    String resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getCarregaDados(), parametrosLot);
                    JSONArray jsonArray = new JSONArray(resposta); //Transformando o retorno de String para um Array JSON
                    lotacoes.clear();//Limpa os valores antigos do array para listar os novos
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Lotacao lotacao = new Lotacao();
                        lotacao.setId(jsonObject.getString("COD_LOT"));
                        lotacao.setLotacao(jsonObject.getString("NOME_LOT"));
                        lotacoes.add(lotacao);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //pega nome pela posição
                //  cargo = parent.getItemAtPosition(posicao).toString();

                final ArrayAdapter<Lotacao> adapter2
                        = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, lotacoes);
                spnLotacao.setAdapter(adapter2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //Método do Spinner para capturar o item selecionado
        spnLotacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                lotacao = (Lotacao) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> parametrosPost = new ArrayList<>();
                parametrosPost.add(new BasicNameValuePair("acao", "1"));
                parametrosPost.add(new BasicNameValuePair("cpf", mDados.getCpf()));
                parametrosPost.add(new BasicNameValuePair("matricula", mDados.getMatricula()));
                parametrosPost.add(new BasicNameValuePair("lot", lotacao.getId()));
                try {
                    String resposta = ConexaoHttpClient.executaHttpPost(ConexaoHttpClient.getAtualizaDados(), parametrosPost);
                    Log.i("Script", resposta);
                    if (resposta.equals("1")) {
                        mDados.setLotacao(lotacao.getLotacao());//Atualizando nome da lotação
                        mDados.setCodCargo(cargo.getId());//Atualizando o cod do cargo referente a lotação
                        Toast.makeText(getActivity(), "Sua lotação foi alterada com sucesso!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    } else
                        Toast.makeText(getActivity(), "Não foi possível alterar!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Cria o objeto AlertDialog
        return builder.create();

    }


}
