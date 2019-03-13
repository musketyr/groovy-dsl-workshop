package cz.orany.yuml.model;

import java.util.Objects;

public class DefaultNote implements DiagramContentDefinition, Note {

    private final String text;
    private final String color;

    public DefaultNote(String text, String color) {
        this.text = text;
        this.color = color;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultNote note = (DefaultNote) o;
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
