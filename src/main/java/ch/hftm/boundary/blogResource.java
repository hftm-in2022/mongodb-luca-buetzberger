package ch.hftm.boundary;

import java.util.List;

import ch.hftm.control.BlogService;
import ch.hftm.entity.Blog;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/blogs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class blogResource {

    @Inject
    BlogService blogService;

    @GET
    @Blocking
    public List<Blog> getAllEntries() {
        return blogService.listAllEntries();
    }

    @POST
    @Blocking
    public void createEntry(Blog entry) {
        blogService.addEntry(entry);
    }
}