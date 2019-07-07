package mosecom.dictionaries;

public enum DocTypes {
    // Инспекции и обследования
    WELL_INSPECTION(1001),
    SPRING_INSPECTION(1002),
    DRAWWELL_INSPECTION(1003),

    // Документы по скважинам
    RECCARD(3001),
    PASSPORT(3002),
    DESCRIPTION(3007);

    private int id;

    DocTypes(int id) {
        this.id = id;
    }
}

