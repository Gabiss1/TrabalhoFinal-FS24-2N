package Controller;

import Model.Treinador;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TreinadorController {
    public void cadastrarTreinador(Treinador treinador) throws Exception{
        Transaction transaction = null;

        if(treinador.getNome().trim().isEmpty() || treinador.getNome() == null){
            throw new Exception("O Nome do treinador é obrigatório!");
        }

        if(!revisaoDeTexto(treinador.getNome().trim())){
            throw new Exception("O nome do Treinador deve conter apenas letras ou espaços!");
        }

        if(!revisaoDeTexto(treinador.getCidade().trim())){
            throw new Exception("O nome da cidade do Treinador deve conter apenas letras ou espaços!");
        }

        if(treinador.getCidade().trim().isEmpty() || treinador.getCidade() == null){
            throw new Exception("A Cidade do treinador é obrigatório!");
        }

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(treinador);

            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao cadastrar Treinador: "+e.getMessage());
        }
    }

    public void updateTreinador(Treinador treinador) throws Exception{
        Transaction transaction = null;
        if(treinador.getNome().trim().isEmpty() || treinador.getNome() == null){
            throw new Exception("O Nome do treinador é obrigatório!");
        }

        if(!revisaoDeTexto(treinador.getNome().trim())){
            throw new Exception("O nome do Treinador deve conter apenas letras ou espaços!");
        }

        if(!revisaoDeTexto(treinador.getCidade().trim())){
            throw new Exception("O nome da Cidade do Treinador deve conter apenas letras ou espaços!");
        }

        if(treinador.getCidade().trim().isEmpty() || treinador.getCidade() == null){
            throw new Exception("A Cidade do treinador é obrigatória!");
        }

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(treinador);

            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao Atualizar Treinador: "+e.getMessage());
        }
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

    public List<Treinador> listarTreinadores(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Treinador> treinadores = session.createQuery("FROM Treinador", Treinador.class);
            return treinadores.getResultList();
        }
    }

    public Treinador getTreinadorByName(String nome){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Treinador> query = session.createQuery("FROM Treinador WHERE nome = :nome", Treinador.class);
            query.setParameter("nome", nome);
            return query.getSingleResult();
        }
    }

    public Treinador getTreinadorById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Treinador.class, id);
        }
    }

    public void removerTreinador(int id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Treinador treinador = getTreinadorById(id);
            if (treinador != null){
                session.remove(treinador);
            }
            transaction.commit();
        } catch (Exception e){
            if (transaction!=null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao remover Poke: "+ e.getMessage());
        }
    }
}
