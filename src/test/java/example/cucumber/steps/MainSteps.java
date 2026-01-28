package example.cucumber.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Keys;

import java.time.LocalTime;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.actions;
import static org.assertj.core.api.Assertions.assertThat;

public class MainSteps {

    @When("^Жду завершения загрузки страницы$")
    public void waitPage() {
        LocalTime currentTime = LocalTime.now();
        LocalTime deadline = currentTime.plusMinutes(5);
        while (!Objects.equals(Selenide.executeJavaScript("return document.readyState"), "complete")) {
            sleep(5000);
            System.out.println("Жду завершения загрузки страницы...");
            LocalTime now = LocalTime.now();
            if (now.isAfter(deadline)) {
                Assertions.fail("Страница загружалась дольше 5 минут");
            }
        }
        System.out.println("Дождался");
    }

    @When("^Открываю главную страницу (.*)$")
    public void openPage(String url) {
        open(url);
        sleep(2000);
//        executeJavaScript("window.stop()");
    }

    @Then("^Проверяю что текущий адрес (.*)$")
    public void checkURL(String url) {
        assertThat(Selenide.webdriver().driver().getWebDriver().getCurrentUrl()).isEqualTo(url);
    }

    @When("^Нажимаю кнопку (.*) на клавиатуре$")
    public void pressKey(String key) {
        switch (key) {
            case "ENTER" -> actions().keyDown(Keys.ENTER).keyUp(Keys.ENTER).perform();
            case "TAB" -> actions().keyDown(Keys.TAB).keyUp(Keys.TAB).perform();
            default -> Assertions.fail("поведение для кнопки не описано");
        }
    }





}
