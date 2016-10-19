package kotik.simple.listener;


import kotik.simple.service.CommandManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.ReadyEvent;

@Service
public class InterfaceListener implements IListener<ReadyEvent> {

    @Autowired
    private CommandManager manager;

    @Override
    public void handle(ReadyEvent event) { // This is called when the ReadyEvent is dispatched
        System.out.println(manager.handle());
    }
}
