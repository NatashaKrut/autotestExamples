package example.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@Component
public class MedicalServicesPage {
    public final SelenideElement medService = $x("//a[text()='Медицинские услуги']").as("Пункт меню Медицинские услуги");
    public final SelenideElement medServiceSearchInput = $x("//input[@name='q']").as("Поле поиска медицинских услуг");
    public final SelenideElement searchPrompt = $x("//span[@class='result-item__filter']").as("Посдказка с найденным вариантом");
    public By href = By.xpath("./../.."); //содержимое ссылки относительно подсказки searchPrompt
    public final SelenideElement advertisementDecline = $x("//a[text()='Нет, спасибо']").as("Кнопка отказа в рекламе");
    public final ElementsCollection searchPrompts = $$x("//ul[@class='side-bar__list']/li").as("Посдказки с найденными вариантами");
    public By promptsHref = By.xpath("./a"); //содержимое ссылки относительно подсказки в коллекции searchPrompts
    public final ElementsCollection menuMedService = $$x("//a[ancestor::div[contains(@class,'side-bar')]]");
    public final ElementsCollection menuMedServiceWithoutText = $$x("//a[ancestor::div[contains(@class,'side-bar')] and not(text()='МРТ тела')]");

}
