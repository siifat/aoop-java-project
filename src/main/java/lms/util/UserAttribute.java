package lms.util;

public enum UserAttribute {
    EMAIL("email"),
    ID("id");

    private final String columnName;

    UserAttribute(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName(){
        return columnName;
    }
}


