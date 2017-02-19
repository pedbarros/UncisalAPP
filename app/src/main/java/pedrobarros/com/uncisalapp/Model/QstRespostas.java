package pedrobarros.com.uncisalapp.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Pedro Barros.
 * Responsável pelo POJO das respostas do questionários e transformar em JSON
 * Utilizada:
 * Criação: 31/03/2016.
 */
public class QstRespostas {

    private int id;
    private String qstPergunta;
    private String qstResposta;
    private String qstQuest;
    private String rspTxt;
    //private String qstHist;

    public QstRespostas() {
        super();
    }

    public QstRespostas(String qstPergunta, String qstResposta, String qstQuest,
                        String rspTxt) {
        super();
        this.qstPergunta = qstPergunta;
        this.qstResposta = qstResposta;
        this.qstQuest = qstQuest;
        this.rspTxt = rspTxt;
        //this.qstHist = qstHist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQstPergunta() {
        return qstPergunta;
    }

    public void setQstPergunta(String qstPergunta) {
        this.qstPergunta = qstPergunta;
    }

    public String getQstResposta() {
        return qstResposta;
    }

    public void setQstResposta(String qstResposta) {
        this.qstResposta = qstResposta;
    }

    public String getQstQuest() {
        return qstQuest;
    }

    public void setQstQuest(String qstQuest) {
        this.qstQuest = qstQuest;
    }

    public String getRspTxt() {
        return rspTxt;
    }

    public void setRspTxt(String rspTxt) {
        this.rspTxt = rspTxt;
    }

//    public String getQstHist() {
//        return qstHist;
//    }
//
//    public void setQstHist(String qstHist) {
//        this.qstHist = qstHist;
//    }

    // Metodo responsável por criar um JSON
    public String answersToJSON(List<QstRespostas> listQst) {
        JSONArray ja = new JSONArray();

        try {
            for(int i = 0; i < listQst.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("pergunta", listQst.get(i).getQstPergunta());
                object.put("resposta", listQst.get(i).getQstResposta());
                object.put("qst", listQst.get(i).getQstQuest());
                object.put("resp_txt", listQst.get(i).getRspTxt());
                ja.put(object);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ja.toString();
    }

    /*@Override
    public String toString() {
        return "{" +
                ""+'"'+"pergunta"+'"'+":"+'"' + qstPergunta +'"'+
                ","+'"'+"resposta"+'"'+":"+'"' + qstResposta+'"'+
                ","+'"'+"qst"+'"'+":"+'"' + qstQuest+'"'+
                ","+'"'+"resp_txt"+'"'+":"+'"' + rspTxt +'"'+
                '}';
    }*/
}
