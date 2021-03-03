package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
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
public class EnhetstestKontoController {

    @InjectMocks
    private AdminKontoController adminKontoController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;


    @Test
    public void testRegKonto() {
        //arrange
        Konto konto = new Konto();
        when(sjekk.loggetInn()).thenReturn("OK");
        when(repository.registrerKonto(konto)).thenReturn("Logget inn");

        //act
        String result = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("Logget inn", result);
        verify(repository).registrerKonto(konto);
    }

    @Test
    public void testFailRegKonto() {
        //arrange
        Konto konto = new Konto();
        when(repository.registrerKonto(konto)).thenReturn("Ikke innlogget");

        //act
        String result = adminKontoController.registrerKonto(konto);

        //assert
        assertEquals("Ikke innlogget", result);
        verify(repository, never()).registrerKonto(konto);
    }

    @Test
    public void testEndreKonto(){
        //arrange
        Konto konto = new Konto();
        when(sjekk.loggetInn()).thenReturn("OK");
        when(repository.endreKonto(konto)).thenReturn("OK");

        //act
        String result = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("OK", result);
        verify(repository).endreKonto(konto);
    }

    @Test
    public void testFailEndreKonto(){
        //arrange
        Konto konto = new Konto();
        when(repository.endreKonto(konto)).thenReturn("Ikke innlogget");

        //act
        String result = adminKontoController.endreKonto(konto);

        //assert
        assertEquals("Ikke innlogget", result);
        verify(repository, never()).endreKonto(konto);

    }

    @Test
    public void testSlettKonto(){
        //arrange

        when(sjekk.loggetInn()).thenReturn("OK");
        when(repository.slettKonto("12345678900")).thenReturn("kontonummer");

        //act
        String result = adminKontoController.slettKonto("12345678900");

        //assert
        assertEquals("kontonummer", result);
        verify(repository.slettKonto("12345678900"));
    }

    @Test
    public void testFailSlettKonto(){
        //arrange
        when(repository.slettKonto("12345678900")).thenReturn("Ikke innlogget");

        //act
        String result = adminKontoController.slettKonto("12345678900");

        //assert
        assertEquals("Ikke innlogget", result);
        verify(repository, never()).slettKonto("12345678900");

    }
}
