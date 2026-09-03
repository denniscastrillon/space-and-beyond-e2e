package com.devco.spaceandbeyond.questions;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import com.devco.spaceandbeyond.ui.DestinationsGallery;
import net.serenitybdd.screenplay.Question;

public class OrderSummary {

    private OrderSummary() {
    }

    public static Question<Double> total() {
        return Question.about("the order total").answeredBy(actor ->
                money(CheckoutPage.ORDER_TOTAL.resolveFor(actor).getText()));
    }

    public static Question<String> travellersAndDatesSummary() {
        return Question.about("the travellers & dates summary").answeredBy(actor ->
                DestinationsGallery.TRAVELLERS_SUMMARY.resolveFor(actor).getText().trim());
    }

    private static double money(String text) {
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }
}
