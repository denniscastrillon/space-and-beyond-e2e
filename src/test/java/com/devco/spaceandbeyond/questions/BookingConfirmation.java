package com.devco.spaceandbeyond.questions;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.screenplay.Question;

public class BookingConfirmation {

    private static final String EXPECTED_TEXT = "destination booked";

    private BookingConfirmation() {
    }

    public static Question<String> message() {
        return Question.about("the booking confirmation message").answeredBy(actor ->
                CheckoutPage.BOOKING_CONFIRMATION_MESSAGE.resolveAllFor(actor).stream()
                        .filter(element -> element.isCurrentlyVisible())
                        .map(element -> element.getText().trim())
                        .filter(text -> text.toLowerCase().contains(EXPECTED_TEXT))
                        .findFirst()
                        .orElse(""));
    }
}
