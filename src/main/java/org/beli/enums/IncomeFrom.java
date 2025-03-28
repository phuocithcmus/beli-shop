package org.beli.enums;

public enum IncomeFrom {
    SALARY_HUSBAND("SALARY_HUSBAND"),
    SALARY_WIFE("SALARY_HUSBAND"),
    BUSINESS("SALARY_HUSBAND"),
    OTHER("SALARY_HUSBAND");

    private String value;

    IncomeFrom(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static IncomeFrom getIncomeFromByValue(String value) {
        for (IncomeFrom d : IncomeFrom.values()) {
            if (d.value == value) {
                return d;
            }
        }
        return null;
    }
}
