package Model.DAO;

import Conexao.ConexaoPostgresDB;
import Model.Treinador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinadorDAO {

    public void inserir(Treinador treinador) throws SQLException {
        String sql = "INSERT INTO pokemons (nome, cidade) VALUES (?, ?)";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, treinador.getNome());
            stmt.setString(2, treinador.getCidade());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    treinador.setId(rs.getInt(1));
                    treinador.setCidade(rs.getString(2));
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir treinador: " + ex.getMessage());
        }
    }

    public Treinador buscarPorId(int id) {
        String sql = "SELECT * FROM treinador WHERE id_treinador = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Treinador(
                            rs.getInt("id_treinador"),
                            rs.getString("nome"),
                            rs.getString("cidade")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar treinador por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Treinador> listarTodos() {
        List<Treinador> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM pokemons ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pokemons.add(new Treinador(
                        rs.getInt("id_treinador"),
                        rs.getString("nome"),
                        rs.getString("cidade")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os treinadores: " + e.getMessage());
        }
        return pokemons;
    }

    public List<Treinador> buscarPorNome(String nomeBusca) {
        List<Treinador> pokemons = new ArrayList<>();
        String sql = "SELECT * FROM treinador WHERE LOWER(nome) LIKE LOWER(?) ORDER BY nome";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + nomeBusca + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pokemons.add(new Treinador(
                            rs.getInt("id_treinador"),
                            rs.getString("nome"),
                            rs.getString("cidade")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar treinador por nome: " + e.getMessage());
        }
        return pokemons;
    }


    public void atualizar(Treinador treinador) throws SQLException {
        String sql = "UPDATE treinador SET nome = ?, cidade = ? WHERE id_treinador = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, treinador.getNome());
            stmt.setString(2, treinador.getCidade());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar treinador: " + ex.getMessage());
        }
    }

    public void remover(int id) throws SQLException {
        String sql = "DELETE FROM treinador WHERE id_treinador = ?";
        try (Connection conn = ConexaoPostgresDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public boolean treinadorJaExiste(String nome) throws SQLException {
        String sql = "SELECT COUNT(*) FROM treinador WHERE LOWER(nome) = LOWER(?)";
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
