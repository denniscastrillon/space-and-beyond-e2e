package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class AcceptTheTermsAndConditions implements Task {

    public static AcceptTheTermsAndConditions checkbox() {
        return instrumented(AcceptTheTermsAndConditions.class);
    }

    @Step("{0} accepts the terms and conditions")
    @Override
    public <T extends Actor> void performAs(T actor) {
        boolean alreadyAccepted = CheckoutPage.TERMS_CHECKBOX_INPUT.resolveFor(actor).isSelected();
        if (!alreadyAccepted) {
            actor.attemptsTo(
                    Scroll.to(CheckoutPage.TERMS_AND_CONDITIONS_CHECKBOX),
                    Click.on(CheckoutPage.TERMS_AND_CONDITIONS_CHECKBOX)
            );
        }
    }
}
