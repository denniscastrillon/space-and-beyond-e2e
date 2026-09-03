package com.devco.spaceandbeyond.ui;

import net.serenitybdd.screenplay.targets.Target;

public class DatePickerDialog {

    private static final String ACTIVE_DIALOG =
            "//*[contains(@class,'dialog') and contains(@class,'active')]";

    public static final Target MONTH_TITLE = Target.the("calendar month title")
            .locatedBy(ACTIVE_DIALOG + "//span[contains(@class,'title')]");

    public static final Target NEXT_MONTH = Target.the("next month arrow")
            .locatedBy(ACTIVE_DIALOG + "//button[.//*[normalize-space()='chevron_right']]");

    public static final Target OK_BUTTON = Target.the("calendar OK button")
            .locatedBy(ACTIVE_DIALOG + "//button[normalize-space()='Ok']");

    public static Target dayNumbered(int day) {
        return Target.the("day " + day)
                .locatedBy(ACTIVE_DIALOG +
                        "//div[contains(@class,'day___') and not(contains(@class,'disabled'))]" +
                        "[.//span[normalize-space()='" + day + "']]");
    }

    private DatePickerDialog() {
    }
}
