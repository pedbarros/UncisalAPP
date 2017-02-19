package pedrobarros.com.uncisalapp.Model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pedro Barros on 09/04/2016.
 */
public class HistQst {

    private String un_desc_hist;
    private String un_data_hist;
    private String un_cod_hist_qst;
    private String un_cod_hist_pes;

    public HistQst() {

    }

    public HistQst(String un_desc_hist, String un_data_hist, String un_cod_hist_qst, String un_cod_hist_pes) {
        this.un_desc_hist = un_desc_hist;
        this.un_data_hist = un_data_hist;
        this.un_cod_hist_qst = un_cod_hist_qst;
        this.un_cod_hist_pes = un_cod_hist_pes;
    }

    public String getUn_desc_hist() {
        return un_desc_hist;
    }

    public String getUn_data_hist() {
        return un_data_hist;
    }

    public String getUn_cod_hist_qst() {
        return un_cod_hist_qst;
    }

    public String getUn_cod_hist_pes() {
        return un_cod_hist_pes;
    }

    public void setUn_desc_hist(String un_desc_hist) {
        this.un_desc_hist = un_desc_hist;
    }

    public void setUn_data_hist(String un_data_hist) {
        this.un_data_hist = un_data_hist;
    }

    public void setUn_cod_hist_qst(String un_cod_hist_qst) {
        this.un_cod_hist_qst = un_cod_hist_qst;
    }

    public void setUn_cod_hist_pes(String un_cod_hist_pes) {
        this.un_cod_hist_pes = un_cod_hist_pes;
    }

   public String histQstToJSON(HistQst histQst) {
        JSONObject jo = new JSONObject();
        try {
            jo.put("desc_hist", histQst.getUn_desc_hist());
            jo.put("data_hist", histQst.getUn_data_hist());
            jo.put("cod_hist_qst", histQst.getUn_cod_hist_qst());
            jo.put("cod_hist_pes", histQst.getUn_cod_hist_pes());
        }catch(JSONException e){ e.printStackTrace(); }

        return (jo.toString());
    }

     /*@Override
    public String toString() {
        return "{" +
                ""+'"'+"desc_hist"+'"'+":"+'"' + un_desc_hist +'"'+
                ","+'"'+"data_hist"+'"'+":"+'"' + un_data_hist+'"'+
                ","+'"'+"cod_hist_qst"+'"'+":"+'"' + un_cod_hist_qst+'"'+
                ","+'"'+"cod_hist_pes"+'"'+":"+'"' + un_cod_hist_pes +'"'+
                '}';
    }*/


}
