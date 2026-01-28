package example.cucumber.steps;

import com.codeborne.selenide.ClickOptions;
import com.codeborne.selenide.SelenideElement;
import example.cucumber.pages.AnalyzesPage;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class AnalyzesSteps{

    /**
     * Page-классы, с которыми работают шаги
     */
    @Autowired
    AnalyzesPage analyzesPage;

    /**
     * Константы
     */

    private final String color_turquoise = "rgba(8, 195, 220, 1)";


    @Then("^Проверяю количество карточек анализов на странице равно (\\d+)$")
    public void checkQuantityAnalyzesCards(int count) {
        assertThat(analyzesPage.analyzesCards.size()).isEqualTo(count);
    }

    @Then("^Проверяю наличие бонусов под ценой в каждой карточе анализов$")
    public void checkBonuses() {
        //Иногда блок бонусов в карточках не загружается сразу, необходимо обновить страницу и подождать
        if (analyzesPage.bonuses.isEmpty()) {
            sleep(3000);
            refresh();
            sleep(1000);
        }
        analyzesPage.bonuses.shouldHave(sizeGreaterThan(0));
        analyzesPage.bonuses.last().scrollTo();
        assertThat(analyzesPage.analyzesCards.size()).isEqualTo(analyzesPage.bonuses.filter(visible).size());
    }

    @Then("^Нажимаю на кнопку Показать еще$")
    public void clickButtonShowMore() {
        analyzesPage.showMoreButton.click();
    }

    @Then("^Нажимаю на кнопку страница (.*) и проверяю что переход на страницу осуществлен$")
    public void clickButtonPage4(String number) {
        SelenideElement currentButton = analyzesPage.pageButtons.filter(text(number)).first();
        assertThat(currentButton).as("Не найдена кнопка с номером " + number).isNotNull();
        currentButton.scrollIntoView(false).click(ClickOptions.usingJavaScript());
        sleep(5000);
        assertThat(currentButton.getCssValue("color")).as("Неверный цвет").isEqualTo(color_turquoise);
    }

    @Then("^В блоке пагинации нажимаю стрелку вперед и проверяю что осуществлен переход на страницу (.*)$")
    public void clickButtonForwardArrow(String number) {
        analyzesPage.buttonForwardArrow.click();
        SelenideElement currentButton = analyzesPage.pageButtons.filter(text(number)).first();
        assertThat(currentButton).as("Не найдена кнопка с номером " + number).isNotNull();
        assertThat(currentButton.getCssValue("color")).as("Неверный цвет").isEqualTo(color_turquoise);
    }

    @Then("^Проверяю наличие кнопки Фильтр$")
    public void checkButtonFilter() {
        analyzesPage.buttonFilter.shouldBe(visible);
    }

    @Then("^Нажимаю на кнопку Фильтр и проверяю что отобразился поп-ап с фильтрами$")
    public void clickButtonFilter() {
        analyzesPage.buttonFilter.click();
        analyzesPage.filterWindow.shouldBe(visible);
    }

    @Then("^Проверяю наличие названия поп-апа Фильтр$")
    public void checkTextPopUpHeader() {
        analyzesPage.popUpHeader.shouldBe(visible).shouldHave(text("Фильтр"));
    }

    @Then("^Проверяю наличие кнопки Крестик$")
    public void checkButtonClose() {
        analyzesPage.popUpClose.shouldBe(visible);
    }

    @Then("^Проверяю наличие чек-бокса Доступен выезд на дом$")
    public void checkCheckbox() {
        analyzesPage.checkbox.shouldBe(visible).shouldHave(text("Доступен выезд на дом"));
    }

    @Then("^Проверяю наличие фильтров$")
    public void checkFilters(List<String> nameFilter) {
        assertThat(analyzesPage.filtersOfFilter.filter(visible).texts()).isEqualTo(nameFilter);
    }
}
