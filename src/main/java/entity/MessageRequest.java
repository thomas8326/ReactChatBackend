package entity;

public class MessageRequest extends AbstractMessageCodec<MessageRequest, MessageRequest>{
    private String roomId;
    private String content;
    private String senderMsgId;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSenderMsgId() {
        return senderMsgId;
    }

    public void setSenderMsgId(String senderMsgId) {
        this.senderMsgId = senderMsgId;
    }

    @Override
    protected Class<MessageRequest> getReadClass() {
        return MessageRequest.class;
    }

    @Override
    public MessageRequest transform(MessageRequest messageRequest) {
        return messageRequest;
    }
}
