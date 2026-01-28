package example.cucumber.pages;

import com.codeborne.selenide.SelenideElement;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$x;

@Component
public class DoctorPage {

    public final SelenideElement upperMenuDoctorsButton = $x("//a[@href and text()='Врачи']").as("Кнопка Врачи в верхнем меню навигации");
    public final SelenideElement inputDoctor = $x("//input[@id='vrachi_filter_search']").as("Поле поиска ФИО/Специальность/Адрес ");
}
