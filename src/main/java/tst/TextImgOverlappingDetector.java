package tst;

import com.google.common.collect.ImmutableMap;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public final class TextImgOverlappingDetector {
    private static final Logger LOG = LoggerFactory.getLogger(TextImgOverlappingDetector.class);

    private TextImgOverlappingDetector() {
        throw new RuntimeException("You don't want to construct this.");
    }

    private static final String JS_TEXT_AND_IMG_NODES_RECTS = readFileAsString("src/main/resources/clientRect.js.txt");
    private static final NumberDblValComparator NUMBER_DBL_VAL_COMPARATOR = new NumberDblValComparator();
    public static final String TEXT_RECTS_KEY = "textRects";
    public static final String IMG_RECTS_KEY = "imgRects";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_LEFT = "left";
    public static final String KEY_BOTTOM = "bottom";
    public static final String KEY_RIGHT = "right";
    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_TOP = "top";

    public static Map<String, Number> createRect(final Number top, final Number left,
                                                 final Number bottom, final Number right) {
        return ImmutableMap.of(KEY_TOP, top, KEY_LEFT, left, KEY_BOTTOM, bottom, KEY_RIGHT, right);
    }

    /**
     * @param webDriver  WebDriver to open given URL and execute JavaScript.
     * @param urlToCheck URL to check
     * @return 1 if some text and img nodes are overlapping, 0 otherwise.
     */
    public static int detect(final WebDriver webDriver, final String urlToCheck) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) webDriver;
            LOG.info("Opening {} ...", urlToCheck);
            webDriver.get(urlToCheck);
            final int result = TextImgOverlappingDetector.detect(js);
            LOG.info("img-text overlapping detection result for {} is {}.", urlToCheck, result);
            return result;
        } catch (Exception e) {
            LOG.error("Error", e);
            throw new RuntimeException("Error detecting text-img overlapping", e);
        } finally {
            webDriver.close();
        }
    }

    /**
     * @param jsExecutor WebDriver with opened page to test.
     * @return 1 if some text and img nodes are overlapping, 0 otherwise.
     */
    public static int detect(final JavascriptExecutor jsExecutor) {
        if (JS_TEXT_AND_IMG_NODES_RECTS.isEmpty()) {
            throw new IllegalArgumentException("JS file to get nodes rectangles didn't read. Can't continue.");
        }
        @SuppressWarnings("unchecked")
        final Map<String, List<Map<String, Number>>> jsRes = (Map<String, List<Map<String, Number>>>) jsExecutor.executeScript(JS_TEXT_AND_IMG_NODES_RECTS);
        final List<Map<String, Number>> textRects = jsRes.get(TEXT_RECTS_KEY);
        final List<Map<String, Number>> imgRects = jsRes.get(IMG_RECTS_KEY);
        for (Map<String, Number> imgRect : imgRects) {
            for (Map<String, Number> textRect : textRects) {
                if (areRectsOverlap(imgRect, textRect)) {
                    LOG.info("Found overlapping for text node: {}\nand image node: {}",
                            textRect, imgRect);
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * @param filename file name.
     * @return content of file with given name, or empty string if IOException occurred.
     */
    private static String readFileAsString(final String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "";
        }
    }

    public static boolean areRectsOverlap(final Map<String, Number> r1,
                                          final Map<String, Number> r2) {
        return !(greq(r2.get(KEY_LEFT), r1.get(KEY_RIGHT))
                || leq(r2.get(KEY_RIGHT), r1.get(KEY_LEFT))
                || greq(r2.get(KEY_TOP), r1.get(KEY_BOTTOM))
                || leq(r2.get(KEY_BOTTOM), r1.get(KEY_TOP)));
    }

    public static boolean leq(final Number n1, final Number n2) {
        return NUMBER_DBL_VAL_COMPARATOR.compare(n1, n2) <= 0;
    }

    public static boolean greq(final Number n1, final Number n2) {
        return NUMBER_DBL_VAL_COMPARATOR.compare(n1, n2) >= 0;
    }

    public static boolean lessOrEqual(final Number n1, final Number n2) {
        return NUMBER_DBL_VAL_COMPARATOR.compare(n1, n2) <= 0;
    }

    public static class NumberDblValComparator implements Comparator<Number> {
        @Override
        public int compare(Number n1, Number n2) {
            final double n1v = n1.doubleValue(), n2v = n2.doubleValue();
            int res = 0;
            if (n1v > n2v) {
                res = 1;
            } else if (n1v < n2v) {
                res = -1;
            }
            return res;
        }
    }
}
