package geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {

    GeoServiceImpl geoService;
    public static final String LOCALHOST = "127.0.0.1";
    public static final String MOSCOW_IP = "172.0.32.11";
    public static final String NEW_YORK_IP = "96.44.183.149";

    @BeforeEach
    public void setUpGeoServiceImpl() {
        geoService = new GeoServiceImpl();
    }

    public static Stream<Arguments> testByIp() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, "10th Avenue", 32)),
                Arguments.of("172.16.0.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.183.200", new Location("New York", Country.USA, null, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("testByIp")
    public void testByIp(String ip, Location expected) {
        // act
        Location actual = geoService.byIp(ip);
        // assert
        Assertions.assertEquals(expected, actual);
    }
}
