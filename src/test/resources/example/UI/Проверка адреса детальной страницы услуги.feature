@scenario-1

Feature: Работа с UI

  Scenario: Проверка ссылок в подсказках
    Given Открываю главную страницу https://www.invitro.ru/?CITY_NAME=moscow
    When Нажимаю на Медицинские услуги
    When Жду завершения загрузки страницы
    Then Проверяю что текущий адрес https://www.invitro.ru/moscow/radiology/
    When Ввожу в поиск  N70
    Then Проверяю что текст подсказки УЗИ с допплеровской оценкой транскраниальная артерий методом мониторирования
    Then Проверяю что подсказка имеет ссылку https://www.invitro.ru/moscow/radiology/uzi/obsledovaniya-sosudov/transkranialnaya-ultrazvukovaya-dopplerografiya/
    When Нажимаю на подсказку
    When Жду завершения загрузки страницы
    Then Проверяю что текущий адрес https://www.invitro.ru/moscow/radiology/uzi/obsledovaniya-sosudov/transkranialnaya-ultrazvukovaya-dopplerografiya/
    When Ввожу в поиск Рентген
    When Закрываю рекламу
    Then Проверяю что отображается подсказки в количестве 10
    When Нахожу подсказку с текстом Рентгенография голеностопного сустава
    Then Проверяю что найденная подсказка имеет ссылку https://www.invitro.ru/moscow/radiology/rentgen/sustavov-kostej/rentgenografiya_golenostopnogo_sustava/
    When Нажимаю на найденную подсказку
    Then Проверяю что текущий адрес https://www.invitro.ru/moscow/radiology/rentgen/sustavov-kostej/rentgenografiya_golenostopnogo_sustava/