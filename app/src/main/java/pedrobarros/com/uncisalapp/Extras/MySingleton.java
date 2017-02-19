package pedrobarros.com.uncisalapp.Extras;

/**
 * Created by Pedro Barros on 30/04/2016.
 * Singleton do projeto
 * Valores que serão estaticos e poderão ser acessados em todo APP
 */
public class MySingleton {
    private static MySingleton instance = new MySingleton();
    // minhas variáveis
    private String id;
    private String cpf;
    private String matricula;
    private String nome;
    private String lotacao;
    private String codCargo;

    private MySingleton() {
        this.setId(null);
        this.setNome(null);
        this.setMatricula(null);
        this.setCpf(null);
        this.setLotacao(null);
        this.setCodCargo(null);

    }

    public static MySingleton getInstance()  {
        return instance;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getLotacao() {
        return lotacao;
    }

    public void setLotacao(String lotacao) {
        this.lotacao = lotacao;
    }

    public String getCodCargo() {
        return codCargo;
    }

    public void setCodCargo(String codCargo) {
        this.codCargo = codCargo;
    }

}

