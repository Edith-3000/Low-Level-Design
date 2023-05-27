package models;

public class Mark {
    private final MarkType markType;

    public Mark(final MarkType markType) {
        this.markType = markType;
    }

    public MarkType getMarkType() {
        return markType;
    }
}
