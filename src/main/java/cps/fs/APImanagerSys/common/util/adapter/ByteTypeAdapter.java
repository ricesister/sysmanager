package cps.fs.APImanagerSys.common.util.adapter;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author Created by TruthBean on 2017-07-31 14:00.
 */
public class ByteTypeAdapter extends TypeAdapter<Byte> {
    @Override
    public void write(JsonWriter out, Byte value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value);
        }

    }

    @Override
    public Byte read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        try {
            String value = in.nextString();
            if ("".equals(value)) {
                return 0;
            }
            return Byte.parseByte(value);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
