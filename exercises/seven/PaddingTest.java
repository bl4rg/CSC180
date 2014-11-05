package seven;

import org.junit.Test;

import static org.junit.Assert.*;

public class PaddingTest {
    private Padding padding  = new Padding();
    private final char TEST = 0x03;

    @Test
    public void testPad() throws Exception {
        System.out.println(padding.pad(1234L, 20));
        System.out.println(padding.pad("Hello, there!", 10));
        System.out.println(padding.pad(null, 10));
        System.out.println(padding.pad(null, 0));
        System.out.println(padding.pad(1234, 0));
    }

    @Test
    public void testUnpad() throws Exception {
        System.out.println(padding.unpad("1234" + TEST + TEST + TEST + TEST+ TEST + TEST + TEST + TEST + TEST));
        System.out.println(padding.unpad("34.45" + TEST + TEST + TEST + TEST));
        System.out.println(padding.unpad("Howdy, Stranger!" + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST));
        System.out.println(padding.unpad("1234123412341234" + TEST + TEST + TEST + TEST));
        System.out.println(padding.unpad(""));
    }

    @Test
    public void testUnpad1() throws Exception {
        System.out.println(padding.unpad("1234" + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST + TEST, Double.class));
        System.out.println(padding.unpad("34.45" + TEST + TEST + TEST + TEST, Integer.class));
        System.out.println(padding.unpad("Ora Viva!" + TEST + TEST + TEST + TEST + TEST, Long.class));
        System.out.println(padding.unpad("", Integer.class));
    }
}