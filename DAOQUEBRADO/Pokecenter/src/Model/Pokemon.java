package Model;

public class Pokemon {
    private int id;
    private String name;
    private String tipoPrimario;
    private String tipoSecundario;
    private int nivel;
    private int hp_maximo;
    private int hp_atual;
    private int fk_id_treinador;

    public Pokemon() {
    }

    //Constructor Para Cadastro POKEMON
    public Pokemon(String name, String tipoPrimario, String tipoSecundario, int nivel, int hp_maximo, int hp_atual) {
        this.name = name;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
    }

    //Constructor Para Edicao De POKEMON
    public Pokemon(int id, String name, String tipoPrimario, String tipoSecundario, int nivel, int hp_maximo, int hp_atual) {
        this.id = id;
        this.name = name;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
    }

    //Constructor Para Cadastrar POKEMON com TREINADOR
    public Pokemon(String name, String tipoPrimario, String tipoSecundario, int nivel, int hp_maximo, int hp_atual, int fk_id_treinador) {
        this.name = name;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
        this.fk_id_treinador = fk_id_treinador;
    }

    //Constructor Para Edicao POKEMON com TREINADOR
    public Pokemon(int id, String name, String tipoPrimario, String tipoSecundario, int nivel, int hp_maximo, int hp_atual, int fk_id_treinador) {
        this.id = id;
        this.name = name;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.nivel = nivel;
        this.hp_maximo = hp_maximo;
        this.hp_atual = hp_atual;
        this.fk_id_treinador = fk_id_treinador;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTipoPrimario() {
        return tipoPrimario;
    }
    public String getTipoSecundario() {
        return tipoSecundario;
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

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTipoPrimario(String tipoPrimario) {
        this.tipoPrimario = tipoPrimario;
    }
    public void setTipoSecundario(String tipoSecundario) {
        this.tipoSecundario = tipoSecundario;
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
}
