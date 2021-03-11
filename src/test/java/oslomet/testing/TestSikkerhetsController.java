package oslomet.testing;



import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.mock.web.MockHttpSession;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestSikkerhetsController {

    @InjectMocks
    private Sikkerhet sikkerhet;

    @Mock
    private BankRepository repository;

    @Mock
    MockHttpSession session;

    @Before
    public void initSession() {
        Map<String, Object> attributes = new HashMap<>();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                return attributes.get(key);
            }
        }).when(session).getAttribute(anyString());

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                String key = (String) invocation.getArguments()[0];
                Object value = invocation.getArguments()[1];
                attributes.put(key, value);
                return null;
            }
        }).when(session).setAttribute(anyString(), any());
    }

    @Test
    public void sjekkLoggetInn() {
        when(repository.sjekkLoggInn(anyString(), anyString())).thenReturn("OK");

        String resultat = sikkerhet.sjekkLoggInn("01234566778", "Heipådeg");

        assertEquals("OK", resultat);
    }

    @Test
    public void loggetInnFeilPersNrOrPswrd(){
        when(repository.sjekkLoggInn(anyString(),anyString())).thenReturn("Feil i personnummer eller passord");
        String resultat = sikkerhet.sjekkLoggInn("01234566778","Heipådeg");

        assertEquals("Feil i personnummer eller passord",resultat);
    }

    @Test
    public void test_LoggetInn() {
        session.setAttribute("Innlogget", "01234566778");

        String resultat = sikkerhet.loggetInn();

        assertEquals("01234566778", resultat);
    }
//heihei
    @Test
    public void testFail_LoggetInn() {
        session.setAttribute(null, null);

        String resultat = sikkerhet.loggetInn();

        assertNull(resultat);
    }

    @Test
    public void test_LoggInnAdmin() {

        String resultat = sikkerhet.loggInnAdmin("Admin", "Admin");

        assertEquals("Logget inn", resultat);
    }

    @Test
    public void test_LoggInnAdminFail() {

        String resultat = sikkerhet.loggInnAdmin("IkkeAdmin", "IkkeAdmin");

        assertEquals("Ikke logget inn", resultat);
    }
}
