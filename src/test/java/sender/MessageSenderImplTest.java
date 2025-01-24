package sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MessageSenderImplTest {
    LocalizationServiceImpl localizationService;
    GeoService geoService;
    MessageSenderImpl messageSender;
    HashMap<String, String> headers;

    @BeforeEach
    public void setUp() {
        localizationService = Mockito.mock(LocalizationServiceImpl.class);
        geoService = Mockito.mock(GeoServiceImpl.class);
        messageSender = new MessageSenderImpl(geoService, localizationService);
        headers = new HashMap<>();
    }

    @Test
    public void testSendWithMoscowIp() {
        // arrange
        String ip = "172.0.32.11";
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Mockito.when(geoService.byIp(ip)).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String expected = "Добро пожаловать";
        // act
        String actual = messageSender.send(headers);
        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSendWithUsaIp() {
        // arrange
        String ip = "96.44.183.149";
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        Mockito.when(geoService.byIp(ip)).thenReturn(new Location("New York", Country.USA, "10th Avenue", 32));
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String expected = "Welcome";
        // act
        String actual = messageSender.send(headers);
        // assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testSendWithEmptyHeaders() {
        // Arrange
        Mockito.when(localizationService.locale(any(Country.class))).thenReturn("Welcome");
        Mockito.when(geoService.byIp(null)).thenReturn(null);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, null);
        Class<NullPointerException> expected = NullPointerException.class;

        // Act
        Executable actual = () -> messageSender.send(null);

        // Assert
        Assertions.assertThrows(expected, actual);
    }

    @Test
    public void testSendWithUnknownIp() {
        // Arrange
        String ip = "192.168.1.1"; // An unknown IP
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        Mockito.when(geoService.byIp(ip)).thenReturn(new Location(null, Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        String expected = "Welcome";

        // Act
        String result = messageSender.send(headers);

        // Assert
        Assertions.assertEquals(expected, result);
    }
}
