package pedrobarros.com.uncisalapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pedrobarros.com.uncisalapp.Model.Principal;
import pedrobarros.com.uncisalapp.R;

/**
 * Created by Pedro Barros.
 * Customização dos listviews utilizados no APP
 * Utilizada:
 * Criação: 10/05/2016.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Principal> lista;

    public ListViewAdapter(Context context, ArrayList<Principal> lista){
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;
        ViewHolder holder;

        if(convertView == null){
            layout = LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false);
            holder = new ViewHolder(layout);
            layout.setTag(holder);
        }
        else{
            layout = convertView;
            holder = (ViewHolder) layout.getTag();
        }

        Principal principal = (Principal) getItem(position);
        holder.titulo.setText(principal.getTitulo());
        holder.descricao.setText(principal.getDescricao());
        holder.iv.setImageResource(principal.getImagemPrincipal(position));

        return layout;
    }

    public class ViewHolder {

        final TextView descricao;
        final TextView titulo;
        final ImageView iv;

        public ViewHolder(View layout) {
            titulo = (TextView) layout.findViewById(R.id.txtPrincipal);
            descricao = (TextView) layout.findViewById(R.id.txtSubPrincipal);
            iv = (ImageView) layout.findViewById(R.id.imgCustom);
        }
    }
}
