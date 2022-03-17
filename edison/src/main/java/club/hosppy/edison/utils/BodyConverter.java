package club.hosppy.edison.utils;

import java.nio.charset.StandardCharsets;

public class BodyConverter {

    public static String convertToString(byte[] body) {
        return body == null ? null : new String(body, StandardCharsets.UTF_8);
    }

    public static byte[] convertToBody(String body) {
        return body == null ? null : body.getBytes(StandardCharsets.UTF_8);
    }
}
