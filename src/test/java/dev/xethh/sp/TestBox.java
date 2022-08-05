package dev.xethh.sp;

import dev.xethh.sp.Box;
import dev.xethh.sp.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestBox {

    @Test
    @DisplayName("basic testing")
    public void testBox() {
        Box box = Box.of("Box A", BigDecimal.valueOf(3));
        assertEquals(BigDecimal.valueOf(3), box.getMaxWeight());
        assertEquals("Box A", box.getName());
        assertEquals(0, box.getItems().size());
    }

    @Test
    @DisplayName("set item")
    public void testSetItem() {
        Box box = Box.of("Box A", BigDecimal.valueOf(3));

        //Test exceed max
        assertThrows(RuntimeException.class, () -> {
            box.setItems(Arrays.asList(
                    Item.of("A", BigDecimal.valueOf(1)),
                    Item.of("B", BigDecimal.valueOf(1)),
                    Item.of("C", BigDecimal.valueOf(1)),
                    Item.of("D", BigDecimal.valueOf(1))
            ));
        });

        //Test set Items
        box.setItems(Arrays.asList(
                Item.of("C", BigDecimal.valueOf(1)),
                Item.of("D", BigDecimal.valueOf(1))
        ));
        assertEquals(2, box.getItems().size());
        assertEquals(BigDecimal.valueOf(2), box.weight());
    }

    @Test
    @DisplayName("can put")
    public void testCanPut() {
        Box box = Box.of("Box A", BigDecimal.valueOf(3));

        assertEquals(true, box.canPut(
                Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.ZERO)))));
        assertEquals(true, box.canPut(
                Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.ONE)))));
        assertEquals(false, box.canPut(
                Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.valueOf(3.1))))));
    }

    @Test
    @DisplayName("put")
    public void testPut() {
        Box box = Box.of("Box A", BigDecimal.valueOf(3));

        box.put( Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.ZERO))));
        assertEquals(BigDecimal.ZERO, box.weight());

        box.put( Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.ONE))));
        assertEquals(BigDecimal.ONE, box.weight());

        box.put( Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.valueOf(2)))));
        assertEquals(BigDecimal.valueOf(3), box.weight());

        assertThrows(RuntimeException.class, ()->{
            box.put( Box.of("xx", BigDecimal.TEN, Arrays.asList(Item.of("",BigDecimal.valueOf(0.1)))));
        });
    }
}
