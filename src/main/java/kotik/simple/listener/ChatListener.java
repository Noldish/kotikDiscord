package kotik.simple.listener;


import kotik.simple.service.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;

@Service
public class ChatListener implements IListener<MessageReceivedEvent> {

    @Autowired
    private CommandManager manager;

    @Override
    public void handle(MessageReceivedEvent messageReceivedEvent) {
        IMessage message = messageReceivedEvent.getMessage();
        manager.message(message.getContent(),message.getChannel());
    }
}
