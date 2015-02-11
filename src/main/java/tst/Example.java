package tst;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Example {
    private static final Logger LOG = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) throws Exception {
        final String jsGetClientRectText = Resources.toString(
                Resources.getResource("clientRect.js.txt"), Charsets.UTF_8);

        final WebDriver driver = new FirefoxDriver();
        final JavascriptExecutor js = (JavascriptExecutor) driver;

        final File currDir = new File("./");
//        final String htmlPath = "file:///" + currDir.getAbsolutePath() + "/test1.html";
        final String htmlPath = "http://habrahabr.ru/";
        LOG.info("Opening {}...", htmlPath);
        driver.get(htmlPath);
        LOG.info("Opened {}", htmlPath);

        LOG.info("Executing JS:\n{}", jsGetClientRectText);
        @SuppressWarnings("unchecked")
        final Object jsResult = js.executeScript(jsGetClientRectText);
        LOG.info("Executed JS");

        LOG.info("jsResult: {}", jsResult);

        //@SuppressWarnings("unchecked")
        //final Map<String, Long> rect0 = (Map<String, Long>) jsResult.get(0);

        //LOG.info("rect0: {}", rect0);

driver.close();
        driver.quit();
    }
}
