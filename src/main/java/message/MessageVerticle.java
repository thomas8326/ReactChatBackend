package message;

import constants.Events;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.ServerWebSocket;

import java.util.HashMap;
import java.util.Map;

public class MessageVerticle extends AbstractVerticle {
    private Map<String, String> webSocketHashMap = new HashMap<>();

    @Override
    public void start() {
        vertx.eventBus().consumer("createWebSocket", this::onCreateMessage);
        vertx.eventBus().consumer(Events.SERVICE_RECEIVED_MESSAGE, this::onReceivedMessage);
    }

    private void onReceivedMessage(Message<String> event) {
        event.reply(event.body());
    }

    private void onCreateMessage(Message<String> event) {
        String webSocketId = event.headers().get("webSocketId");
        String message = event.body();

//        vertx.eventBus().request("createWebsocket", message, reply -> {
//            if(reply.succeeded()) {
//                webSocketHashMap.put(webSocketId, )
//            }
//        })

    }
}
