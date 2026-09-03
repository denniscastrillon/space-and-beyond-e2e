package com.devco.spaceandbeyond.interactions;

import com.devco.spaceandbeyond.ui.DestinationsGallery;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static net.serenitybdd.screenplay.Tasks.instrumented;

// Drags the react-toolbox slider knob by geometry: knobLeft = trackLeft + fraction * trackWidth.
// Aims slightly under the target so no pricier destination slips through the filter.
public class MoveThePriceSliderTo implements Interaction {

    public static final int MIN_PRICE = 100;
    public static final int MAX_PRICE = 1800;

    private static final int UNDERSHOOT = 10;
    private static final int PRICE_TOLERANCE = 5;
    private static final int MAX_NUDGES = 8;

    private final int targetMaxPrice;

    public MoveThePriceSliderTo(int targetMaxPrice) {
        this.targetMaxPrice = targetMaxPrice;
    }

    public static MoveThePriceSliderTo aMaximumOf(int targetMaxPrice) {
        return instrumented(MoveThePriceSliderTo.class, targetMaxPrice);
    }

    @Step("{0} moves the price slider to a maximum of $#targetMaxPrice")
    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();

        WebElementFacade track = DestinationsGallery.PRICE_SLIDER_TRACK.resolveFor(actor);
        WebElement knob = DestinationsGallery.PRICE_SLIDER_KNOB.resolveFor(actor);

        int trackLeft = track.getLocation().getX();
        int trackWidth = track.getSize().getWidth();
        int viewportWidth = ((Number) ((JavascriptExecutor) driver)
                .executeScript("return window.innerWidth")).intValue();

        boolean toTheMax = targetMaxPrice >= MAX_PRICE;
        int aim = toTheMax ? MAX_PRICE
                : Math.max(MIN_PRICE, Math.min(MAX_PRICE, targetMaxPrice - UNDERSHOOT));

        for (int attempt = 0; attempt <= MAX_NUDGES; attempt++) {
            int current = currentSliderValue(actor);
            if (Math.abs(current - aim) <= PRICE_TOLERANCE) {
                return;
            }

            int desiredKnobLeft;
            if (toTheMax) {
                desiredKnobLeft = Math.min(trackLeft + trackWidth + 30, viewportWidth - 8);
            } else {
                double fraction = (aim - MIN_PRICE) / (double) (MAX_PRICE - MIN_PRICE);
                desiredKnobLeft = (int) Math.round(trackLeft + fraction * trackWidth);
            }

            int dx = desiredKnobLeft - knob.getLocation().getX();
            if (dx == 0) {
                dx = current < aim ? 2 : -2;
            }

            new Actions(driver)
                    .clickAndHold(knob)
                    .moveByOffset(dx, 0)
                    .release()
                    .perform();
        }
    }

    private int currentSliderValue(Actor actor) {
        String raw = DestinationsGallery.PRICE_SLIDER_VALUE.resolveFor(actor).getValue();
        String digits = raw == null ? "" : raw.replaceAll("[^0-9]", "");
        return digits.isEmpty() ? MAX_PRICE : Integer.parseInt(digits);
    }
}
