package Controller;

import Model.DAO.TreinadorDAO;
import Model.Pokemon;
import Model.Treinador;

import java.sql.SQLException;
import java.util.List;

public class TreinadorController {
    private TreinadorDAO treinadorDAO;

    public TreinadorController(TreinadorDAO treinadorDAO) {
        this.treinadorDAO = treinadorDAO;
    }

    public void cadastrarTreinador(String nome, String cidade) throws Exception {

        if (nome == null || nome.trim().isEmpty()){
            throw new Exception("O nome do treinador não pode estar vazio.");
        }

        if (cidade == null || cidade.trim().isEmpty()){
            throw new Exception("A cidade do treinador não pode estar vazia.");
        }

        if (treinadorDAO.treinadorJaExiste(nome)){
            throw new Exception("Treinador com este nome já registrado.");
        }

        Treinador treinador = new  Treinador(nome, cidade);
        treinadorDAO.inserir(treinador);
    }

    public void atualizarTreinador (int id,  String nome, String cidade) throws Exception {

        if (nome == null || nome.trim().isEmpty()){
            throw new Exception("O nome do treinador não pode estar vazio.");
        }

        if (cidade == null || cidade.trim().isEmpty()){
            throw new Exception("A cidade do treinador não pode estar vazia.");
        }

        if (!treinadorDAO.treinadorJaExiste(nome)){
            throw new Exception("Não há um treinador registrado com este nome.");
        }

        Treinador treinador = new Treinador(id,nome,cidade);
        treinadorDAO.atualizar(treinador);
    }

    public List<Treinador> listarTreinadores(){
        return treinadorDAO.listarTodos();
    }

    public void removeTreinador(int id, String nome) throws Exception {

        if (treinadorDAO.buscarPorNome(nome) == null) {
            throw new Exception("Treinador com este nome não existente.");
        }

        try {
            if (treinadorDAO.treinadorJaExiste(nome)) {
                treinadorDAO.remover(id);
            };
        } catch (SQLException e) {
            throw new Exception("Erro ao remover Treinador: " + e.getMessage());
        }
    }

    public Treinador buscarTreinadorPorNome(String nome) {
        return treinadorDAO.buscarPorNome(nome).getFirst();
    }

    public Treinador buscarTreinadorPorId(int id) {
        return treinadorDAO.buscarPorId(id);
    }
}










