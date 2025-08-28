package Controller;

import Model.Pokemon;
import Services.JsonReader;
import Util.HibernateUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokemonController {

    public void cadastrarPokemon(Pokemon pokemon) throws Exception{
        Transaction transaction = null;

        if (pokemon.getNome() == null || pokemon.getNome().trim().isEmpty()) {
            throw new Exception("O nome do Pokémon é obrigatório.");
        }

        if(!revisaoDeTexto(pokemon.getNome()) && !pokemon.getNome().trim().toLowerCase().equalsIgnoreCase("porygon2")){
            throw new Exception("O nome do pokémon deve conter apenas letras, espaços ou o número 2 (caso seja o Porygon 2)!");
        }

        if (pokemon.getTipo_primario() == null || pokemon.getTipo_primario().trim().isEmpty()) {
            throw new Exception("O tipo primário do Pokémon é obrigatório.");
        }

        if (!revisaoDeTexto(pokemon.getTipo_primario())){
            throw new Exception("O Tipo do Pokémon não deve conter números!");
        }

        if (pokemon.getTipo_secundario() != null){
            if (pokemon.getTipo_primario().toLowerCase().equals(pokemon.getTipo_secundario().toLowerCase()) || !revisaoDeTexto(pokemon.getTipo_secundario())) {
                throw new Exception("O Tipo Secundário não pode ser igual ao Tipo Primário e também não pode conter números.");
            }
        }

        if (pokemon.getNivel() < 0 || pokemon.getNivel() > 100 || String.valueOf(pokemon.getNivel()).trim().isEmpty()) {
            throw new Exception("O nível não é válido.");
        }

        if (pokemon.getHp_maximo() < 0 || String.valueOf(pokemon.getHp_maximo()).trim().isEmpty()) {
            throw new Exception("O HP máximo não é válido.");
        }

        if (pokemon.getHp_atual() > pokemon.getHp_maximo() || String.valueOf(pokemon.getHp_atual()).trim().isEmpty()) {
            throw new Exception("O HP máximo não é válido.");
        }

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.persist(pokemon);

            transaction.commit();
        }  catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao cadastrar Pokemon: "+e.getMessage());
        }
    }

    public void cadastrarEmLote(List<Pokemon> pokemons) throws Exception {
        Transaction transaction = null;
        ObjectMapper mapper = new ObjectMapper();
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
//            mapper.writeValue(new
//                    File("C:\\Users\\GABRIEL\\Documents\\TrabalhoFinal-FS24-2N\\PokeCenter\\src\\main\\resources\\pokemons.json"), pokemons);
            mapper.writeValue(new
                    File("C:\\Users\\GABRIELGARCEZDEOLIVE\\Documents\\Atividades-FS24-2N\\TrabalhoFinal-FS24-2N\\PokeCenter\\src\\main\\resources\\pokemons.json"), pokemons);
            System.out.println(pokemons.get(2).getNome());
            for(Pokemon poke : pokemons){
                System.out.println(poke.getNome());
                poke.setFkToNull();
                session.merge(poke);
            }
            session.flush();
            transaction.commit();
        } catch (IOException e) {
            System.out.println("Houve um erro ao salvar o arquivo.");
            e.printStackTrace();
        }
    }

    public void curarTodosPokes(List<Pokemon> pokemons){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            for(Pokemon poke : pokemons){
                poke.setHp_atual(poke.getHp_maximo());
                session.merge(poke);
            }
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Houve um erro ao salvar o arquivo: "+ e.getMessage());
        }
    }

    public void updatePoke(Pokemon pokemon) throws Exception{
        Transaction transaction = null;

        if (pokemon.getNome() == null || pokemon.getNome().trim().isEmpty()) {
            throw new Exception("O nome do Pokémon é obrigatório.");
        }

        if(!revisaoDeTexto(pokemon.getNome()) && !pokemon.getNome().trim().toLowerCase().equalsIgnoreCase("porygon2")){
            throw new Exception("O nome do pokémon deve conter apenas letras, espaços ou o número 2 (caso seja o Porygon 2)!");
        }

        if (pokemon.getTipo_primario() == null || pokemon.getTipo_primario().trim().isEmpty()) {
            throw new Exception("O tipo primário do Pokémon é obrigatório.");
        }

        if (!revisaoDeTexto(pokemon.getTipo_primario())){
            throw new Exception("O Tipo do Pokémon não deve conter números!");
        }

        if (pokemon.getTipo_secundario() != null){
            if (pokemon.getTipo_primario().toLowerCase().equals(pokemon.getTipo_secundario().toLowerCase()) || !revisaoDeTexto(pokemon.getTipo_secundario())) {
                throw new Exception("O Tipo Secundário não pode ser igual ao Tipo Primário e também não pode conter números.");
            }
        }

        if (pokemon.getNivel() < 0 || pokemon.getNivel() > 100 || String.valueOf(pokemon.getNivel()).trim().isEmpty()) {
            throw new Exception("O nível não é válido.");
        }

        if (pokemon.getHp_maximo() < 0 || String.valueOf(pokemon.getHp_maximo()).trim().isEmpty()) {
            throw new Exception("O HP máximo não é válido.");
        }

        if (pokemon.getHp_atual() > pokemon.getHp_maximo() || String.valueOf(pokemon.getHp_atual()).trim().isEmpty()) {
            throw new Exception("O HP máximo não é válido.");
        }

        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.merge(pokemon);

            transaction.commit();
        } catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao Atualizar Pokemon: "+e.getMessage());
        }
    }

    public List<Pokemon> listarTodosPokes(){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Pokemon> pokemons = session.createQuery("FROM Pokemon", Pokemon.class);
            return pokemons.getResultList();
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

    public Pokemon getPokemonById(int id){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            return session.get(Pokemon.class, id);
        }
    }

    public List<Pokemon> getPokemonByName(String nome){
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Pokemon> pokemon = session.createQuery("FROM Pokemon WHERE nome = :nome", Pokemon.class);
            pokemon.setParameter("nome", nome);
            return pokemon.getResultList();
        }
    }

    public List<Pokemon> getPokemonByTreinador(String nome){
        TreinadorController treinadorController = new TreinadorController();
        int treinador = treinadorController.getTreinadorByName(nome).getId();
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Pokemon> pokemon = session.createQuery("FROM Pokemon WHERE fk_id_treinador = :treinador", Pokemon.class);
            pokemon.setParameter("treinador", treinador);
            return pokemon.getResultList();
        }
    }

    public void removerPokemon(int id){
        Transaction transaction = null;
        try(Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Pokemon pokemon = getPokemonById(id);
            if (pokemon != null){
                session.remove(pokemon);
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
