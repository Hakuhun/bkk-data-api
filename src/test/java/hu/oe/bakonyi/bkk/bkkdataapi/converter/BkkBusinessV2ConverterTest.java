package hu.oe.bakonyi.bkk.bkkdataapi.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkBusinessDataV2;
import hu.oe.bakonyi.bkk.bkkdataapi.model.BkkData;
import hu.oe.bakonyi.bkk.bkkdataapi.model.weather.Model200;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class BkkBusinessV2ConverterTest {

    @Test
    public void test_converter_ConvertsOk_OnGoodData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        BkkBusinessDataV2Converter converter = new BkkBusinessDataV2Converter();
        Model200 weather = mapper.readValue(ResourceUtils.getFile("classpath:model200_ok.json"), Model200.class);
        BkkData bkkData = mapper.readValue(ResourceUtils.getFile("classpath:bkkdata_single_ok.json"), BkkData.class);
        Instant lastUpdateTime = Instant.ofEpochSecond(1575928283);
        BkkBusinessDataV2 bkkBusinessDataV2 = converter.convert(bkkData, lastUpdateTime, weather);
        Assert.assertNotNull(bkkBusinessDataV2);
        Assert.assertEquals(bkkBusinessDataV2.getAlert(),0);
        Assert.assertEquals(bkkBusinessDataV2.getRouteId(), "BKK_0230");
        Assert.assertEquals(bkkBusinessDataV2.getTripId(), "BKK_C03830418");
        Assert.assertEquals(bkkBusinessDataV2.getStopId(), "BKK_009042");
        Assert.assertEquals(bkkBusinessDataV2.getMonth(), 12);
        Assert.assertEquals(bkkBusinessDataV2.getHour(), 22);
        Assert.assertEquals(bkkBusinessDataV2.getDayOfWeek(),1);
        Assert.assertEquals(bkkBusinessDataV2.getArrivalDiff(),1,0);
        Assert.assertEquals(bkkBusinessDataV2.getDepartureDiff(), 34,0);
        Assert.assertEquals(bkkBusinessDataV2.getLastUpdateTime().longValue(), 1575928283);
        Assert.assertEquals(bkkBusinessDataV2.getHumidity(), 100,0);
        Assert.assertEquals(bkkBusinessDataV2.getVisibility(), 200,0);
        Assert.assertEquals(bkkBusinessDataV2.getRain(), 0,0);
        Assert.assertEquals(bkkBusinessDataV2.getSnow(), 0,0);
    }

    @Test
    public void test_converter_ThrowsException_OnMissingData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        BkkBusinessDataV2Converter converter = new BkkBusinessDataV2Converter();
        Model200 weather = mapper.readValue(ResourceUtils.getFile("classpath:model200_ok.json"), Model200.class);
        BkkData bkkData = mapper.readValue(ResourceUtils.getFile("classpath:bkkdata_single_nok_missingrote.json"), BkkData.class);
        Instant lastUpdateTime = Instant.ofEpochSecond(1575928283);
        assertThrows(ConversionFailedException.class, ()->converter.convert(bkkData, lastUpdateTime, weather));
        Model200 errroWeather = mapper.readValue(ResourceUtils.getFile("classpath:model200_nok.json"), Model200.class);
        assertThrows(ConversionFailedException.class, ()->converter.convert(bkkData, lastUpdateTime, errroWeather));
        assertThrows(ConversionFailedException.class, ()->converter.convert(bkkData, null, weather));
    }

    @Test
    public void test_getActualHour_calculatesCorrect_OnData(){
        BkkBusinessDataV2Converter converter = new BkkBusinessDataV2Converter();
        Instant onePm = Instant.ofEpochSecond(1575894659);
        int actualHour = converter.getActualHour(10, 1575892859, 1575894659, onePm);
        Assert.assertEquals(13, actualHour);

        //Teszt arra az esetre, ha a megálló kezdőállomás lenne.
        long arrival = 1575892859;
        long departure =1575900059;
        actualHour = converter.getActualHour(0, departure,arrival,onePm);
        Assert.assertEquals(Instant.ofEpochSecond(departure).atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.HOUR_OF_DAY), actualHour);

        //Teszt arra az esetre, ha a megálló nem kezdő megálló
        actualHour = converter.getActualHour(100, departure, arrival , onePm);
        Assert.assertEquals(Instant.ofEpochSecond(arrival).atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.HOUR_OF_DAY), actualHour);

        //Teszt arra az esetre, ha a szerver idő éjféli időpont lenne, de a mégis éjfél utáni a kalkulált idő.
        Instant midnight = Instant.ofEpochSecond(1575849659);
        Instant lastUpdate = Instant.ofEpochSecond(1575849359);
        actualHour = converter.getActualHour(0, lastUpdate.getEpochSecond(), midnight.getEpochSecond() , onePm);
        Assert.assertEquals(lastUpdate.atZone(ZoneId.of("Europe/Budapest")).get(ChronoField.HOUR_OF_DAY), actualHour);
    }

}
