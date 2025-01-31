package ch.hftm;

import java.util.List;
import java.util.Set;

import ch.hftm.entity.Blog;
import ch.hftm.entity.Comment;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;


@QuarkusMain
public class App implements QuarkusApplication {

    @Override
    public int run(String... args) throws Exception {
        System.out.println("Starting the application...");

        // Create comments
        var comments = List.of(
            new Comment("John Doe", "Hello World!"),
            new Comment("Jane Doe", "Great post!")
        );

        // Create a blog entry
        var blogEntry = new Blog();
        blogEntry.title = "Hello World";
        blogEntry.content = "This is my first blog entry";
        blogEntry.author = "John Doe";
        blogEntry.comments = comments;
        blogEntry.userIdLikes = Set.of("John Doe", "Jane Doe");

        // Persist the blog entry
        blogEntry.persist();

        System.out.println("Blog entry persisted!");
        return 0;
    }
}