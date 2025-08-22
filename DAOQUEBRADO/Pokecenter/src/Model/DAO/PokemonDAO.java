package Model.DAO;

import Model.Pokemon;
import Conexao.ConexaoPostgresDB; // Certifique-se que esta classe existe e funciona
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PokemonDAO {

    public void inserir(Pokemon pokemon) throws SQLException {
        String sql = "INSERT INTO pokemon (nome, tipo_primario, tipo_secundario, nivel, hp_maximo, hp_atual, fk_id_treinador) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pokemon.getName());
            stmt.setString(2, pokemon.getTipoPrimario());
            stmt.setString(3, pokemon.getTipoSecundario());
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

    public Pokemon buscarPorId(int id) {
        String sql = "SELECT * FROM pokemon WHERE id_pokemon = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pokemon(
                            rs.getInt("id_pokemon"),
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
                        rs.getInt("id_pokemon"),
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

    public List<Pokemon> buscarPorNome(String nomeBusca) {
        List<Pokemon> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM pokemon WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pokemons.add(new Pokemon(
                            rs.getInt("id_pokemon"),
                            rs.getString("nome"),
                            rs.getString("tipo_primario"),
                            rs.getString("tipo_secundario"),
                            rs.getInt("nivel"),
                            rs.getInt("hp_maximo"),
                            rs.getInt("hp_atual"),
                            rs.getInt("fk_id_treinador")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar Pokémon por nome: " + e.getMessage());
        }
        return pokemons;
    }

    public void atualizar(Pokemon pokemon) throws SQLException {
        String sql = "UPDATE pokemon SET nome = ?, tipo_primario = ?, tipo_secundario = ?, nivel = ?, hp_maximo = ?, hp_atual = ?, fk_id_treinador = ? WHERE id_pokemon = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, pokemon.getName());
            stmt.setString(2, pokemon.getTipoPrimario());
            stmt.setString(3, pokemon.getTipoSecundario());
            stmt.setInt(4, pokemon.getNivel());
            stmt.setInt(5, pokemon.getHp_maximo());
            stmt.setInt(6, pokemon.getHp_atual());
            stmt.setInt(7, pokemon.getFk_id_treinador());
            stmt.setInt(8, pokemon.getId());
            stmt.executeUpdate();
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM pokemon WHERE id_pokemon = ?";
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