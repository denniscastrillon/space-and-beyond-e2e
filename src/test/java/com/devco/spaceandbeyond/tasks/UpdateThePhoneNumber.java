package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.Keys;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class UpdateThePhoneNumber implements Task {

    private final String phoneNumber;

    public UpdateThePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static UpdateThePhoneNumber to(String phoneNumber) {
        return instrumented(UpdateThePhoneNumber.class, phoneNumber);
    }

    @Step("{0} corrects the phone number to '#phoneNumber'")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(CheckoutPage.PHONE_FIELD),
                Clear.field(CheckoutPage.PHONE_FIELD),
                Enter.theValue(phoneNumber).into(CheckoutPage.PHONE_FIELD).thenHit(Keys.TAB),
                Click.on(CheckoutPage.PAGE_HEADING)
        );
    }
}
