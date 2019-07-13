package mosecom.dictionaries;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum DocTypes {
    // Инспекции и обследования
    WELL_INSPECTION(1001, "Инспекции скважин", "Инспекция скважины"),
    SPRING_INSPECTION(1002, "Обследования родников", "Обследование родника"),
    DRAWWELL_INSPECTION(1003, "Обследования колодцев", "Обследование колодца"),

    // Документы по скважинам
    RECCARD(3001, "Учётные карточки", "Учётная карточка"),
    PASSPORT(3002, "Паспорта скважин", "Паспорт скважины"),
    DESCRIPTION(3007, "Геологические описания скважин", "Геологическое описание скважины");

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

