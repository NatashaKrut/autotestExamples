package example.cucumber.steps;

import example.cucumber.pages.MetroStationsPage;
import example.utils.ScenarioContext;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.sleep;

public class MetroStationsSteps {
    @Autowired
    MetroStationsPage metroStationsPage;


    @When("^Нажимаю на раздел Адреса$")
    public void pressAddress() {
        metroStationsPage.upperMenuAddressButton.shouldBe(visible).click();
    }

    @When("^Ввожу название метро в поиск (.*)$")
    public void typeToInputAddress(String name) {
        metroStationsPage.inputAddress.type(name);
    }

    @When("^Запоминаю адреса найденных офисов$")
    public void saveOfficesAddress () {
       ScenarioContext.getContext("addresses");
        List<String> addresses = metroStationsPage.officesCardAddress.texts();
        ScenarioContext.setContext("addresses", addresses);
    }

    @When("^Получаю все адреса по сохраненным станциям метро$")
    public void getAddressesForAllSavedStations(){
        List<String> allStations = (List<String>) ScenarioContext.getContext("allMetroStations");
        List<String> resultAddresses = new ArrayList<>();
        //для каждой станции метро из списка найденных в радиусе
        //выполняю поиск и собираю список адресов медофисов
        //собираю их в один List<String>, который и я вляется результатом
        allStations.forEach(station -> {
            metroStationsPage.inputAddress.type(station);
            sleep(1000);
            resultAddresses.addAll( metroStationsPage.officesCardAddress.texts());
        });

        //вывожу результат в консоль
        resultAddresses.forEach(System.out::println);
    }

    @When("^Нахожу станции метро в радиусе (\\d+) км от метро с названием (.*)$")
    public void findAllMetroStationsInRadius(int radius, String stationName) {
        ScenarioContext.setContext("allMetroStations", metroStationsPage.getAllStationsInRadius(radius, stationName));
    }
}
