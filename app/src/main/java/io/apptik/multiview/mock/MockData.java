package io.apptik.multiview.mock;

import org.djodjo.json.JsonArray;
import org.djodjo.json.JsonObject;

import java.util.Random;


public class MockData {

    public static JsonArray getMockJsonArray(int noElements, int picSize) {
        JsonArray res = new JsonArray();
        Random rand = new Random();

        for (int i = 0; i < noElements; i++) {
            int cc = (int) (Math.random() * 0x1000000);
            int cc2 = 0xFFFFFF00 ^ cc;
            String color = Integer.toHexString(cc);
            String color2 = Integer.toHexString((0xFFFFFF - cc));
            // String color2 = Integer.toHexString(cc2);
            double lat = 50 + rand.nextInt(300) / 100d;
            double lon = 4 + rand.nextInt(300) / 100d;
            res.add(
                    new JsonObject()
                            .put("pic", "http://dummyimage.com/" + picSize + "/" + color + "/" + color2)
                            .put("title", "Item - " + i)
                            .put("info", "info - " + color)
                            .put("info2", "info - " + color2)
                            .put("info3", "info - " + lat + ":" + lon)
                            .put("loc", new JsonArray().put(lat).put(lon))
            );
        }

        return res;
    }
}
