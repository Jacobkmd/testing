package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
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
public class EnhetstestingKundeController {

    @InjectMocks
    private AdminKundeController adminKundeController;

    @Mock
    private AdminRepository repository;

    @Mock
    private Sikkerhet sjekk;


     @Test
    public void hentAlle_loggetInn() {
         List<Kunde> alleKunder = new ArrayList<>();
         Kunde kunde1 = new Kunde("01010110523",
                 "Marius", "Lerøy", "Askerveien 22", "3270",
                 "Asker", "98567632", "HeiHei");
         Kunde kunde2 = new Kunde("02058740889",
                 "Henriette", "Magnussen", "Osloveien 19", "0880",
                 "Oslo","99345467","Heipådeg");
         alleKunder.add(kunde1);
         alleKunder.add(kunde2);

         when(sjekk.loggetInn()).thenReturn("02048840995");
         when(repository.hentAlleKunder()).thenReturn(alleKunder);

         List<Kunde> resultat = adminKundeController.hentAlle();

         assertEquals(alleKunder, resultat);
     }

     @Test
     public void hentAlle_ikkeLoggetInn() {
         when(sjekk.loggetInn()).thenReturn(null);

         List<Kunde> resultat = adminKundeController.hentAlle();

         assertNull(resultat);
     }

     @Test
    public void lagreKunde_loggetInn() {
         Kunde innKunde = new Kunde("02058740889",
                 "Henriette", "Magnussen", "Osloveien 19", "0880",
                 "Oslo","99345467","Heipådeg");

         when(sjekk.loggetInn()).thenReturn("0456789906");
         when(repository.registrerKunde(innKunde)).thenReturn("OK");

         String resultat = adminKundeController.lagreKunde(innKunde);

         assertEquals("OK", resultat);

     }

     @Test
    public void lagreKunde_ikkeLoggetInn() {
         Kunde innKunde = new Kunde("02058740889",
                 "Henriette", "Magnussen", "Osloveien 19", "0880",
                 "Oslo","99345467","Heipådeg");
         when(sjekk.loggetInn()).thenReturn(null);

         String resultat = adminKundeController.lagreKunde(innKunde);

         assertEquals("Ikke logget inn", resultat);
     }


}
