package kotik.simple;


import kotik.simple.service.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IMessage;

@Component
public class ChatListener implements IListener<MessageReceivedEvent> {

    @Autowired
    private CommandManager manager;

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        IMessage message = messageReceivedEvent.getMessage();
        manager.message(message.getContent(),message.getChannel());
    }
}
