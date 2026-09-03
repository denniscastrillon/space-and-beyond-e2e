package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import com.devco.spaceandbeyond.ui.DestinationsGallery;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ChooseTheDestination implements Task {

    private final String destination;

    public ChooseTheDestination(String destination) {
        this.destination = destination;
    }

    public static ChooseTheDestination named(String destination) {
        return instrumented(ChooseTheDestination.class, destination);
    }

    @Step("{0} books the '#destination' destination")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                WaitUntil.the(DestinationsGallery.destinationCard(destination), isVisible())
                        .forNoMoreThan(10).seconds(),
                Scroll.to(DestinationsGallery.destinationCard(destination)),
                Click.on(DestinationsGallery.bookButtonFor(destination)),
                WaitUntil.the(CheckoutPage.NAME_FIELD, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
