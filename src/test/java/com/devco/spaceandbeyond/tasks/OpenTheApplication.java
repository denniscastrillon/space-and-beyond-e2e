package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.SpaceAndBeyondHomePage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class OpenTheApplication implements Task {

    public static OpenTheApplication landingPage() {
        return instrumented(OpenTheApplication.class);
    }

    @Step("{0} opens the Space & Beyond application")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.browserOn().the(SpaceAndBeyondHomePage.class));
    }
}
