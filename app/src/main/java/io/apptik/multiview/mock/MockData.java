package io.apptik.multiview.mock;

import org.djodjo.json.JsonArray;
import org.djodjo.json.JsonObject;

import java.util.Random;


public class MockData {

    static Random rand = new Random();

    public static JsonArray getMockJsonArray(int noElements, int picSize) {
        JsonArray res = new JsonArray();
        for (int i = 0; i < noElements; i++) {
            res.add(getRandomEntry(i, picSize));
        }

        return res;
    }

    public static JsonObject getRandomEntry(int id, int picSize) {
        int cc = (int) (Math.random() * 0x1000000);
        int cc2 = 0xFFFFFF00 ^ cc;
        String color = Integer.toHexString(cc);
        String color2 = Integer.toHexString((0xFFFFFF - cc));
        // String color2 = Integer.toHexString(cc2);
        double lat = 50 + rand.nextInt(300) / 100d;
        double lon = 4 + rand.nextInt(300) / 100d;
        return new JsonObject()
                .put("pic", "http://dummyimage.com/" + picSize + "/" + color + "/" + color2)
                .put("title", "Item - " + id)
                .put("info", "info - " + color)
                .put("info2", "info - " + color2)
                .put("info3", "info - " + lat + ":" + lon)
                .put("loc", new JsonArray().put(lat).put(lon))
                .put("id", id);
    }
}
