package example.cucumber.steps;

import example.cucumber.pages.DoctorPage;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import static com.codeborne.selenide.Condition.visible;

public class DoctorSteps {
    @Autowired
    DoctorPage doctorPage;

    @When("^Нажимаю на Врачи$")
    public void pressDoctors() {
        doctorPage.upperMenuDoctorsButton.shouldBe(visible).click();
    }

    @When("^Ввожу (.*) в поиск врачей$")
    public void typeToInputDoctor(String name) {
        doctorPage.inputDoctor.type(name);
    }

}
