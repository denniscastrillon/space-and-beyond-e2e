package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.model.Traveller;
import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ProvideTravellerDetails implements Task {

    private final Traveller traveller;

    public ProvideTravellerDetails(Traveller traveller) {
        this.traveller = traveller;
    }

    public static ProvideTravellerDetails of(Traveller traveller) {
        return instrumented(ProvideTravellerDetails.class, traveller);
    }

    @Step("{0} provides the traveller details for #traveller")
    @Override
    public <T extends Actor> void performAs(T actor) {
        fill(actor, CheckoutPage.NAME_FIELD, traveller.name());
        fill(actor, CheckoutPage.EMAIL_FIELD, traveller.email());
        fill(actor, CheckoutPage.SSN_FIELD, traveller.socialSecurityNumber());
        fill(actor, CheckoutPage.PHONE_FIELD, traveller.phoneNumber());
        actor.attemptsTo(Click.on(CheckoutPage.PAGE_HEADING));
    }

    private void fill(Actor actor, Target field, String value) {
        actor.attemptsTo(
                Click.on(field),
                Enter.theValue(value).into(field).thenHit(Keys.TAB)
        );
    }
}
