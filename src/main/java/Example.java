import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Example {
    private static final Logger LOG = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) throws Exception {
        final String jsGetClientRectText = Resources.toString(
                Resources.getResource("clientRect.js.txt"), Charsets.UTF_8);

        final WebDriver driver = new FirefoxDriver();
        final JavascriptExecutor js = (JavascriptExecutor) driver;

        final File currDir = new File("./");
        final String htmlPath = "file:///" + currDir.getAbsolutePath() + "/test1.html";
        driver.get(htmlPath);

        LOG.info("Executing JS:\n{}", jsGetClientRectText);
        @SuppressWarnings("unchecked")
        final List<Map<String, ?>> jsResult = (List<Map<String, ?>>) js.executeScript(jsGetClientRectText);

        @SuppressWarnings("unchecked")
        final Map<String, Long> rect0 = (Map<String, Long>) jsResult.get(0);

        LOG.info("rect0: {}", rect0);


        driver.quit();
    }
}
