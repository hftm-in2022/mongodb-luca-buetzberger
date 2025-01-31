package ch.hftm.boundary;

import java.util.List;

import ch.hftm.control.PokemonService;
import ch.hftm.entity.Pokemon;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/pokemon")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PokemonResource {

    @Inject
    PokemonService pokemonService;

    @GET
    @Blocking
    public List<Pokemon> getAllPokemons() {
        return pokemonService.listAllPokemon();
    }

    @POST
    @Blocking
    public void createEntry(Pokemon pokemon) {
        pokemonService.addEntry(pokemon);
    }
}