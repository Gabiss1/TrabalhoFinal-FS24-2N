package Controller;

import Model.Pokemon;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.nio.file.SecureDirectoryStream;
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
            throw new RuntimeException("Erro ao cadastrar Treinador: "+e.getMessage());
        }
    }

    public void updatePoke(int id){
        Pokemon pokemon = getPokemonById(id);
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

}
