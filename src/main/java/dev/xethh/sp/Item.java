package dev.xethh.sp;

import java.math.BigDecimal;

public class Item {
    private String name;
    private BigDecimal weight;

    public Item() {
    }

    public Item(String name, BigDecimal weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public static Item of(String name, BigDecimal weight){
        return new Item(name, weight);
    }
}
