package Model.DAO;

import Model.Pokemon;
import Conexao.ConexaoPostgresDB; // Certifique-se que esta classe existe e funciona
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {

    public void inserir(Pokemon pokemon) throws SQLException {
        String sql = "INSERT INTO pokemon (nome, tipo_primario, tipo_secundario, nivel, hp_maximo, hp_atual, fk_id_treinador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pokemon.getNome());
            stmt.setString(2, pokemon.getTipo_primario());
            stmt.setString(3, pokemon.getTipo_secundario());
            stmt.setInt(4, pokemon.getNivel());
            stmt.setInt(5, pokemon.getHp_maximo());
            stmt.setInt(6, pokemon.getHp_atual());
            stmt.setInt(7, pokemon.getFk_id_treinador());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pokemon.setId(rs.getInt(1));
                }
            }
        }
    }

    public void inserirCargaMassiva(List<Pokemon> pokemons) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new
                File("C:\\Users\\GABRIEL\\Documents\\TrabalhoFinal-FS24-2N\\DAOQUEBRADO\\Pokecenter\\src\\Resources\\pokemons.json"), pokemons);

        for (Pokemon poke : pokemons){
            poke.setFkNull();
        }
        String sql = "INSERT INTO pokemon (nome, tipo_primario, tipo_secundario, nivel, hp_maximo, hp_atual, fk_id_treinador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoPostgresDB.conectar();
        conn.setAutoCommit(false);
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        final int Batch_size = 1000;

        for(int i = 0; i<pokemons.size(); i++){
            Pokemon pokemon = pokemons.get(i);
            stmt.setString(1, pokemon.getNome());
            stmt.setString(2, pokemon.getTipo_primario());
            stmt.setString(3, pokemon.getTipo_secundario());
            stmt.setInt(4, pokemon.getNivel());
            stmt.setInt(5, pokemon.getHp_maximo());
            stmt.setInt(6, pokemon.getHp_atual());
            if(pokemon.verificarFkNull()){
                stmt.setNull(7, Types.INTEGER);
            } else {
            stmt.setInt(7, pokemon.getFk_id_treinador());
            }
            stmt.addBatch();
            if((i+1) == pokemons.size() || (i+1) % Batch_size == 0){
                stmt.executeBatch();
                stmt.clearBatch();
            }
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    pokemon.setId(rs.getInt(1));
                }
            }
        }
        conn.commit();
        conn.setAutoCommit(true);
    }

    public Pokemon buscarPorId(int id) {
        String sql = "SELECT * FROM pokemon WHERE id = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pokemon(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("tipo_primario"),
                            rs.getString("tipo_secundario"),
                            rs.getInt("nivel"),
                            rs.getInt("hp_maximo"),
                            rs.getInt("hp_atual"),
                            rs.getInt("fk_id_treinador")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Pokémon por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Pokemon> listarTodos() {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM pokemon ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pokemons.add(new Pokemon(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo_primario"),
                        rs.getString("tipo_secundario"),
                        rs.getInt("nivel"),
                        rs.getInt("hp_maximo"),
                        rs.getInt("hp_atual"),
                        rs.getInt("fk_id_treinador")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os Pokémons: " + e.getMessage());
        }
        return pokemons;
    }

    public Pokemon buscarPorNome(String nomeBusca) {
        Pokemon pokemon;
        String sql = "SELECT * FROM pokemon WHERE nome = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomeBusca );
            try (ResultSet rs = stmt.executeQuery()) {
                    pokemon = new Pokemon(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("tipo_primario"),
                            rs.getString("tipo_secundario"),
                            rs.getInt("nivel"),
                            rs.getInt("hp_maximo"),
                            rs.getInt("hp_atual"),
                            rs.getInt("fk_id_treinador")
                    );
                    return pokemon;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Pokémon por nome: " + e.getMessage());
        }
        return null;
    }

    public void atualizar(Pokemon pokemon) throws SQLException {
        String sql = "UPDATE pokemon SET nome = ?, tipo_primario = ?, tipo_secundario = ?, nivel = ?, hp_maximo = ?, hp_atual = ?, fk_id_treinador = ? WHERE id = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pokemon.getNome());
            stmt.setString(2, pokemon.getTipo_primario());
            stmt.setString(3, pokemon.getTipo_secundario());
            stmt.setInt(4, pokemon.getNivel());
            stmt.setInt(5, pokemon.getHp_maximo());
            stmt.setInt(6, pokemon.getHp_atual());
            stmt.setInt(7, pokemon.getFk_id_treinador());
            stmt.setInt(8, pokemon.getId());
            stmt.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM pokemon WHERE id = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean pokemonJaExiste(String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pokemon WHERE LOWER(nome) = LOWER(?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}