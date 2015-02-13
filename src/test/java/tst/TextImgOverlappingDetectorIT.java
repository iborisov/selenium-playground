package tst;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class TextImgOverlappingDetectorIT {
    private WebDriver webDriver;
    private static final File CURR_DIR = new File("./");

    private static String getFileUrl(final String relativeFilename) {
        return "file:///" + CURR_DIR.getAbsolutePath() + relativeFilename;
    }

    @Before
    public void before() {
        webDriver = new FirefoxDriver();
    }

    @After
    public void after() {
        webDriver.quit();
    }

    @Test
    public void testGood() throws Exception {
        final String urlToCheck = getFileUrl("/src/test/resources/test_good.html");
        final int result = TextImgOverlappingDetector.detect(webDriver, urlToCheck);

        assertEquals(0, result);
    }

    @Test
    public void testBad() throws Exception {
        final String urlToCheck = getFileUrl("/src/test/resources/test_bad.html");
        final int result = TextImgOverlappingDetector.detect(webDriver, urlToCheck);

        assertEquals(1, result);
    }
}
