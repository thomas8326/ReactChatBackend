package websocket;

import constants.Events;
import entity.MessageRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.core.http.HttpServerRequest;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WebsocketVerticle extends AbstractVerticle {
    private Map<String, ServerWebSocket> connectedMap = new HashMap<>();
    private Map<String, List<String>> roomUsers = new HashMap<>();
    private final Pattern chatUrlPattern = Pattern.compile("/chatroom/(?<roomId>\\w+)");
    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

//        this.apiRouteHandler(router);
        this.observeWebSocketRoom();
        this.createWebSocket(router);
    }

    private void createWebSocket(Router router) {
        vertx.createHttpServer().requestHandler(router).webSocketHandler(new Handler<ServerWebSocket>() {
            @Override
            public void handle(ServerWebSocket serverWebSocket) {
                String id = serverWebSocket.binaryHandlerID();

                serverWebSocket.frameHandler(handler -> {
                    if (handler.isClose()) {
                        return;
                    }

                    MessageRequest messageRequest = new JsonObject(handler.textData()).mapTo(MessageRequest.class);

                    createRoom(messageRequest, id);
                    connectedMap.put(id, serverWebSocket);

                    DeliveryOptions options = new DeliveryOptions();
                    options.addHeader("WEB_SOCKET_ID", id);
                    vertx.eventBus().publish(Events.SERVICE_CREATE_MESSAGE, messageRequest, options);
                });

                serverWebSocket.closeHandler(handler -> {
                    connectedMap.remove(id);
                    roomUsers.entrySet().forEach(room -> room.getValue().remove(id));
                });
            }
        }).listen(8090);
    }

    private void observeWebSocketRoom() {
        vertx.eventBus().consumer(Events.SERVICE_CREATE_MESSAGE, this::sendMessageToRoomUser);
    }

    private void sendMessageToRoomUser(Message<MessageRequest> event) {
        MessageRequest messageRequest = event.body();
        roomUsers.get(messageRequest.getRoomId())
                .forEach(webSocketId -> {
                    if(connectedMap.get(webSocketId).equals(null)) {
                        return;
                    }
                    connectedMap.get(webSocketId).writeTextMessage(messageRequest.getContent());
                });
    }

    private void createRoom(MessageRequest messageRequest, String webSocketId) {
        String roomId = messageRequest.getRoomId();

        if(checkRoomIsExist(roomId)) {
            roomUsers.put(roomId, new ArrayList<>());
        }

        if(roomUsers.get(roomId).contains(webSocketId)) {
            return;
        }

        roomUsers.get(roomId).add(webSocketId);
    }

    private boolean checkRoomIsExist(String roomId) {
        return roomUsers.size() == 0 || !Objects.nonNull(roomUsers.get(roomId));
    }

    private void setRoom(Message<String> event) {

    }
}
