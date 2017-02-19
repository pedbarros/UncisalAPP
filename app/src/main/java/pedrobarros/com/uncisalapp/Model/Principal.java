package pedrobarros.com.uncisalapp.Model;

import android.graphics.Bitmap;

import pedrobarros.com.uncisalapp.R;

/**
 * Created by Pedro Barros.
 * Classe para customizar um listView com Titulo e Descricao
 * Utilizada:
 * Criação: 10/04/2016
 */
public class Principal {
    private String titulo;
    private String descricao;

    public Principal(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getImagemPrincipal(int position){
        switch(position){
            case 0:
                return(R.drawable.alunos);
            case 1:
                return(R.drawable.questionario);
            case 2:
                return(R.drawable.verquest);
            case 3:
                return(R.drawable.relatorio);
            case 4:
                return(R.drawable.download);
            case 5:
                return(R.drawable.sair);
            default:
                return(R.drawable.sair);
        }
    }

}
