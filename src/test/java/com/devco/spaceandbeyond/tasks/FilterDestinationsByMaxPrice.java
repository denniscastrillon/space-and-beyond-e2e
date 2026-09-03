package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.interactions.MoveThePriceSliderTo;
import com.devco.spaceandbeyond.ui.DestinationsGallery;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Scroll;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class FilterDestinationsByMaxPrice implements Task {

    private final int maxPrice;

    public FilterDestinationsByMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public static FilterDestinationsByMaxPrice of(int maxPrice) {
        return instrumented(FilterDestinationsByMaxPrice.class, maxPrice);
    }

    @Step("{0} filters destinations to a maximum price of $#maxPrice")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Scroll.to(DestinationsGallery.PRICE_SLIDER),
                MoveThePriceSliderTo.aMaximumOf(maxPrice)
        );
    }
}
