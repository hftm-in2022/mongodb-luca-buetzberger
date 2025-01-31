package ch.hftm.control;

import java.util.List;

import ch.hftm.entity.Blog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class BlogService {

    public List<Blog> listAllEntries() {
        return Blog.listAll();
    }

    @Transactional
    public void addEntry(Blog entry) {
        entry.persist();
    }
}