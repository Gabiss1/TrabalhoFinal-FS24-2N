package Model;

import jakarta.persistence.*;

@Entity
@Table(name = "pokemon")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "nome", nullable = false)
    String nome;

    @Column(name = "tipo_primario", nullable = false)
    String tipo_primario;

    @Column(name = "tipo_secundario")
    String tipo_secundario;

    @Column(name = "nivel", nullable = false)
    int nivel;

    @Column(name = "hp_maximo", nullable = false)
    int hp_maximo;

    @Column(name = "hp_atual", nullable = false)
    int hp_atual;

    @Column(name = "fk_id_treinador")
    Integer fk_id_treinador;

    public Pokemon(int id, String nome, String tipo_primario, String tipo_secundario, int nivel, int hp_maximo, int hp_atual, int fk_id_treinador) {
        this.id = id;
        this.nome = nome;
        this.tipo_primario = tipo_primario;
        this.tipo_secundario = tipo_secundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
        this.fk_id_treinador = fk_id_treinador;
    }

    public Pokemon(String nome, String tipo_primario, String tipo_secundario, int nivel, int hp_maximo, int hp_atual, int fk_id_treinador) {
        this.nome = nome;
        this.tipo_primario = tipo_primario;
        this.tipo_secundario = tipo_secundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
        this.fk_id_treinador = fk_id_treinador;
    }

    public Pokemon(){}

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

    public String getTipo_primario() {
        return tipo_primario;
    }

    public void setTipo_primario(String tipo_primario) {
        this.tipo_primario = tipo_primario;
    }

    public String getTipo_secundario() {
        return tipo_secundario;
    }

    public void setTipo_secundario(String tipo_secundario) {
        this.tipo_secundario = tipo_secundario;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getHp_maximo() {
        return hp_maximo;
    }

    public void setHp_maximo(int hp_maximo) {
        this.hp_maximo = hp_maximo;
    }

    public int getHp_atual() {
        return hp_atual;
    }

    public void setHp_atual(int hp_atual) {
        this.hp_atual = hp_atual;
    }

    public Integer getFk_id_treinador() {
        return fk_id_treinador;
    }

    public void setFk_id_treinador(Integer fk_id_treinador) {
        this.fk_id_treinador = fk_id_treinador;
    }

    public boolean getFkNull(){
        return this.fk_id_treinador == null;
    }

    public void setFkToNull(){
        this.fk_id_treinador = null;
    }
}
