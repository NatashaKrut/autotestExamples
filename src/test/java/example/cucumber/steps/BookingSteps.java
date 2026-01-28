package example.cucumber.steps;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import example.cucumber.pages.BookingPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class BookingSteps {

    @Autowired
    BookingPage bookingPage;

    @When("^Указываю (врача или специальность|медицинскую услугу|офис): (.*)$")
    public void setBookingFilter(String filter, String value) {
        switch (filter) {
            case "врача или специальность" -> bookingPage.setDoctor(value);
            case "медицинскую услугу" -> bookingPage.setMedService(value);
            case "офис" -> bookingPage.setOffice(value);
        }
    }

    @Then("^Проверяю что выбранные фильтры отображаются в панели$")
    public void checkFilterPanel(List<String> nameFilter) {
        assertThat(bookingPage.filterPanel.texts()).isEqualTo(nameFilter);
    }

    @Then("^Проверяю что отображается одна карточка врача (.*)$")
    public void checkCardDoctor(String name) {
        assertThat(bookingPage.doctorListCards.filter(text(name)).first().getText()).
                contains(name);
    }

    @Then("^Проверяю что основная специальность врача (.*) указана первой в карточке$")
    public void checkBasicDoctorJob(String job) {
        sleep(2000);
        assertThat(bookingPage.doctorJobs.first().getText()).isEqualTo(job);
    }

    @Then("^Проверяю что у искомого врача основная специальность (.*) и она синего цвета$")
    public void checkDoctorJobAndColor(String job) {
        assertThat(bookingPage.doctorJob.getText()).isEqualTo(job);
        assertThat(bookingPage.doctorJob.getCssValue("color")).isEqualTo("rgba(0, 151, 179, 1)");
    }

    @Then("^Проверяю что цена на первичный прием (.*)$")
    public void checkCost(String cost) {
        assertThat(bookingPage.doctorCardCost.getText()).isEqualTo(cost);
    }

    @Then("^Проверяю что в карточке есть специальность (.*)$")
    public void checkDoctorJob(String job) {
        assertThat(bookingPage.doctorJobs.texts()).contains(job);
    }

    @Then("^Проверяю что список специальностей состоит из$")
    public void checkJobsList(List<String> lobList) {
        assertThat(bookingPage.doctorJobs.texts()).isEqualTo(lobList);
    }

    @Then("^Проверяю что станции (.*) соответствует метка (.*) цвета$")
    public void checkMarkColor(String metro, String color) {
        bookingPage.showMoreButton.shouldBe(visible).hover();
        sleep(2000);
        SelenideElement station = bookingPage.doctorCardAddress
                .shouldHave(sizeGreaterThan(2))
                .filter(ownText(metro))
                .first();
        String rgba = "";
        switch (color) {
            case "синего" -> rgba = "rgba(0, 120, 191, 1)";
            case "фиолетового" -> rgba = "rgba(142, 71, 155, 1)";
            case "оранжевого" -> rgba = "rgba(245, 130, 32, 1)";
        }
        assertThat(station.$(bookingPage.mark).getCssValue("background-color")).isEqualTo(rgba);
    }
}
