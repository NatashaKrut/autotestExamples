package example.cucumber.steps;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.UIAssertionError;
import example.cucumber.pages.MedicalServicesPage;
import example.utils.ScenarioContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static org.assertj.core.api.Assertions.assertThat;

public class MedicalServicesSteps {
    @Autowired
    MedicalServicesPage medServicesPage;

    @When("^Нажимаю на Медицинские услуги$")
    public void clickMedService() {
        medServicesPage.medService.click();
    }

    @When("^Ввожу в поиск (.*)$")
    public void typeToInput(String code) {
        medServicesPage.medServiceSearchInput.type(code);
    }

    @Then("^Проверяю что текст подсказки (.*)$")
    public void checkPrompt(String text) {
        assertThat(medServicesPage.searchPrompt.shouldBe(visible).getText()).as("Неверный текст подсказки").isEqualTo(text);
    }

    @Then("^Проверяю что подсказка имеет ссылку (.*)$")
    public void checkURLPrompt(String url) {
        assertThat(medServicesPage.searchPrompt.$(medServicesPage.href).getAttribute("href")).as("Неверная ссылка").isEqualTo(url);
    }

    @When("^Нажимаю на подсказку$")
    public void clickPrompt() {
        medServicesPage.searchPrompt.click();
    }

    @When("^Закрываю рекламу$")
    public void closeAds() {
        try { //пробуем дождаться окна с рекламой и отказаться
            medServicesPage.advertisementDecline.shouldBe(enabled, Duration.ofSeconds(60)).click();
        } catch (UIAssertionError ignore) {
            //если реклама не появилась (убрали с сайта) - игнорируем ошибку, возникшую в блоке try
        }
    }

    @Then("^Проверяю что отображается подсказки в количестве (\\d+)$")
    public void checkPromptsSize(int count) {
        assertThat(medServicesPage.searchPrompts.shouldHave(sizeGreaterThan(0)).size()).as("Неверное количество подсказок").isEqualTo(count);
    }

    @When("^Нахожу подсказку с текстом (.*)$")
    public void findFirstPrompt(String name) {
        SelenideElement foundPrompt = medServicesPage.searchPrompts.filter(text(name)).first();
        ScenarioContext.setContext("promptWithText", foundPrompt);
    }

    @Then("^Проверяю что найденная подсказка имеет ссылку (.*)$")
    public void checkSavedPromptHref(String url) {
        SelenideElement foundPrompt = (SelenideElement) ScenarioContext.getContext("promptWithText");
        assertThat(foundPrompt.$(medServicesPage.promptsHref).getAttribute("href")).isEqualTo(url);
    }

    @When("^Нажимаю на найденную подсказку$")
    public void clickSavePrompt() {
        SelenideElement foundPrompt = (SelenideElement) ScenarioContext.getContext("promptWithText");
        foundPrompt.click();
    }

    @When("^Нажимаю на каждый элемент в меню Медицинские услуги$")
    public void clickMenuMedService() {
        medServicesPage.menuMedService.shouldHave(sizeGreaterThan(0));
        medServicesPage.menuMedService.asDynamicIterable()
                .forEach(SelenideElement::click);
    }

    @When("^Нажимаю на каждый элемент в меню Медицинские услуги за исключением МРТ тела на уровне xPath$")
    public void clickMenuMedServiceExceptFromXpath() {
        medServicesPage.menuMedServiceWithoutText.shouldHave(sizeGreaterThan(0));
        medServicesPage.menuMedServiceWithoutText.asDynamicIterable()
                .forEach(SelenideElement::click);
    }

    @When("^Нажимаю на каждый элемент в меню Медицинские услуги за исключением МРТ тела из коллекции$")
    public void clickMenuExceptFromCollection() {
        medServicesPage.menuMedService.shouldHave(sizeGreaterThan(0));
        medServicesPage.menuMedService.filter(not(text("МРТ тела"))).asDynamicIterable().forEach(SelenideElement::click);
    }

    @When("^Нажимаю на каждый элемент в меню Медицинские услуги за исключением МРТ тела во время клика$")
    public void clickMenuExceptWhenClick() {
        medServicesPage.menuMedService.asDynamicIterable()
                .forEach(selenideElement -> {
                    if (!selenideElement.getText().equals("МРТ тела")) {
                        selenideElement.click();
                    }
                });
    }

}
