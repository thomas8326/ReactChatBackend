package entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.JsonObject;

public abstract class AbstractMessageCodec<S, R> implements MessageCodec<S, R> {

    private ObjectMapper mapper = new ObjectMapper();
    protected abstract Class<R> getReadClass();

    @Override
    public void encodeToWire(Buffer buffer, S s) {
        String json;
        try {
            json = mapper.writeValueAsString(s);
        } catch (JsonProcessingException e) {
            json = "";
        }

        JsonObject jsonObject = new JsonObject(json);
        String jsonToStr = jsonObject.encode();
        buffer.appendInt(jsonToStr.getBytes().length);
        buffer.appendString(jsonToStr);

    }

    @Override
    public R decodeFromWire(int position, Buffer buffer) {
        // My custom message starting from this *position* of buffer
        int _pos = position;

        // Length of JSON
        int length = buffer.getInt(_pos);

        // Get JSON string by it`s length
        // Jump 4 because getInt() == 4 bytes
        String jsonStr = buffer.getString(_pos+=4, _pos+=length);
        JsonObject contentJson = new JsonObject(jsonStr);

        R result = null;
        try {
            result = mapper.readValue(jsonStr, getReadClass());
        } catch (Exception e) {
            System.out.println("mapper json to class is error");
        }

        // We can finally create custom message object
        return result;
    }

    @Override
    public String name() {
        return this.getClass().getSimpleName();
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
