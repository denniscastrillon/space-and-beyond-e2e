package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.interactions.PickACalendarDate;
import com.devco.spaceandbeyond.interactions.SelectFromReactDropdown;
import com.devco.spaceandbeyond.model.TravelSearch;
import com.devco.spaceandbeyond.ui.LandingPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class EnterTheTripDetails implements Task {

    private final TravelSearch search;

    public EnterTheTripDetails(TravelSearch search) {
        this.search = search;
    }

    public static EnterTheTripDetails from(TravelSearch search) {
        return instrumented(EnterTheTripDetails.class, search);
    }

    @Step("{0} enters the trip details (#search)")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                PickACalendarDate.of(LandingPage.DEPARTING_FIELD, search.departureDate()),
                PickACalendarDate.of(LandingPage.RETURNING_FIELD, search.returnDate()),
                SelectFromReactDropdown.from(
                        LandingPage.ADULTS_DROPDOWN,
                        LandingPage.adultsOption(String.valueOf(search.adults())))
        );

        if (search.children() > 0) {
            actor.attemptsTo(
                    SelectFromReactDropdown.from(
                            LandingPage.CHILDREN_DROPDOWN,
                            LandingPage.childrenOption(String.valueOf(search.children())))
            );
        }
    }
}
