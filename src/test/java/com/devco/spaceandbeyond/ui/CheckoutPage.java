package com.devco.spaceandbeyond.ui;

import net.serenitybdd.screenplay.targets.Target;

public class CheckoutPage {

    private static String fieldByHint(String hint) {
        return "//div[contains(@class,'input')][.//span[contains(@class,'hint') and normalize-space()='" + hint + "']]//input";
    }

    public static final Target NAME_FIELD = Target.the("Name field").locatedBy(fieldByHint("Name"));
    public static final Target EMAIL_FIELD = Target.the("Email Address field").locatedBy(fieldByHint("Email Address"));
    public static final Target SSN_FIELD = Target.the("Social Security Number field").locatedBy(fieldByHint("Social Security Number"));
    public static final Target PHONE_FIELD = Target.the("Phone Number field").locatedBy(fieldByHint("Phone Number"));
    public static final Target PROMO_CODE_FIELD = Target.the("promo code field").locatedBy(fieldByHint("I have a promo code"));

    public static final Target FILE_INPUT = Target.the("health insurance file input")
            .locatedBy("//div[contains(@class,'dropzone')]//input[@type='file']");

    public static final Target UPLOADED_FILE_PREVIEW = Target.the("uploaded document preview")
            .locatedBy("//div[contains(@class,'dropzone')]//img");

    public static final Target APPLY_PROMO_BUTTON = Target.the("APPLY promo button")
            .locatedBy("//button[contains(@class,'apply-button')]");

    public static final Target TERMS_AND_CONDITIONS_CHECKBOX = Target.the("I agree to the terms and conditions checkbox")
            .locatedBy("//label[.//span[contains(normalize-space(.),'I agree to the terms and conditions')]]");

    public static final Target TERMS_CHECKBOX_INPUT = Target.the("terms checkbox input")
            .locatedBy("//label[.//span[contains(normalize-space(.),'I agree to the terms and conditions')]]//input[@type='checkbox']");

    public static final Target PAY_NOW_BUTTON = Target.the("PAY NOW button")
            .locatedBy("//button[contains(@class,'pay-button')]");

    public static final Target ORDER_TOTAL = Target.the("order total")
            .locatedBy("//div[contains(@class,'price')]//strong");

    public static final Target PAGE_HEADING = Target.the("CHECKOUT heading")
            .locatedBy("//h1");

    public static final Target BOOKING_CONFIRMATION_MESSAGE = Target.the("'Destination Booked' confirmation message")
            .locatedBy("//*[contains(normalize-space(.),'Destination Booked')]");

    private CheckoutPage() {
    }
}
