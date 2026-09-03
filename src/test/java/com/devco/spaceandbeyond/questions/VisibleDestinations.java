package com.devco.spaceandbeyond.questions;

import com.devco.spaceandbeyond.ui.DestinationsGallery;
import java.util.List;
import net.serenitybdd.screenplay.Question;

public class VisibleDestinations {

    private VisibleDestinations() {
    }

    public static Question<List<Double>> prices() {
        return Question.about("the visible destination prices").answeredBy(actor ->
                DestinationsGallery.VISIBLE_PRICE_TAGS.resolveAllFor(actor).stream()
                        .map(element -> element.getText().replaceAll("[^0-9.]", ""))
                        .filter(text -> !text.isEmpty())
                        .map(Double::parseDouble)
                        .toList());
    }

    public static Question<Integer> count() {
        return Question.about("the number of visible destinations").answeredBy(actor ->
                DestinationsGallery.DESTINATION_CARDS.resolveAllFor(actor).size());
    }
}
