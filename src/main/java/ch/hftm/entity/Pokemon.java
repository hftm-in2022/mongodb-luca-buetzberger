
package ch.hftm.entity;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Pokemon extends PanacheMongoEntity {
    public String pokedexID;
    public String name;
    public String weight;
    public List<String> types;
}