package dev.xethh.sp;

import dev.xethh.sp.Box;
import dev.xethh.sp.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static dev.xethh.sp.BoxPacking.process;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestProcess {

    @Test
    @DisplayName("basic testing")
    public void testBox() {
        List<Item> items = Arrays.asList(
                Item.of("A", BigDecimal.valueOf(0.1)),
                Item.of("B", BigDecimal.valueOf(1.9)),
                Item.of("F", BigDecimal.valueOf(0.1)),
                Item.of("G", BigDecimal.valueOf(0.1)),
                Item.of("C", BigDecimal.valueOf(1.9)),
                Item.of("D", BigDecimal.valueOf(1.9)),
                Item.of("E", BigDecimal.valueOf(0.22))
        );


        List<Box> boxs = process(items, BigDecimal.valueOf(2));

        assertEquals(4, boxs.size());
        assertEquals(BigDecimal.valueOf(2).setScale(2,RoundingMode.HALF_UP), boxs.get(0).weight().setScale(2,RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(2).setScale(2,RoundingMode.HALF_UP), boxs.get(1).weight().setScale(2,RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(2).setScale(2,RoundingMode.HALF_UP), boxs.get(2).weight().setScale(2,RoundingMode.HALF_UP));
        assertEquals(BigDecimal.valueOf(0.22).setScale(2,RoundingMode.HALF_UP), boxs.get(3).weight().setScale(2,RoundingMode.HALF_UP));
    }

}
