package example.cucumber.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Элементы и методы для работы с анализами
 */
@Component
public class AnalyzesPage {
    public final ElementsCollection analyzesCards = $$x("//div[@class='analyzes-list item_card item_card--active']").as("Карточки анализов");
    public final ElementsCollection bonuses = $$x("//div[@class='analyzes-item__total--bonus']").as("Бонусы в карточке анализов");
    public final SelenideElement showMoreButton = $x("//span[text()='Показать еще']").as("Кнопка Показать еще для карточек анализов");
    public final ElementsCollection pageButtons = $$x("//a[contains(@class,'catalog_pagination__elem')]").as("Кнопки пагинации в нижней части страницы");
    public final SelenideElement buttonForwardArrow = $x("(//span[@class='ds_box_svg ds_stroke_whidth_1 nocolor_dark'])[2]").as("Кнопка пагинации Вперед");
    public final SelenideElement buttonFilter = $x("//div[@class='side-bar__filter--btn ds_b_3x ds_b_inline open-popup-filter']").as("Анализы: кнопка Фильтр");
    public final SelenideElement filterWindow = $x("//div[@class='search-filter__filter opened']").as("Анализы: окно фильтра");
    public final SelenideElement popUpHeader = $x("//div[@class='search-filter__filter--header']/span").as("Анализы: заголовок в окне фильтра");
    public final SelenideElement popUpClose = $x("//div[@class='search-filter__filter--header']/span/following-sibling::*[name()='svg']").as("Анализы: кнопка закрыть окно фильтра");
    public final SelenideElement checkbox = $x("//span[contains(text(),'Доступен выезд на дом')]").as("Анализы: чек-бокс Доступен выезд на дом в окне фильтра");
    public final ElementsCollection filtersOfFilter = $$x("//div[@class='search-filter__filter--list']/button").as("Анализы: фильтры в окне фильтра");
}

