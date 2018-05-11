package me.wopian.note;

public class NotesBuilder {
    private String title;
    private String ago;

    NotesBuilder(String title, String ago) {
        this.title = title;
        this.ago = ago;
    }

    /*
    NotesBuilder(String title, String ago) {
        this.title = title;
        this.ago = ago;
    }
    */

    public String getTitle() {
        return title;
    }

    public String getAgo() {
        return ago;
    }
}
