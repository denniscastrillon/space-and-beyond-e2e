package com.devco.spaceandbeyond.stepdefinitions;

import com.devco.spaceandbeyond.model.TravelSearch;
import com.devco.spaceandbeyond.model.Traveller;
import com.devco.spaceandbeyond.questions.BookingConfirmation;
import com.devco.spaceandbeyond.questions.CheckoutForm;
import com.devco.spaceandbeyond.questions.OrderSummary;
import com.devco.spaceandbeyond.questions.VisibleDestinations;
import com.devco.spaceandbeyond.tasks.AcceptTheTermsAndConditions;
import com.devco.spaceandbeyond.tasks.ApplyThePromotionalCode;
import com.devco.spaceandbeyond.tasks.ChooseTheDestination;
import com.devco.spaceandbeyond.tasks.EnterTheTripDetails;
import com.devco.spaceandbeyond.tasks.FilterDestinationsByMaxPrice;
import com.devco.spaceandbeyond.tasks.OpenTheApplication;
import com.devco.spaceandbeyond.tasks.PayNow;
import com.devco.spaceandbeyond.tasks.PressSelectDestination;
import com.devco.spaceandbeyond.tasks.ProvideTravellerDetails;
import com.devco.spaceandbeyond.tasks.ShowAllDestinations;
import com.devco.spaceandbeyond.tasks.UpdateThePhoneNumber;
import com.devco.spaceandbeyond.tasks.UploadHealthInsuranceDocument;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.List;
import java.util.Map;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.waits.Wait;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.hamcrest.Matchers.is;

public class AgendarViajeStepDefinitions {

    private static final TravelSearch A_DEFAULT_TRIP = new TravelSearch(7, 14, 2, 1);

    private double totalBeforePromo;
    private TravelSearch lastSearch = A_DEFAULT_TRIP;

    private Actor paula() {
        return OnStage.theActorInTheSpotlight();
    }

    @Given("que Paula quiere agendar un viaje espacial en Space & Beyond")
    public void paulaQuiereAgendarUnViaje() {
        OnStage.theActorCalled("Paula").attemptsTo(OpenTheApplication.landingPage());
    }

    @Given("que Paula ha buscado un viaje y ve todos los destinos")
    public void paulaHaBuscadoUnViaje() {
        paula().attemptsTo(
                EnterTheTripDetails.from(A_DEFAULT_TRIP),
                PressSelectDestination.toSeeTheDestinations(),
                ShowAllDestinations.byPressingLoadMore()
        );
    }

    @When("busca un viaje con los datos:")
    public void buscaUnViajeConLosDatos(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().get(0);
        lastSearch = TravelSearch.from(row);
        paula().attemptsTo(EnterTheTripDetails.from(lastSearch));
    }

    @When("presiona SELECT DESTINATION")
    public void presionaSelectDestination() {
        paula().attemptsTo(PressSelectDestination.toSeeTheDestinations());
    }

    @When("presiona LOAD MORE para mostrar todos los destinos")
    public void presionaLoadMore() {
        paula().attemptsTo(ShowAllDestinations.byPressingLoadMore());
    }

    @When("filtra los destinos a un precio máximo de {int} dólares")
    public void filtraLosDestinos(int precioMaximo) {
        paula().attemptsTo(FilterDestinationsByMaxPrice.of(precioMaximo));
    }

    @Then("todos los destinos visibles cuestan {int} dólares o menos")
    public void todosLosDestinosVisiblesCuestanMenosDe(int precioMaximo) {
        List<Double> precios = paula().asksFor(VisibleDestinations.prices());
        assertThat(precios)
                .as("precios de los destinos visibles")
                .allMatch(precio -> precio <= precioMaximo);
    }

    @Then("al restaurar el filtro a {int} dólares se vuelven a mostrar {int} destinos")
    public void alRestaurarElFiltro(int precioMaximo, int destinosEsperados) {
        paula().attemptsTo(FilterDestinationsByMaxPrice.of(precioMaximo));
        assertThat(paula().asksFor(VisibleDestinations.count()))
                .as("número de destinos visibles tras restaurar el filtro")
                .isEqualTo(destinosEsperados);
    }

