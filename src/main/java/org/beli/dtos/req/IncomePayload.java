package org.beli.dtos.req;

public record IncomePayload(int month, int year, String from, String note, String amount) {
}
