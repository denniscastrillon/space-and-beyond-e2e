package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isEnabled;

public class PayNow implements Task {

    public static PayNow toCompleteTheBooking() {
        return instrumented(PayNow.class);
    }

    @Step("{0} presses PAY NOW")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(CheckoutPage.PAY_NOW_BUTTON),
                WaitUntil.the(CheckoutPage.PAY_NOW_BUTTON, isEnabled()).forNoMoreThan(10).seconds(),
                Click.on(CheckoutPage.PAY_NOW_BUTTON)
        );
    }
}
