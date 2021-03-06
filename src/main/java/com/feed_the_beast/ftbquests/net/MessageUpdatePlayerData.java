package com.feed_the_beast.ftbquests.net;

import com.feed_the_beast.ftbquests.client.ClientQuestFile;
import com.feed_the_beast.ftbquests.quest.PlayerData;
import com.feed_the_beast.ftbquests.util.NetUtils;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class MessageUpdatePlayerData extends MessageBase
{
	private final UUID uuid;
	private final String name;

	MessageUpdatePlayerData(PacketBuffer buffer)
	{
		uuid = NetUtils.readUUID(buffer);
		name = buffer.readString();
	}

	public MessageUpdatePlayerData(PlayerData data)
	{
		uuid = data.uuid;
		name = data.name;
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		NetUtils.writeUUID(buffer, uuid);
		buffer.writeString(name);
	}

	@Override
	public void handle(NetworkEvent.Context context)
	{
		if (ClientQuestFile.exists())
		{
			PlayerData data = ClientQuestFile.INSTANCE.getData(uuid);
			data.name = name;
		}
	}
}