    @When("elige el destino {string}")
    public void eligeElDestino(String destino) {
        paula().attemptsTo(ChooseTheDestination.named(destino));
    }

    @Then("el total del pedido corresponde al precio de {string} multiplicado por la cantidad de viajeros")
    public void elTotalCorrespondeAlPrecioPorViajeros(String destino) {
        double precioPorViajero = paula().asksFor(VisibleDestinations.priceOf(destino));
        int viajeros = lastSearch.adults() + lastSearch.children();
        assertThat(paula().asksFor(OrderSummary.total()))
                .as("total del pedido (%.2f x %d viajeros)", precioPorViajero, viajeros)
                .isCloseTo(precioPorViajero * viajeros, within(0.01));
    }

    @When("diligencia los datos del viajero:")
    public void diligenciaLosDatosDelViajero(DataTable dataTable) {
        Traveller viajero = Traveller.from(dataTable.asMaps().get(0));
        paula().attemptsTo(ProvideTravellerDetails.of(viajero));
    }

    @When("carga el documento de salud {string}")
    public void cargaElDocumentoDeSalud(String rutaRecurso) {
        paula().attemptsTo(UploadHealthInsuranceDocument.fromResource(rutaRecurso));
    }

    @When("aplica el código promocional {string}")
    public void aplicaElCodigoPromocional(String codigo) {
        totalBeforePromo = paula().asksFor(OrderSummary.total());
        paula().attemptsTo(ApplyThePromotionalCode.of(codigo));
    }

    @When("acepta los términos y condiciones")
    public void aceptaLosTerminosYCondiciones() {
        paula().attemptsTo(AcceptTheTermsAndConditions.checkbox());
    }

    @When("presiona PAY NOW")
    public void presionaPayNow() {
        paula().attemptsTo(PayNow.toCompleteTheBooking());
    }

    @When("corrige el teléfono del viajero a {string}")
    public void corrigeElTelefono(String telefono) {
        paula().attemptsTo(UpdateThePhoneNumber.to(telefono));
    }

    @Then("el total del pedido disminuye respecto al valor previo al código promocional")
    public void elTotalDisminuye() {
        assertThat(paula().asksFor(OrderSummary.total()))
                .as("total tras aplicar el código promocional (antes era %.2f)", totalBeforePromo)
                .isLessThan(totalBeforePromo);
    }

    @Then("Paula ve el mensaje de confirmación {string}")
    public void paulaVeElMensajeDeConfirmacion(String mensajeEsperado) {
        assertThat(paula().asksFor(BookingConfirmation.message()))
                .as("mensaje de confirmación tras PAY NOW (defecto SPACE-001)")
                .contains(mensajeEsperado);
    }

    @Then("el resumen del viaje contiene {string}")
    public void elResumenDelViajeContiene(String textoEsperado) {
        assertThat(paula().asksFor(OrderSummary.travellersAndDatesSummary()))
                .as("resumen de viajeros y fechas")
                .contains(textoEsperado);
    }

    @Then("el botón PAY NOW permanece deshabilitado")
    public void elBotonPayNowPermaneceDeshabilitado() {
        assertThat(paula().asksFor(CheckoutForm.payNowIsEnabled()))
                .as("PAY NOW habilitado")
                .isFalse();
    }

    @Then("el botón PAY NOW queda habilitado")
    public void elBotonPayNowQuedaHabilitado() {
        paula().attemptsTo(
                Wait.until(CheckoutForm.payNowIsEnabled(), is(true)).forNoMoreThan(8).seconds()
        );
        assertThat(paula().asksFor(CheckoutForm.payNowIsEnabled()))
                .as("PAY NOW habilitado tras corregir el teléfono")
                .isTrue();
    }
}
