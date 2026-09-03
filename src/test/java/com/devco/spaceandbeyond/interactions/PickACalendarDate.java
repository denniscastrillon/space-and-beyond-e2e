package com.devco.spaceandbeyond.interactions;

import com.devco.spaceandbeyond.ui.DatePickerDialog;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotVisible;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PickACalendarDate implements Interaction {

    private static final DateTimeFormatter MONTH_YEAR =
            DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH);
    private static final int MAX_MONTH_HOPS = 24;

    private final Target dateField;
    private final LocalDate date;

    public PickACalendarDate(Target dateField, LocalDate date) {
        this.dateField = dateField;
        this.date = date;
    }

    public static PickACalendarDate of(Target dateField, LocalDate date) {
        return instrumented(PickACalendarDate.class, dateField, date);
    }

    @Step("{0} picks #date in the #dateField calendar")
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(dateField),
                WaitUntil.the(DatePickerDialog.OK_BUTTON, isVisible()).forNoMoreThan(8).seconds()
        );

        String wanted = date.format(MONTH_YEAR);
        for (int hop = 0; hop < MAX_MONTH_HOPS; hop++) {
            String shown = DatePickerDialog.MONTH_TITLE.resolveFor(actor).getText().trim();
            if (shown.equalsIgnoreCase(wanted)) {
                break;
            }
            actor.attemptsTo(Click.on(DatePickerDialog.NEXT_MONTH));
        }

        actor.attemptsTo(
                Click.on(DatePickerDialog.dayNumbered(date.getDayOfMonth())),
                Click.on(DatePickerDialog.OK_BUTTON),
                WaitUntil.the(DatePickerDialog.OK_BUTTON, isNotVisible()).forNoMoreThan(8).seconds()
        );
    }
}
