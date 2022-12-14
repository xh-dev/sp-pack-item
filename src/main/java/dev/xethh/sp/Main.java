package dev.xethh.sp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class Main {
    public static class DtoReq {
        private BigDecimal max;
        private List<Item> items;

        public BigDecimal getMax() {
            return max;
        }

        public void setMax(BigDecimal max) {
            this.max = max;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }
    }

    public static class DtoResp {
        public static class Row {
            private BigDecimal weight;
            private List<String> items;

            public BigDecimal getWeight() {
                return weight;
            }

            public void setWeight(BigDecimal weight) {
                this.weight = weight;
            }

            public List<String> getItems() {
                return items;
            }

            public void setItems(List<String> items) {
                this.items = items;
            }

            public static Row convert(Box box) {
                Row row = new Row();
                row.setWeight(box.weight());
                row.setItems(box.getItems().stream().map(Item::getName).collect(Collectors.toList()));
                return row;
            }
        }

        private BigDecimal total;
        private List<Row> boxes;

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public List<Row> getBoxes() {
            return boxes;
        }

        public void setBoxes(List<Row> boxes) {
            this.boxes = boxes;
        }

        public static DtoResp convert(List<Box> boxes) {
            DtoResp resp = new DtoResp();
            resp.setBoxes(boxes.stream().map(Row::convert).collect(Collectors.toList()));
            resp.setTotal(resp.boxes.stream().map(it -> it.weight).reduce(BigDecimal.ZERO, BigDecimal::add));
            return resp;
        }
    }

    public static void main(String[] args) {
        ObjectMapper om = new ObjectMapper();
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(BigDecimal.class, new BigDecimalDeserializer());
        om.registerModule(mod);

        Javalin app = Javalin.create().start(8080);

        app.post("/boxes", context -> {
            DtoReq data = om.readValue(context.body(), DtoReq.class);
            List<Box> boxes = BoxPacking.process(data.items, data.max);
            context.json(DtoResp.convert(boxes));
        });

        app.get("version", context -> {
            Logger logger = LoggerFactory.getLogger("/version");
            try{
                Map<String, Object> map = new HashMap<>();
                URL res = Main.class.getResource("/META-INF/maven/dev.xethh.sp/pack-item/pom.properties");
                if(res != null){
                    map.put("packaging", "jar");
                    InputStream is = Main.class.getResourceAsStream("/META-INF/maven/dev.xethh.sp/pack-item/pom.properties");
                    if(is == null){
                        context.status(500);
                        map.put("error", "fail to load pom.properties");
                        context.json(map);
                        return;
                    } else {
                        Properties p = new Properties();
                        p.load(is);
                        String version = p.getProperty("version", "unknown");
                        map.put("version", version);
                        context.json(map);
                        return;
                    }
                } else {
                    map.put("packaging", "file");

                    InputStream is = new FileInputStream("pom.xml");
                    Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
                    NodeList project = dom.getElementsByTagName("project").item(0).getChildNodes();
                    for(int i = 0; i < project.getLength(); i++){
                        Node item = project.item(i);
                        if(item.getNodeName().equalsIgnoreCase("version")){
                            map.put("version", item.getTextContent());
                            context.json(map);
                            return;
                        }
                    }
                    map.put("version", "unknown");
                    context.json(map);
                    return;
                }
            } catch (Exception ex){
                logger.error("Error process version",ex);
            }

        });
    }
}