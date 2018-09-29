package cps.fs.APImanagerSys.common.util.adapter;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Created by TruthBean on 2017-08-01 21:56.
 */
public class BigIntegerTypeAdapter extends TypeAdapter<BigInteger> {
    @Override
    public void write(JsonWriter out, BigInteger value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value);
        }

    }

    @Override
    public BigInteger read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        try {
            String value = in.nextString();
            if ("".equals(value)) {
                return BigInteger.ZERO;
            }
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }
}
