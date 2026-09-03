package com.devco.spaceandbeyond.model;

import java.time.LocalDate;
import java.util.Map;

public record TravelSearch(int departingInDays, int returningInDays, int adults, int children) {

    public static TravelSearch from(Map<String, String> row) {
        return new TravelSearch(
                Integer.parseInt(row.get("departingInDays").trim()),
                Integer.parseInt(row.get("returningInDays").trim()),
                Integer.parseInt(row.get("adults").trim()),
                Integer.parseInt(row.getOrDefault("children", "0").trim())
        );
    }

    public LocalDate departureDate() {
        return LocalDate.now().plusDays(departingInDays);
    }

    public LocalDate returnDate() {
        return LocalDate.now().plusDays(returningInDays);
    }

    @Override
    public String toString() {
        return String.format("depart %s, return %s, %d adult(s), %d child(ren)",
                departureDate(), returnDate(), adults, children);
    }
}
