package Model;

public class Treinador {
    private int id;
    private String nome;
    private String cidade;

    public Treinador() {
    }

    public Treinador(int id, String nome, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
    }

    public Treinador(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCidade() {
        return cidade;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
