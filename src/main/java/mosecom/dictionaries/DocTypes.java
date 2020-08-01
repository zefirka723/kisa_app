package mosecom.dictionaries;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum DocTypes {
    // Инспекции и обследования
    WELL_INSPECTION(1001, "Инспекции скважин", "Инспекция скважины"),
    SPRING_INSPECTION(1002, "Обследования родников", "Обследование родника"),
    DRAWWELL_INSPECTION(1003, "Обследования колодцев", "Обследование колодца"),
    INTAKE_INSPECTION(1201, "Обследования ВЗУ", "Обследование ВЗУ"),

    // Подземные воды
    GW_OBSERVATION_JOURNAL(1004, "Журналы наблюдений за уровнем и температурой ПВ", "Журнал наблюдений за уровнем и температурой ПВ"),
    GW_ANALYSIS_PROTOCOL(1101,	"Протоколы анализа пробы воды", "Протокол анализа пробы воды"),
    GW_JOURNAL (1202	, "Журналы опытно-фильтрационных работ", "Журнал опытно-фильтрационных работ"),
    GW_WELL_PUMPING_ACT(1203,"Акты прокачки скважины", "Акт прокачки скважины"),

    // Документы по скважинам
    RECCARD(3001, "Учётные карточки", "Учётная карточка"),
    PASSPORT(3002, "Паспорта скважин", "Паспорт скважины"),
    DESCRIPTION(3007, "Геологические описания скважин", "Геологическое описание скважины"),
    PLUGGING_ACT(3006, "Акты тампонажа скважин", "Акт тампонажа скважины"),
    OTHER_PD(8001, "Прочая документация", "Прочая документация"),

    // Отчёты
    LICENSE_REPORT(4004, "Отчёты недропользователей", "Отчёты недропользователей"),

    // ЭГП
    EGP_SURVEY_ACT(2001, "Акты визуального обследования участков ЭГП", "Акт визуального обследования участка ЭГП"),

    // разное
    CONCLUSION(5001, "Заключения ГЗ", "Заключение ГЗ"),
    PROTOCOL(4002, "Протоколы ОЗ", "Протокол ОЗ"),
    RECON_SURVEY_ACT(1900, "Акты рекогносцировочного обследования участков", "Акт рекогносцировочного обследования участка"),
    RADIOLOGY_PROTOCOL(1102, "Протоколы анализа проб воды на радиоактивность", "Протокол анализа проб воды на радиоактивность"),
    REPORTS_AND_MAPS(10000, "Отчёты и карты", "Отчёты и карты"),
    LICENSE(4001, "Лицензии", "Лицензия");

    private int id;
    private String listName;
    private String itemName;

    DocTypes(int id, String listName, String itemName) {
        this.id = id;
        this.listName = listName;
        this.itemName = itemName;
    }

    public int getId() {
        return id;
    }

    public String getListName() {
        return listName;
    }

    public String getItemName() {
        return itemName;
    }

    public static DocTypes getOneById(int id) throws NullPointerException {
        return Arrays.stream(DocTypes.values())
                .filter(DocTypes -> DocTypes.getId() == id)
                .findFirst()
                .orElseThrow(NullPointerException::new);
    }
}

