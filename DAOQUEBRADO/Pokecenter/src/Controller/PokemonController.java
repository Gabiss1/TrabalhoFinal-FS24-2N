package Controller;

import Model.DAO.PokemonDAO;
import Model.Pokemon;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PokemonController {
    private PokemonDAO pokemonDAO;

    public PokemonController() {
        this.pokemonDAO = new PokemonDAO();
    }

    public void cadastrarPokemon(String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo, int hpAtual) throws Exception {

        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O campo 'Nome' não pode estar em branco.");
        }

        if (!nome.toLowerCase().trim().equals("porygon2")) {
            if (nome.contains("1") || nome.contains("2") || nome.contains("3") || nome.contains("4") || nome.contains("5") || nome.contains("6") || nome.contains("7") || nome.contains("8") || nome.contains("9") || nome.contains("0")) {
                throw new Exception("O nome não pode conter numeros.");
            }
        }

        if (tipoPrimario.contains("1") || tipoPrimario.contains("2") || tipoPrimario.contains("3") || tipoPrimario.contains("4") || tipoPrimario.contains("5") || tipoPrimario.contains("6") || tipoPrimario.contains("7") || tipoPrimario.contains("8") || tipoPrimario.contains("9") || tipoPrimario.contains("0")) {
            throw new Exception("O tipo primario não pode conter numeros.");
        }

        if (pokemonDAO.pokemonJaExiste(nome)) {
            throw new Exception("Um Pokemon com este nome já esta registrado.");
        }

        if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
            throw new Exception("O campo 'Tipo Primario' não pode estar em branco.");
        }

        if (tipoSecundario != null) {
            if (tipoSecundario.equals(tipoPrimario)) {
                throw new Exception("Os tipos do Pokemon não podem ser repetidos.");
            }
            if (tipoSecundario.contains("1") || tipoSecundario.contains("2") || tipoSecundario.contains("3") || tipoSecundario.contains("4") || tipoSecundario.contains("5") || tipoSecundario.contains("6") || tipoSecundario.contains("7") || tipoSecundario.contains("8") || tipoSecundario.contains("9") || tipoSecundario.contains("0")) {
                throw new Exception("O tipo secundario não pode conter numeros.");
            }
        }

        if (nivel <= 0 || nivel > 100) {
            throw new Exception("O nivel do Pokemon não pode ser menor que 0 ou maior que 100.");
        }

        if (hpMaximo <= 0) {
            throw new Exception("O hp maximo deve ser positivo.");
        }

        if(hpAtual > hpMaximo || hpAtual < 0) {
            throw new Exception("O HP do Pokemon não pode ser maior que o HP maximo ou menor que 0.");
        }

        Pokemon pokemon = new Pokemon(nome, tipoPrimario, tipoSecundario, nivel, hpMaximo, hpAtual);
        pokemonDAO.inserir(pokemon);
    }

    public void atualizarPokemon(int id, String nome, String tipoPrimario, String tipoSecundario, int nivel, int hpMaximo, int hpAtual) throws Exception {

        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("O campo 'Nome' não pode estar em branco.");
        }

        if (tipoPrimario == null || tipoPrimario.trim().isEmpty()) {
            throw new Exception("O campo 'Tipo Primario' não pode estar em branco.");
        }

        if (tipoSecundario != null) {
            if (tipoSecundario.equals(tipoPrimario)) {
                throw new Exception("Os tipos do Pokemon não podem ser repetidos.");
            }
        }

        if (nivel <= 0 || nivel > 100) {
            throw new Exception("O nivel do Pokemon não pode ser menor que 0 ou maior que 100.");
        }

        if (hpMaximo <= 0) {
            throw new Exception("O hp maximo deve ser positivo.");
        }

        if (pokemonDAO.pokemonJaExiste(nome) == false) {
            throw new Exception("Não há um Pokemon com esse nome registrado.");
        }

        Pokemon pokemon = new Pokemon(id, nome, tipoPrimario, tipoSecundario, nivel, hpMaximo, hpAtual);
        pokemonDAO.atualizar(pokemon);
    }

    public List<Pokemon> listarTodosPokemons() {
        return pokemonDAO.listarTodos();
    }

    public boolean revisaoDeTexto(String texto){
        List<Character> letrasTexto = new ArrayList<>();

        for(int i = 0; i<texto.length(); i++){
            letrasTexto.add(texto.charAt(i));
        }

        for (Character letra: letrasTexto){
            if (!Character.isLetter(letra)){
                return false;
            }
        }
        return true;
    }

    public Pokemon buscarPokemonPorId(int id) {
        return pokemonDAO.buscarPorId(id);
    }

    public void removerPokemon(int id) throws Exception {

        if (buscarPokemonPorId(id) == null) {
            throw new Exception("Pokemon com este ID não existente.");
        }

        try {
            pokemonDAO.remover(id);
        } catch (SQLException e) {
            throw new Exception("Erro ao remover Pokémon: " + e.getMessage());
        }
    }

    public List<Pokemon> buscarPokemonsPorNome(String nome) {
        return pokemonDAO.buscarPorNome(nome);
    }

    public Pokemon pokemonGetNome(String nome) {
        return buscarPokemonsPorNome(nome).get(0);
    }

}