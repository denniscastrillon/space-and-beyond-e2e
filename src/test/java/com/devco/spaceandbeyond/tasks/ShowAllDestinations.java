package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.DestinationsGallery;
import java.time.Duration;
import java.util.List;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ShowAllDestinations implements Task {

    private static final int MAX_CLICKS = 10;
    private static final Duration RENDER_TIMEOUT = Duration.ofSeconds(5);

    public static ShowAllDestinations byPressingLoadMore() {
        return instrumented(ShowAllDestinations.class);
    }

    @Step("{0} loads every destination")
    @Override
    public <T extends Actor> void performAs(T actor) {
        WebDriverWait wait = new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), RENDER_TIMEOUT);

        for (int click = 0; click < MAX_CLICKS && loadMoreIsActionable(actor); click++) {
            int countBefore = cardCount(actor);
            actor.attemptsTo(
                    Scroll.to(DestinationsGallery.LOAD_MORE_BUTTON),
                    Click.on(DestinationsGallery.LOAD_MORE_BUTTON)
            );
            wait.until(driver -> cardCount(actor) > countBefore || !loadMoreIsActionable(actor));
        }
    }

    private int cardCount(Actor actor) {
        return DestinationsGallery.DESTINATION_CARDS.resolveAllFor(actor).size();
    }

    private boolean loadMoreIsActionable(Actor actor) {
        List<WebElementFacade> buttons = DestinationsGallery.LOAD_MORE_BUTTON.resolveAllFor(actor);
        return !buttons.isEmpty()
                && buttons.get(0).isCurrentlyVisible()
                && buttons.get(0).isEnabled();
    }
}
