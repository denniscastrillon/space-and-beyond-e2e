package com.devco.spaceandbeyond.questions;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.screenplay.Question;

public class CheckoutForm {

    private CheckoutForm() {
    }

    public static Question<Boolean> payNowIsEnabled() {
        return Question.about("whether PAY NOW is enabled").answeredBy(actor ->
                CheckoutPage.PAY_NOW_BUTTON.resolveFor(actor).isEnabled());
    }
}
