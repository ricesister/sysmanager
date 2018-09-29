package cps.fs.APImanagerSys.common.util.adapter;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Timestamp;

/**
 * @author Created by TruthBean on 2017-08-09 15:30.
 */
public class TimestampTypeAdapter extends TypeAdapter<Timestamp> {
    @Override
    public void write(JsonWriter out, Timestamp value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(value.getTime());
    }

    @Override
    public Timestamp read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        try {
            String value = in.nextString();
            if ("".equals(value)) {
                return null;
            }
            try {
                Long longValue = Long.parseLong(value);
                return new Timestamp(longValue);
            }catch (NumberFormatException e){
                return Timestamp.valueOf(value);
            }

        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
