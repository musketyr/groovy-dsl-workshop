package cz.orany.yuml.model;

import java.util.Objects;

public class Note {

    private String text;
    private String color;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(text, note.text) &&
                Objects.equals(color, note.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, color);
    }

    @Override
    public String toString() {
        return "Note{" +
                "text='" + text + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

}
