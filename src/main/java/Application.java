import io.vertx.core.Vertx;
import message.MessageVerticle;
import websocket.WebsocketVerticle;
import config.ChatRegisterCodec;

public class Application {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(ChatRegisterCodec.class.getName());
        vertx.deployVerticle(WebsocketVerticle.class.getName());
        System.out.println("Chat build successful");
    }
}
