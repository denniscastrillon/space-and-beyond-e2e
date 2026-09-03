package com.devco.spaceandbeyond.ui;

import net.serenitybdd.screenplay.targets.Target;

public class DestinationsGallery {

    public static final Target TRAVELLERS_SUMMARY = Target.the("travellers & dates summary")
            .locatedBy("//h3[contains(@class,'Gallery__headline-2')]");

    public static final Target LOAD_MORE_BUTTON = Target.the("LOAD MORE button")
            .locatedBy("//button[normalize-space()='Load more']");

    public static final Target DESTINATION_CARDS = Target.the("destination cards")
            .locatedBy("//div[contains(@class,'gallery-item')]");

    public static final Target VISIBLE_PRICE_TAGS = Target.the("visible destination price tags")
            .locatedBy("//div[contains(@class,'gallery-item')]//span[contains(@class,'price-tag')]");

    public static final Target PRICE_SLIDER = Target.the("price filter slider")
            .locatedBy("//div[contains(@class,'price-filter-slider')]");

    public static final Target PRICE_SLIDER_KNOB = Target.the("price filter slider knob")
            .locatedBy("//div[contains(@class,'price-filter-slider')]//div[contains(@class,'knob')]");

    public static final Target PRICE_SLIDER_TRACK = Target.the("price filter slider track")
            .locatedBy("//div[contains(@class,'price-filter-slider')]//div[contains(@class,'container')]");

    public static final Target PRICE_SLIDER_VALUE = Target.the("price filter slider value")
            .locatedBy("//div[contains(@class,'price-filter-slider')]//input");

    public static Target destinationCard(String name) {
        return Target.the("'" + name + "' card")
                .locatedBy("//div[contains(@class,'gallery-item')][.//h5[normalize-space()='" + name + "']]");
    }

    public static Target bookButtonFor(String destination) {
        return Target.the("BOOK button for '" + destination + "'")
                .locatedBy("//div[contains(@class,'gallery-item')][.//h5[normalize-space()='" + destination + "']]" +
                        "//button[normalize-space()='Book']");
    }

    private DestinationsGallery() {
    }
}
