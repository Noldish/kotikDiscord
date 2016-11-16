package kotik.simple.service.commands;

import kotik.simple.objects.AbstractCommand;
import sx.blah.discord.handle.obj.IMessage;

public class SoundCommand extends AbstractCommand {

	private String url;
	private final String STOP_COMMAND = "!stopdj";
	private String path;


	public SoundCommand(String url) {
		this.url = url;
	}

	public SoundCommand() {
	}

	public SoundCommand(String path, String name) {
		this.path = path;
	}

	@Override
    public void eval(IMessage message) {

		if (message.getContent().equals(STOP_COMMAND)){
            getCommandManager().getDiscordService().stopSound(message);
		} else {
			if (url!=null)
                getCommandManager().getDiscordService().playSoundToChannelFromURL(message, url);
			if (path!=null)
                getCommandManager().getDiscordService().playSoundToChannelFromFile(message, path, getName());
        }
	}

}
