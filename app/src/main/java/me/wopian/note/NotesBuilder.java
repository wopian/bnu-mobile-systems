package me.wopian.note;

public class NotesBuilder {
    private String title;

    NotesBuilder(String title) {
        this.title = title;
    }

    public String getTitle () {
        return title;
    }
}
