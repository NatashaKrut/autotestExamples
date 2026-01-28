package example.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static java.lang.Math.*;

@Component
public class MetroStationsPage {

    public final SelenideElement upperMenuAddressButton = $x("//a[@href and text()='Адреса']").as("Кнопка Адреса в верхнем меню навигации");
    public final SelenideElement inputAddress = $x("//input[@placeholder='Введите название, метро или адрес']").as("Поиск адреса");
    public final ElementsCollection officesCardAddress = $$x("//div[@class='offices_card__address offices_card__address--title']").as("Адрес в карточке офиса");


    public List<String> getAllStationsInRadius(int radius, String startStation) {
        Response metroStationsData =  RestAssured.given().baseUri("https://api.hh.ru/metro/1").get();
        metroStationsData.then().statusCode(200);
        Map<String, Float> coords = getCoordinatesByName(metroStationsData, startStation);
        System.out.println("Координаты центральной станции:");
        System.out.println("Широта: " + coords.get("lat"));
        System.out.println("Долгота: " + coords.get("lng"));
        List<String> allStationsInRadius = findStationsInRadius(metroStationsData,coords.get("lat"),coords.get("lng"),radius);
        System.out.println("Список станций ");
        System.out.println(allStationsInRadius);
        return allStationsInRadius;
    }

    public List<String> findStationsInRadius(Response response,
                                                    double centerLat,
                                                    double centerLng,
                                                    double radiusKm) {
        List<String> stationsInRadius = new ArrayList<>();
        // Получаем все станции из JSON
        List<Map<String, Object>> allStations = response.jsonPath()
                .getList("lines.stations.flatten()");
        for (Map<String, Object> station : allStations) {
            String stationName = (String) station.get("name");
            double stationLat = ((Number) station.get("lat")).doubleValue();
            double stationLng = ((Number) station.get("lng")).doubleValue();
            // Рассчитываем расстояние
            double distance = calculateDistance(centerLat, centerLng, stationLat, stationLng);
            // Если расстояние меньше или равно радиусу, добавляем станцию
            if (distance <= radiusKm) {
                stationsInRadius.add(stationName);
            }
        }

        return stationsInRadius;
    }

    /**
     * Рассчитывает расстояние между двумя точками по формуле гаверсинусов
     * (метод для расчета расстояния на сфере)
     *
     * @param lat1 Широта первой точки
     * @param lon1 Долгота первой точки
     * @param lat2 Широта второй точки
     * @param lon2 Долгота второй точки
     * @return Расстояние в километрах
     */
    private double calculateDistance(double lat1, double lon1,
                                            double lat2, double lon2) {
        final int R = 6371; // Радиус Земли в километрах
        double latDistance = toRadians(lat2 - lat1);
        double lonDistance = toRadians(lon2 - lon1);
        double a = sin(latDistance / 2) * sin(latDistance / 2)
                + cos(toRadians(lat1)) * cos(toRadians(lat2))
                * sin(lonDistance / 2) * sin(lonDistance / 2);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));
        return R * c;
    }

    public Map<String, Float> getCoordinatesByName(Response response, String stationName) {
        Map<String, Object> station = response.jsonPath().getMap(
                "lines.stations.flatten().find { it.name == '" + stationName + "' }"
        );
        if (station == null) {
            throw new RuntimeException("Станция с именем '" + stationName + "' не найдена");
        }
        Map<String, Float> coordinates = new HashMap<>();
        coordinates.put("lat", (Float) station.get("lat"));
        coordinates.put("lng", (Float) station.get("lng"));
        return coordinates;
    }
}
