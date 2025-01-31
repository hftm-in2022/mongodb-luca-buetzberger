package ch.hftm.control;

import java.util.List;

import ch.hftm.entity.Pokemon;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PokemonService {

    public List<Pokemon> listAllPokemon() {
        return Pokemon.listAll();
    }

    @Transactional
    public void addEntry(Pokemon pokemon) {
        pokemon.persist();
    }
}