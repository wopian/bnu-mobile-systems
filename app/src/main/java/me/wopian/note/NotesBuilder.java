package me.wopian.note;

public class NotesBuilder {
    private String title;

    public NotesBuilder () {

    }

    public NotesBuilder (String title) {
        this.title = title;
    }

    public String getTitle () {
        return title;
    }
}
