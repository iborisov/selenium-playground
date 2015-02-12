package tst;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static tst.TextImgOverlappingDetector.areRectsOverlap;
import static tst.TextImgOverlappingDetector.createRect;

public class TextImgOverlappingDetectorTest {
    @Test
    public void testRectsOverlap() throws Exception {
        assertTrue(areRectsOverlap(
                createRect(10, 10, 30, 30),
                createRect(20, 20, 40, 40)));
    }

    @Test
    public void testRectsDontOverlap() throws Exception {
        assertFalse(areRectsOverlap(
                createRect(10, 10, 30, 30),
                createRect(10, 40, 30, 50)));
        assertFalse(areRectsOverlap(
                createRect(10, 10, 30, 30),
                createRect(40, 40, 60, 60)));
        assertFalse(areRectsOverlap(
                createRect(10, 10, 30, 30),
                createRect(0, 0, 5, 5)));
        assertFalse(areRectsOverlap(
                createRect(10, 10, 30, 30),
                createRect(0, 10, 5, 50)));
    }

    @Test
    public void test_r1_includes_r2_means_overlapped() throws Exception {
        assertTrue(areRectsOverlap(
                createRect(0, 0, 50, 50),
                createRect(10, 10, 20, 20)));
    }
}
