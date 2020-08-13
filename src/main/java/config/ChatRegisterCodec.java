package config;

import entity.MessageRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;

public class ChatRegisterCodec extends AbstractVerticle {
    @Override
    public void start(Promise promise) throws Exception {
        EventBus bus = getVertx().eventBus();
        bus.registerDefaultCodec(MessageRequest.class, new MessageRequest());

        promise.complete();
    }
}
