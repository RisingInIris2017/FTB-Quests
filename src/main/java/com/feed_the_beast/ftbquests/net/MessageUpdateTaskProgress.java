package com.feed_the_beast.ftbquests.net;

import com.feed_the_beast.ftbquests.client.ClientQuestFile;
import com.feed_the_beast.ftbquests.quest.PlayerData;
import com.feed_the_beast.ftbquests.quest.task.Task;
import com.feed_the_beast.ftbquests.util.NetUtils;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

/**
 * @author LatvianModder
 */
public class MessageUpdateTaskProgress extends MessageBase
{
	private UUID player;
	private int task;
	private long progress;

	public MessageUpdateTaskProgress(PacketBuffer buffer)
	{
		player = NetUtils.readUUID(buffer);
		task = buffer.readVarInt();
		progress = buffer.readVarLong();
	}

	public MessageUpdateTaskProgress(PlayerData t, int k, long p)
	{
		player = t.uuid;
		task = k;
		progress = p;
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		NetUtils.writeUUID(buffer, player);
		buffer.writeVarInt(task);
		buffer.writeVarLong(progress);
	}

	@Override
	public void handle(NetworkEvent.Context context)
	{
		Task t = ClientQuestFile.INSTANCE.getTask(task);

		if (t != null)
		{
			PlayerData data = ClientQuestFile.INSTANCE.getData(player);
			ClientQuestFile.INSTANCE.clearCachedProgress();
			data.getTaskData(t).setProgress(progress);
		}
	}
}