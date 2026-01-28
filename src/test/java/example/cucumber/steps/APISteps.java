package example.cucumber.steps;

import example.utils.ScenarioContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class APISteps {

    String token = "Token 5a23dbe139be0ee91ebdece970882452c2c1b261";
    String secretKey = "d68da7098234a04cb25f9a38825b691c8e4881ae";

    @Given("^Отправляю запрос Кем выдан паспорт (.*)$")
    public void sendPassportRequest(String code) {
        Response passport = RestAssured.given()
                .baseUri("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/fms_unit")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", token)
                .body(String.format("{ \"query\": \"%s\" }", code))
                .post();
        passport.then().statusCode(200);
        List<String> values = passport.jsonPath().getList("suggestions.value");
        ScenarioContext.setContext("values", values);
    }

    @Then("^Проверяю что код подразделения соответствует названиям (.*)$")
    public void checkName(String names) {
        List<String> expectedNames = List.of(names.split(", "));
        List<String> actualNames = (List<String>) ScenarioContext.getContext("values");
        assertThat(actualNames)
                .as("Не верные названия подразделений")
                .containsExactlyInAnyOrderElementsOf(expectedNames);
    }

    @Given("^Отправляю запрос номера телефона (.*)$")
    public void sendPhoneNumberRequest (String number){
        Response phoneNumber = RestAssured.given()
                .baseUri("https://cleaner.dadata.ru/api/v1/clean/phone")
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", token)
                .header("X-Secret", secretKey)
                .body(String.format("[\"%s\"]", number))
                .post();
        phoneNumber.then().statusCode(200);
        String phone = phoneNumber.jsonPath().getString("phone[0]");
        String region = phoneNumber.jsonPath().getString("region[0]");
        ScenarioContext.setContext("phone", phone);
        ScenarioContext.setContext("region", region);
    }

    @Then("^Проверяю что номер телефона соответсвует номеру (.*)$")
    public void checkPhoneNumber (String number){
        assertThat(ScenarioContext.getContext("phone")).isEqualTo(number);
    }

    @Then("^Проверяю что номер телефона соответсвует региону (.*)$")
    public void checkRegion (String region){
        assertThat(ScenarioContext.getContext("region")).isEqualTo(region);
    }
}
