package com.devco.spaceandbeyond.interactions;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SelectFromReactDropdown implements Interaction {

    private final Target dropdown;
    private final Target optionTarget;

    public SelectFromReactDropdown(Target dropdown, Target optionTarget) {
        this.dropdown = dropdown;
        this.optionTarget = optionTarget;
    }

    public static SelectFromReactDropdown from(Target dropdown, Target optionTarget) {
        return instrumented(SelectFromReactDropdown.class, dropdown, optionTarget);
    }

    @Step("{0} selects #optionTarget from #dropdown")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(dropdown),
                WaitUntil.the(optionTarget, isVisible()).forNoMoreThan(8).seconds(),
                Click.on(optionTarget)
        );
    }
}
