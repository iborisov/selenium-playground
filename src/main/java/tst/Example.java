package tst;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Example {
    private static final Logger LOG = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) throws Exception {
        final WebDriver driver = new FirefoxDriver();

        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

        final File currDir = new File("./");
        final String htmlGood = "file:///" + currDir.getAbsolutePath() + "/src/test/resources/test_good.html";
        final String htmlBad = "file:///" + currDir.getAbsolutePath() + "/src/test/resources/test_bad.html";

//        TextImgOverlappingDetector.detect(driver, htmlGood);
//        TextImgOverlappingDetector.detect(driver, htmlBad);
        System.out.println(TextImgOverlappingDetector.detect(driver, "http://habrahabr.ru/"));

        driver.quit();
    }
}
