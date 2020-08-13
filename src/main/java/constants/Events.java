package constants;

public class Events {
    private Events() {

    }

    public static final String TBY_CLIENT = "tby.client";
    public static final String MONGO_DB_CLIENT = "tby.mongodb";
    public static final String JWT_PARSER = "tby.jwt.parser";

    public static final String SERVICE_GET_ROOM = "tby.room.get";
    public static final String SERVICE_GET_ROOMS = "tby.rooms.get";
    public static final String SERVICE_CREATE_ROOM = "tby.room.create";
    public static final String SERVICE_UPDATE_ROOM = "tby.room.update";
    public static final String SERVICE_CREATE_ROOM_USER = "tby.room.user.create";

    public static final String SERVICE_ROOM_MESSAGE_READ = "tby.room.message.read";

    public static final String SERVICE_GET_USER_ROOM_UNREAD = "tby.user.room.unread.get";
    public static final String SERVICE_GET_USER_ROOM_UNREADS = "tby.user.room.unreads.get";
    public static final String SERVICE_CREATE_USER_ROOM_UNREAD = "tby.user.room.unread.create";
    public static final String SERVICE_UPDATE_USER_ROOM_UNREAD = "tby.user.room.unread.update";

    public static final String SERVICE_CREATE_MESSAGE = "message.create";
    public static final String SERVICE_RECEIVED_MESSAGE = "message.received";
    public static final String SERVICE_GET_MESSAGE = "tby.message.get";
    public static final String SERVICE_GET_MESSAGES = "tby.messages.get";
}
