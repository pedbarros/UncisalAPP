package pedrobarros.com.uncisalapp.Model;

/**
 * Created by Pedro Barros on 18/05/2016.
 *
 */
public class Lotacao {
    private String id;
    private String nome_lotacao;


    public Lotacao() {
        super();
    }

    public Lotacao(String id, String nome_lotacao) {
        super();
        this.id = id;
        this.nome_lotacao = nome_lotacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLotacao() {
        return nome_lotacao;
    }

    public void setLotacao(String nome_lotacao) {
        this.nome_lotacao = nome_lotacao;
    }

    public String toString() {
        return (this.getLotacao());
    }

}
