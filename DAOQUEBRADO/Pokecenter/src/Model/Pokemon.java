package Model;

public class Pokemon {
    private int id;
    private String nome;
    private String tipo_primario;
    private String tipo_secundario;
    private int nivel;
    private int hp_maximo;
    private int hp_atual;
    private Integer fk_id_treinador;

    public Pokemon() {
    }

    //Constructor Para Cadastro POKEMON
    public Pokemon(String nome, String tipo_primario, String tipo_secundario, int nivel, int hp_maximo, int hp_atual) {
        this.nome = nome;
        this.tipo_primario = tipo_primario;
        this.tipo_secundario = tipo_secundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
    }

    //Constructor Para Edicao De POKEMON
    public Pokemon(int id, String nome, String tipo_primario, String tipo_secundario, int nivel, int hp_maximo, int hp_atual) {
        this.id = id;
        this.nome = nome;
        this.tipo_primario = tipo_primario;
        this.tipo_secundario = tipo_secundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
    }

    //Constructor Para Cadastrar POKEMON com TREINADOR
    public Pokemon(String nome, String tipo_primario, String tipo_secundario, int nivel, int hp_maximo, int hp_atual, int fk_id_treinador) {
        this.nome = nome;
        this.tipo_primario = tipo_primario;
        this.tipo_secundario = tipo_secundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
        this.fk_id_treinador = fk_id_treinador;
    }

    //Constructor Para Edicao POKEMON com TREINADOR
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

    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getTipo_primario() {
        return tipo_primario;
    }
    public String getTipo_secundario() {
        return tipo_secundario;
    }
    public int getNivel() {
        return nivel;
    }
    public int getHp_maximo() {
        return hp_maximo;
    }
    public int getHp_atual() {
        return hp_atual;
    }
    public int getFk_id_treinador() {
        return fk_id_treinador;
    }
    public boolean verificarFkNull(){
        return fk_id_treinador == null;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTipo_primario(String tipo_primario) {
        this.tipo_primario = tipo_primario;
    }
    public void setTipo_secundario(String tipo_secundario) {
        this.tipo_secundario = tipo_secundario;
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    public void setHp_maximo(int hp_maximo) {
        this.hp_maximo = hp_maximo;
    }
    public void setHp_atual(int hp_atual) {
        this.hp_atual = hp_atual;
    }
    public void setFk_id_treinador(int fk_id_treinador) {
        this.fk_id_treinador = fk_id_treinador;
    }
    public void setFkNull(){
        this.fk_id_treinador = null;
    }
}
