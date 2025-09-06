package kuzme.hammermod;

import net.minecraft.core.entity.player.Player;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.core.world.World;
import net.minecraft.server.entity.player.PlayerServer;
import turniplabs.halplibe.helper.EnvironmentHelper;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NetHandler {

	public static void sendPoinnToNearby(World world, float current, float newScale,
										 float oldWidth, float oldHeight
	)
	{
		if (EnvironmentHelper.isServerEnvironment()) {
			for (Player player : world.players) {
				if (player instanceof PlayerServer) {
					PlayerServer serverPlayer = (PlayerServer) player;

					try (ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
						 DataOutputStream dataOutput = new DataOutputStream(byteOutput)) {

						dataOutput.writeFloat(current);
						dataOutput.writeFloat(newScale);
						dataOutput.writeFloat(oldHeight);
						dataOutput.writeFloat(oldWidth);

						PacketCustomPayload packet = new PacketCustomPayload("HammerPoinn", byteOutput.toByteArray());
						serverPlayer.playerNetServerHandler.sendPacket(packet);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}

			}

		}
	}
}
