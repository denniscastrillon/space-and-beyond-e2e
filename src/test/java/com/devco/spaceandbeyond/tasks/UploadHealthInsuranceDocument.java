package com.devco.spaceandbeyond.tasks;

import com.devco.spaceandbeyond.ui.CheckoutPage;
import com.devco.spaceandbeyond.util.TestResources;
import java.nio.file.Path;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Upload;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class UploadHealthInsuranceDocument implements Task {

    private final String resourcePath;

    public UploadHealthInsuranceDocument(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public static UploadHealthInsuranceDocument fromResource(String resourcePath) {
        return instrumented(UploadHealthInsuranceDocument.class, resourcePath);
    }

    @Step("{0} uploads the health-insurance document (#resourcePath)")
    @Override
    public <T extends Actor> void performAs(T actor) {
        Path file = TestResources.fileOnClasspath(resourcePath);
        actor.attemptsTo(
                Upload.theFile(file).to(CheckoutPage.FILE_INPUT),
                WaitUntil.the(CheckoutPage.UPLOADED_FILE_PREVIEW, isVisible()).forNoMoreThan(10).seconds()
        );
    }
}
