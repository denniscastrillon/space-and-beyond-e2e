package com.devco.spaceandbeyond.model;

import java.util.Map;

public record Traveller(String name, String email, String socialSecurityNumber, String phoneNumber) {

    public static Traveller from(Map<String, String> row) {
        return new Traveller(
                row.get("name"),
                row.get("email"),
                row.get("socialSecurityNumber"),
                row.get("phoneNumber")
        );
    }

    @Override
    public String toString() {
        return name + " <" + email + ">";
    }
}
