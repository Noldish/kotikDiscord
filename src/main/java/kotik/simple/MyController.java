package kotik.simple;

import kotik.simple.listener.ChatListener;
import kotik.simple.listener.InterfaceListener;
import kotik.simple.service.CommandManager;
import kotik.simple.service.DiscordService;
import kotik.simple.service.commands.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Romique on 19.10.2016.
 */
@RestController
public class MyController {
    @Autowired
    DiscordService discordService;

    @Autowired
    CommandManager commandManager;

    @RequestMapping("/stop")
    public void stop() throws DiscordException, RateLimitException{
        discordService.shutdown();
    }

    @RequestMapping("/addCommand")
    public String addCommand(@RequestParam(value="message") String message, @RequestParam(value="response") String response){
        System.out.println("/addCommand on message = " + message + " response = " + response);
        return commandManager.addCommand(message, response);
    }

    @RequestMapping("/deleteCommand")
    public String deleteCommand(@RequestParam(value="message") String message){
        return commandManager.deleteCommand(message);
    }

    @RequestMapping("/getCommands")
    public Map<String, String> getCommands(){
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, CommandInterface> entry : commandManager.getCommands().entrySet()){
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }
    
    @RequestMapping("/dbinit")
    public String dbInit(){
    	StringBuilder result = new StringBuilder();
    	result.append(commandManager.addCommand("!гуся", new TextCommand("░ГУСЯ░▄▀▀▀▄░РАБОТЯГИ░░ \n" +
                "▄███▀░◐░░░▌░░░░░░░ \n" +
                "░░░░▌░░░░░▐░░░░░░░ \n" +
                "░░░░▐░░░░░▐░░░░░░░ \n" +
                "░░░░▌░░░░░▐▄▄░░░░░ \n" +
                "░░░░▌░░░░▄▀▒▒▀▀▀▀▄ \n" +
                "░░░▐░░░░▐▒▒▒▒▒▒▒▒▀▀▄ \n" +
                "░░░▐░░░░▐▄▒▒▒▒▒▒▒▒▒▒▀▄ \n" +
                "░░░░▀▄░░░░▀▄▒▒▒▒▒▒▒▒▒▒▀▄ \n" +
                "░░░░░░▀▄▄▄▄▄█▄▄▄▄▄▄▄▄▄▄▄▀▄ \n" +
                "░░░░░░░░░░░▌▌░▌▌░░░░░ \n" +
                "░░░░░░░░░░░▌▌░▌▌░░░░░ \n" +
                "░░░░░░░░░▄▄▌▌▄▌▌░░░░░"))
    			+"\n");
    	result.append(commandManager.addCommand("!пюрешка", new TextCommand("https://www.youtube.com/watch?v=A1Qb4zfurA8"))+"\n");
    	result.append(commandManager.addCommand("!найди пидораса", new FindCommand())+"\n");
    	result.append(commandManager.addCommand("!help", new HelpCommand())+"\n");
    	result.append(commandManager.addCommand("!wtfradio", new SoundCommand("http://vprbbc.streamguys.net:80/vprbbc24.mp3"))+"\n");
    	result.append(commandManager.addCommand("!radiorock", new SoundCommand("http://45.79.186.124:8191/stream"))+"\n");
    	result.append(commandManager.addCommand("!stopdj", new SoundCommand())+"\n");
    	result.append(commandManager.addCommand("!add", new AddCommand())+"\n");
    	result.append(commandManager.addCommand("!badumts", new SoundCommand("/","joke_drum_effect.mp3"))+"\n");
    	result.append(commandManager.addCommand("!delete", new DeleteCommand())+"\n");
        return result.toString();
    }
}
