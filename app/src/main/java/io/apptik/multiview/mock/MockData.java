/*
 * Copyright (C) 2015 AppTik Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
