package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class ApplyThePromotionalCode implements Task {

    private final String promoCode;

    public ApplyThePromotionalCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public static ApplyThePromotionalCode of(String promoCode) {
        return instrumented(ApplyThePromotionalCode.class, promoCode);
    }

    @Step("{0} applies the promotional code '#promoCode'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(promoCode).into(CheckoutPage.PROMO_CODE_FIELD),
                WaitUntil.the(CheckoutPage.APPLY_PROMO_BUTTON, isEnabled()).forNoMoreThan(5).seconds(),
                Click.on(CheckoutPage.APPLY_PROMO_BUTTON)
        );
    }
}
