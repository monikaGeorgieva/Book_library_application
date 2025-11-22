package book_library_app.book.model;

import lombok.Getter;

@Getter
public enum Type {
    ADVENTURE("Приключение"),
    FANTASY("Фантазия"),
    HISTORY("Историческа"),
    MYSTERY("Мистерия"),
    ROMANCE("Романтика"),
    HORROR("Ужас"),
    BIOGRAPHY("Биографична"),
    MEMOIR("Мемоар"),
    SELF_HELP("Самопомощ"),
    FICTION("Фикция"),
    NOVELS("Новела");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
