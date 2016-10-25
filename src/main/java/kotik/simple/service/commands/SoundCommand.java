package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.File;
import java.io.Serializable;

public class SoundCommand implements CommandInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6087128975166540488L;
	private String url;
	private final String STOP_COMMAND = "!stopdj";
	private String path;
	private String name;


	public SoundCommand(String url) {
		this.url = url;
	}

	public SoundCommand() {
	}

	public SoundCommand(String path, String name) {
		this.path = path;
		this.name = name;
	}

	@Override
	public void eval(IMessage message, CommandManager commandManager) {

		if (message.getContent().equals(STOP_COMMAND)){
			commandManager.getDiscordService().stopSound(message);
		} else {
			if (url!=null)
				commandManager.getDiscordService().playSoundToChannelFromURL(message, url);
			if (path!=null)
				commandManager.getDiscordService().playSoundToChannelFromFile(message, path, name);
        }
	}

}
