package dev.xethh.sp;

import dev.xethh.sp.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestItem {

    @Test
    @DisplayName("basic testing")
    public void testItem(){
        Item item = Item.of("item", BigDecimal.ZERO);
        item.setWeight(BigDecimal.ZERO);
        item.setName("item");

        assertEquals(BigDecimal.ZERO, item.getWeight());
        assertEquals("item", item.getName());
    }
}
