package dev.xethh.sp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Box {
    private String name;
    private List<Item> items = new ArrayList<>();

    public boolean canPut(Box box){
        return this.weight().add(box.weight()).compareTo(maxWeight) <= 0;
    }

    public void put(Box box){
        if(!canPut(box)) throw new RuntimeException("can not put into this box");
        this.items.addAll(box.clear());
    }

    public BigDecimal weight(){
        return this.items.stream().map(Item::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private final BigDecimal maxWeight;

    public Box(String name, BigDecimal max_weight) {
        this.name = name;
        this.maxWeight = max_weight;
    }
    public static Box of(String name){
        return new Box(name);
    }
    public static Box of(String name, BigDecimal maxWeight){
        return new Box(name, maxWeight);
    }
    public static Box of(String name, BigDecimal maxWeight, List<Item> item){
        Box box = new Box(name, maxWeight);
        box.setItems(item);
        return box;
    }

    public Box(String name) {
        this(name, BigDecimal.valueOf(2));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        BigDecimal totlaWeight = items.stream().map(Item::getWeight).reduce(BigDecimal.ZERO, BigDecimal::add);
        if(maxWeight.subtract(totlaWeight).compareTo(BigDecimal.ZERO)<0){
            throw new RuntimeException("exceed max weight");
        }
        this.items.addAll(items);
    }

    public List<Item> clear(){
        List<Item> temp = this.items.stream().toList();
        this.items.clear();
        return temp;
    }

    @Override
    public String toString() {
        return "Box{" +
                "name='" + name + '\'' +
                ", weight=" + weight() +
                ", max_weight=" + maxWeight +
                '}';
    }

    public BigDecimal getMaxWeight() {
        return maxWeight;
    }
}
