package com.devco.spaceandbeyond.ui;

import net.serenitybdd.screenplay.targets.Target;

public class LandingPage {

    public static final Target DEPARTING_FIELD = Target.the("Departing date field")
            .locatedBy("//div[contains(@class,'input')][./label[normalize-space()='Departing']]//input");

    public static final Target RETURNING_FIELD = Target.the("Returning date field")
            .locatedBy("//div[contains(@class,'input')][./label[normalize-space()='Returning']]//input");

    public static final Target ADULTS_DROPDOWN = Target.the("Adults dropdown")
            .locatedBy("//div[contains(@class,'dropdown')][.//input[starts-with(@value,'Adults')]]");

    public static final Target CHILDREN_DROPDOWN = Target.the("Children dropdown")
            .locatedBy("//div[contains(@class,'dropdown')][.//input[starts-with(@value,'Children')]]");

    public static final Target SELECT_DESTINATION_BUTTON = Target.the("SELECT DESTINATION button")
            .locatedBy("//button[normalize-space()='Select Destination']");

    public static Target adultsOption(String number) {
        return Target.the("adults option '" + number + "'")
                .locatedBy("//div[contains(@class,'dropdown')][.//input[starts-with(@value,'Adults')]]" +
                        "//li[normalize-space()='" + number + "']");
    }

    public static Target childrenOption(String number) {
        return Target.the("children option '" + number + "'")
                .locatedBy("//div[contains(@class,'dropdown')][.//input[starts-with(@value,'Children')]]" +
                        "//li[normalize-space()='" + number + "']");
    }

    private LandingPage() {
    }
}
