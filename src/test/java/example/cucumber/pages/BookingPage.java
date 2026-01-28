package example.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Элементы и методы для работы с записью к врачу
 */
@Component
public class BookingPage{

    public final SelenideElement buttonSetDoctor = $x("//span[text()='Укажите врача или специальность']").as("Запись к врачу: Кнопка Укажите врача или специальность");
    public final SelenideElement buttonSetMedService = $x("//span[text()='Укажите медицинскую услугу']").as("Запись к врачу: Кнопка Укажите медицинскую услугу");
    public final SelenideElement buttonSetOffice = $x("//span[text()='Укажите офис']").as("Запись к врачу: Кнопка Укажите офис");
    public final SelenideElement inputSetDoctor = $x("//input[@data-testid='modal-doctorAndSpeciality_input']").as("Поле поиска врача или специальности");
    public final SelenideElement inputSetMedService = $x("//input[@data-testid='modal-product_input']").as("Поле поиска медицинской услуги");
    public final SelenideElement inputSetOffice = $x("//input[@data-testid='modal-office_input']").as("Поле поиска офиса");
    public final SelenideElement doctorCard = $x("//span[@class='fullName']").as("Карточка врача в результатах поиска");
    public final ElementsCollection medServiceCard = $$x("//span[@data-testid='name']/span").as("Карточка медицинской услуги в результатах поискае");
    public final SelenideElement OfficeCard = $x("//span[@data-testid='address']").as("Карточка офиса в результатах поиска");
    public final ElementsCollection filterPanel = $$x("//div[@data-testid='main-selected_filter']").as("Набор выбранных фильтров");
    public final ElementsCollection doctorListCards = $$x("//div[@class='vrachi_list__cards']/div").as("Список карточек после выбранных фильтров");
    public final ElementsCollection doctorJobs = $$x("//div[@class='vrach-card__job-info']//p").as("Специальности врача");
    public final SelenideElement doctorJob = $x("//span[@class='vrach-card__job-main-title blue_2']").as("Основная специальность врача");
    public final SelenideElement doctorCardCost = $x("//span[@class='vrach-card__cost']").as("Стоимость приема в карточке врача");
    public final ElementsCollection doctorCardAddress = $$x("//li[@class='vrach-card__address']/span[2]").as("Адрес приема в карточке врача");
    public final By mark = By.xpath("./preceding-sibling::span"); // Метка станции метро
    public final SelenideElement showMoreButton = $x("//div[contains(text(),'Показать еще')]").as("Кнопка Показать еще");

    /**
     * Метод открывает выбор врача или специальности,
     * вводит указанное значение,
     * выбирает найденную карточку
     * @param value - ФИО врача или специальность
     */
    public void setDoctor(String value) {
        buttonSetDoctor.click();
        inputSetDoctor.sendKeys(value);
        doctorCard.shouldHave(text(value)).click();
        doctorCard.shouldBe(disappear);
    }

    /**
     * Метод открывает выбор медицинской услуги,
     * вводит указанное значение,
     * выбирает найденную карточку
     * @param value - медицинская услуга
     */
    public void setMedService(String value) {
        buttonSetMedService.click();
        inputSetMedService.sendKeys(value);
        medServiceCard.filter(text(value)).first().click();
        inputSetMedService.shouldBe(disappear);
    }

    /**
     * Метод открывает выбор офиса,
     * вводит указанное значение,
     * выбирает найденную карточку
     * @param value - адрес офиса
     */
    public void setOffice(String value) {
        buttonSetOffice.click();
        inputSetOffice.sendKeys(value);
        OfficeCard.shouldHave(text(value)).click();
        OfficeCard.shouldBe(disappear);
    }


}
