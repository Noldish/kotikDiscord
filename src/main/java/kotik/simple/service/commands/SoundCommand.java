package kotik.simple.service.commands;

import kotik.simple.service.CommandManager;
import sx.blah.discord.handle.obj.IMessage;

import java.io.Serializable;

public class SoundCommand implements CommandInterface, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6087128975166540488L;
	private String url;
	private final String STOP_COMMAND = "!stopdj";



	public SoundCommand(String url) {
		this.url = url;
	}

	public SoundCommand() {
	}

	@Override
	public void eval(IMessage message, CommandManager commandManager) {

		if (message.getContent().equals(STOP_COMMAND)){
			commandManager.getDiscordService().stopSound(message);
		}
		commandManager.getDiscordService().playSoundToChannelFromURL(message, url);
	}

}
