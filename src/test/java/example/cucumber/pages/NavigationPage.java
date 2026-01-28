package example.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

@Component
public class NavigationPage {
    public final SelenideElement headerMenuButton = $x("//button[@class='invitro_header-menu__btn header-menu__btn ripple']").as("Бургер меню");
    public final ElementsCollection headerMenuList = $$x("//div[@class='row row--wrap row--start invitro_header-hidden_tablet']//*[contains(@class,'header-menu__link')]").as("Содержимое Бургер меню");
    public final SelenideElement selectCity = $x("//div[@id='city']").as("Выбрать город");
    public final SelenideElement citySearch = $x("//input[@class='change-city-search-input']").as("Поиск города");
    public final SelenideElement chooseAnother = $x("//span[contains(text(),'другой')]").as("Выбрать Другой город");;
}
