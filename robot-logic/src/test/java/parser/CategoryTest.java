package parser;

import controller.Category;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static controller.Category.*;

@Test()
public class CategoryTest {
    @DataProvider(name = "categoryDescription")
    private Object[][] testDescriptions() {
        return new Object[][]{
                new Object[]{EDUCATION_AND_SCIENCE, "Education & science"},
                new Object[]{TRAVEL, "Travelling"},
                new Object[]{LIFESTYLE, "Lifestyle & cooking"},
                new Object[]{SEX, "Sex"},
                new Object[]{MEDICINE, "Medicine"},
                new Object[]{ADVENTURE, "Adventure & hobby"}
        };
    }

    @Test(dataProvider = "categoryDescription")
    public void testCategoryDescriptions(Category category, String description) {
        // given, when
        String actualDescription = category.getDescription();
        // then
        assertEquals(actualDescription, description);
    }
}