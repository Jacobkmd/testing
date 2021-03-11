package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentTransaksjoner() {
        Transaksjon enTranskaksjon = new Transaksjon(4, "enTransaksjon",
                4500.00, "01.01222", "hei", "1", "01010110523");
        Transaksjon enTranskaksjon2 = new Transaksjon(5, "enTransaksjon",
                4500.00, "01.01222", "hei", "1", "01010110523");
        List<Transaksjon> liste = new ArrayList<Transaksjon>();
        liste.add(enTranskaksjon);
        liste.add(enTranskaksjon2);

        Konto enKonto = new Konto("01010110523", "01010110523", 1500,
                "lønnskonto", "nok", liste);
        when(sjekk.loggetInn()).thenReturn("01010110523");
        when(repository.hentTransaksjoner(anyString(), anyString(), anyString())).thenReturn(enKonto);
        Konto resultat = bankController.hentTransaksjoner("456", "1545", "4545");
        assertEquals(enKonto.getTransaksjoner(), resultat.getTransaksjoner());
    }

    @Test
    public void test_hentTransaksjoner() {
        //arrange

        //act
        Konto resultat = bankController.hentTransaksjoner("456", "1545", "4545");

        //assert
        assertNull(resultat);
        verify(repository, never()).hentTransaksjoner(any(), any(), any());
    }

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn() {
        // arrange
        List<Konto> saldi = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        saldi.add(konto1);
        saldi.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentSaldi(anyString())).thenReturn(saldi);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertEquals(saldi, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentSaldi();

        // assert
        assertNull(resultat);
    }


    @Test
    public void hentKonti_LoggetInn() {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn() {
        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }

    @Test
    public void endreKundeInfo() {
        //arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.endreKundeInfo(enKunde)).thenReturn("OK");

        // act
        String resultat = bankController.endre(enKunde);

        // assert
        assertEquals("OK", resultat);
    }

    @Test
    public void test_regBetaling() {
        //arrange
        Transaksjon transaksjon = new Transaksjon();

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.registrerBetaling(any())).thenReturn("OK");

        //act
        String resultat = bankController.registrerBetaling(transaksjon);

        //assert
        assertEquals("OK", resultat);

        verify(repository).registrerBetaling(transaksjon);
    }

    @Test
    public void test_regBetaling2() {
        //arrange
        Transaksjon transaksjon = new Transaksjon();

        //act
        String resultat = bankController.registrerBetaling(transaksjon);

        //assert
        assertNull(resultat);

        verify(repository, never()).registrerBetaling(transaksjon);
    }

    @Test
    public void endreKundeInfo_IkkeloggetInn() {

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }


    @Test
    public void hentBetalinger_loggetInn() {
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "enBetaling", 4000.00,
                "01.013333", "heisann", "1", "01010110523");
        Transaksjon transaksjon2 = new Transaksjon(2, "enBetaling2", 5000.00,
                "01.014444", "Hello", "2", "0204667899");
        betalinger.add(transaksjon1);
        betalinger.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("03456776655");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger);

        List<Transaksjon> Resultat = bankController.hentBetalinger();

        assertEquals(betalinger, Resultat);

    }

    @Test
    public void hentBetalinger_ikkeLoggetInn() {

        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> Resultat = bankController.hentBetalinger();

        assertNull(Resultat);
    }

    @Test
    public void utforBetaling_loggetinn() {
        List<Transaksjon> betalinger = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(1, "enBetaling", 4000.00,
                "01.013333", "heisann", "1", "01010110523");
        betalinger.add(transaksjon1);


        when(sjekk.loggetInn()).thenReturn("03456776655");
        when(repository.utforBetaling(anyInt())).thenReturn("OK");
        when(repository.hentBetalinger(anyString())).thenReturn(betalinger); // Måtte huske å sette hentbetalinger til å returnere betalinger.


        List<Transaksjon> resultat = bankController.utforBetaling(1); //samme hvilket nummer man bruker her.

        assertEquals(betalinger, resultat);


    }

    @Test
    public void utforBetaling_ikkeLoggetInn() {
        when(sjekk.loggetInn()).thenReturn(null);

        List<Transaksjon> Resultat = bankController.utforBetaling(1);

        assertNull(Resultat);
    }

}

