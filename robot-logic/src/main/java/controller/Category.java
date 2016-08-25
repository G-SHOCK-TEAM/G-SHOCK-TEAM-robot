package controller;

/**
 * Enum which represents all available categories in the application.
 * It is possible to return the text (description description) using getDescription() method.
 */
public enum Category {
    EDUCATION_AND_SCIENCE("Education & science"),
    TRAVEL("Travelling"),
    LIFESTYLE("Lifestyle & cooking"),
    SEX("Sex"),
    MEDICINE("Medicine"),
    ADVENTURE("Adventure & hobby");

    private final String description;

    Category(String name) {
        this.description = name;
    }

    /**
     * Method to obtain the description of the category.
     *
     * @return string with the description of particular category.
     */
    public String getDescription() {
        return description;
    }
}