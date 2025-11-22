package book_library_app.user.model;

import lombok.Getter;

@Getter
public enum Country {

    BULGARIA("България"),
    ARMENIA("Армения"),
    COSTA_RICA("Коста Рика"),
    CUBA("Куба"),
    FRANCE("Франция"),
    GERMANY("Германия"),
    SWITZERLAND("Щвейцария"),
    GREECE("Гърция"),
    SPAIN("Испания"),
    ITALY("Италия"),
    JAPAN("Япония"),
    INDIA("Индия"),
    UNITED_KINGDOM("Великобритания"),
    UNITED_STATES("Америка"),
    UKRAINIAN("Украйна"),
    OTHER ("Друга");

    private final String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
