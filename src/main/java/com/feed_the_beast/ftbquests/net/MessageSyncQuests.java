package com.feed_the_beast.ftbquests.net;

import com.feed_the_beast.ftblib.lib.io.DataIn;
import com.feed_the_beast.ftblib.lib.io.DataOut;
import com.feed_the_beast.ftblib.lib.net.MessageToClient;
import com.feed_the_beast.ftblib.lib.net.NetworkWrapper;
import com.feed_the_beast.ftbquests.client.FTBQuestsClient;
import com.google.gson.JsonElement;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author LatvianModder
 */
public class MessageSyncQuests extends MessageToClient
{
	private JsonElement json;

	public MessageSyncQuests()
	{
	}

	public MessageSyncQuests(JsonElement e)
	{
		json = e;
	}

	@Override
	public NetworkWrapper getWrapper()
	{
		return FTBQuestsNetHandler.QUESTS;
	}

	@Override
	public void writeData(DataOut data)
	{
		data.writeJson(json);
	}

	@Override
	public void readData(DataIn data)
	{
		json = data.readJson();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onMessage()
	{
		FTBQuestsClient.loadQuests(json.getAsJsonObject());
	}
}