package Model;

import jakarta.persistence.*;

@Entity
@Table(name = "treinador")
public class Treinador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "nome", nullable = false)
    String nome;

    @Column(name = "cidade", nullable = false)
    String cidade;

    public Treinador(int id, String nome, String cidade) {
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
    }

    public Treinador(String nome, String cidade) {
        this.nome = nome;
        this.cidade = cidade;
    }

    public Treinador() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
