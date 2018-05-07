package me.wopian.note;

public class NotesBuilder {
    private String title;
    private String content;

    public NotesBuilder () {

    }

    public NotesBuilder (String title) {
        this.title = title;
    }

    public String getTitle () {
        return title;
    }
}
