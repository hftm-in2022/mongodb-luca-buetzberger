package ch.hftm.entity;

public class Comment {
    public String author;
    public String content;
    public Comment() {}

    public Comment (String author, String content) {
        this.author = author;
        this.content = content;
    }
}
