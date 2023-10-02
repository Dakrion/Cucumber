package steps;

import com.codeborne.selenide.*;
import com.codeborne.selenide.conditions.Text;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

public class WildberriesStep {
    private final SelenideElement searchInput = $(By.id("searchInput"));
    private final SelenideElement searchResultTitle = $(By.className("searching-results__title"));
    private final ElementsCollection products = $$x("//*[contains(@class,\"product-card \")]");
    private final ElementsCollection searchTagsList = $$x("//*[contains(@class,\"search-tags__item \")]");

    @Given("Открываем сайт {string}")
    public void openWebSite(String url) {
        Selenide.open(url);
    }

    @When("Вводим в поисковую строку {string}")
    public void search(String value) {
        if (searchInput.isDisplayed()) {
            searchInput.click();
            searchInput.val(value).pressEnter();
        }
    }

    @And("Ждем отображения результатов")
    public void waitResults() throws InterruptedException {
        Thread.sleep(5000);
        searchResultTitle.shouldBe(Condition.visible);
    }

    @Then("Проверяем количество результатов")
    public void checkCountResults() {
        System.out.println(products.asDynamicIterable().stream().count());
    }

    @And("Выбираем предустановленный фильтр {string}")
    public void selectFilter(String filterName) {
        SelenideElement filter = searchTagsList.asDynamicIterable().stream()
                .filter(selenideElement -> selenideElement.has(Text.innerText(filterName)))
                .findFirst()
                .orElseThrow();
        if (filter.isDisplayed()) filter.click();
    }
}
