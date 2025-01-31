
package ch.hftm.entity;

import java.util.List;
import java.util.Set;

import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Blog extends PanacheMongoEntity {
    public String title;
    public String content;
    public String author;
    public List<Comment> comments;
    public Set<String> userIdLikes;
}