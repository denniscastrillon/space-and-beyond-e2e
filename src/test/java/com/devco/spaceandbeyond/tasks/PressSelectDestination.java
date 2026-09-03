package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.DestinationsGallery;
import com.devco.spaceandbeyond.ui.LandingPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PressSelectDestination implements Task {

    public static PressSelectDestination toSeeTheDestinations() {
        return instrumented(PressSelectDestination.class);
    }

    @Step("{0} presses SELECT DESTINATION")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(LandingPage.SELECT_DESTINATION_BUTTON),
                Click.on(LandingPage.SELECT_DESTINATION_BUTTON),
                WaitUntil.the(DestinationsGallery.TRAVELLERS_SUMMARY, isVisible()).forNoMoreThan(15).seconds(),
                WaitUntil.the(DestinationsGallery.DESTINATION_CARDS, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
