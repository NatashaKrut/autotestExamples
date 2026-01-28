package example.cucumber.steps;

import com.codeborne.selenide.SelenideElement;
import example.cucumber.pages.NavigationPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.Keys;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Condition.text;
import static org.assertj.core.api.Assertions.assertThat;

public class NavigationSteps {

    @Autowired
    NavigationPage navigationPage;

    @When("^Нажимаю бургер-меню$")
    public void clickHeaderMenuButton() {
        navigationPage.headerMenuButton.click();
    }

    @When("^Нажимаю на (.*) в верхнем меню$")
    public void clickElementHeaderMenuList(String element) {
        navigationPage.headerMenuList.filter(text(element)).first().click();
    }

    @When("^Выбираю город (.*)$")
    public void selectCity(String city) {
        navigationPage.selectCity.click();
        navigationPage.chooseAnother.click();
        navigationPage.citySearch.sendKeys(city + Keys.ARROW_DOWN + Keys.ENTER);
    }

    @Then("Проверяю что выбранный город (.*)$")
    public void checkSelectedCity(String city) {
        assertThat(navigationPage.selectCity.getText()).isEqualTo(city);
    }


}
