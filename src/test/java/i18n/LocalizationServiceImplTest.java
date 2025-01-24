package i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;
import java.util.stream.Stream;

public class LocalizationServiceImplTest {

    LocalizationServiceImpl localizationService;

    @BeforeEach
    public void setLocalizationService(){
        localizationService = new LocalizationServiceImpl();
    }

    public static Stream<Arguments> localeTest(){
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource
    public void localeTest(Country country, String expected){
        // act
        String actual = localizationService.locale(country);
        // assert
        Assertions.assertEquals(expected,actual);
    }
}
