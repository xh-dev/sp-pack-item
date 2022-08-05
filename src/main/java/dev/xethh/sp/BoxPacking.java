package dev.xethh.sp;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BoxPacking {
    public static List<Box> process(List<Item> items, BigDecimal max){
        return process(
                items
                        .stream()
                        .map(it->Box.of(it.getName(),max, Arrays.asList(it)))
                        .collect(Collectors.toList()),
                0);
    }
    private static List<Box> process(List<Box> boxes, int index) {
        List<Box> boxesList = boxes;
        boxesList.sort(Comparator.comparing(Box::weight).reversed());
        Box head = boxesList.get(index);

        while (true) {
            Optional<Box> out = boxesList.stream().skip(index + 1).filter(it->it.getItems().size()>0).filter(head::canPut).findFirst();
            if (out.isPresent()) {
                head.put(out.get());
                boxesList = boxesList.stream()
                        .filter(it->it.getItems().size()!=0)
                        .sorted(Comparator.comparing(Box::weight).reversed())
                        .collect(Collectors.toList());
            } else {
                break;
            }
        }

        if (boxesList.size() > index + 1 && boxesList.get(index+1).getItems().size()>0) {
            return process(boxesList, index + 1);
        } else {
            return boxesList;
        }
    }

}