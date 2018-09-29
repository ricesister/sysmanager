package cps.fs.APImanagerSys.common.util.adapter;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author Created by TruthBean on 2017-07-31 13:38.
 */
public class CharTypeAdapter extends TypeAdapter<Character> {
    @Override
    public void write(JsonWriter out, Character value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value);
    }

    @Override
    public Character read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        try {
            String value = in.nextString();
            if ("".equals(value)) {
                return Character.MIN_VALUE;
            }
            return value.charAt(0);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
