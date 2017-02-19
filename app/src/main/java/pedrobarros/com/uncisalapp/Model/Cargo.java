package pedrobarros.com.uncisalapp.Model;

/**
 * Created by Pedro Barros on 18/05/2016.
 */
public class Cargo {

    private String id;
    private String nome_cargo;


    public Cargo() {
        super();
    }

    public Cargo(String id, String nome_cargo) {
        super();
        this.id = id;
        this.nome_cargo = nome_cargo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCargo() {
        return nome_cargo;
    }

    public void setCargo(String nome_cargo) {
        this.nome_cargo = nome_cargo;
    }

    public String toString() {
        return (this.getCargo());
    }

}
