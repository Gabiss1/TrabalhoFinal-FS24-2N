package Services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.Pokemon;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonReader {
    public JsonReader(){}
    public List<Pokemon> lerPokemonsDoJson() throws IOException {
// Usa o class loader para encontrar o arquivo no classpath (src/main/resources)
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("pokemons.json");
        if (inputStream == null) {
            throw new IOException("Arquivo 'pokemons.json' não encontrado na pasta resources.");
        }
// O ObjectMapper é a classe principal para conversão JSON -> Java e vice-versa
        ObjectMapper mapper = new ObjectMapper();
// Usa TypeReference para informar ao Jackson que queremos uma lista de Pokemons
        List<Pokemon> pokemons = mapper.readValue(inputStream, new TypeReference<List<Pokemon>>() {});
        System.out.println(pokemons.getFirst().getFk_id_treinador());
        return pokemons;
    }
}