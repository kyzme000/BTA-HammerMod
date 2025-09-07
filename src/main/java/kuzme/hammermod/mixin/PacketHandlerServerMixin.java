package kuzme.hammermod.mixin;

import com.mojang.nbt.tags.CompoundTag;
import kuzme.hammermod.util.IScalable;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.player.PlayerServer;
import net.minecraft.server.net.handler.PacketHandlerServer;
import net.minecraft.server.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@Mixin(value = PacketHandlerServer.class, remap = false)
public abstract class PacketHandlerServerMixin {

	@Shadow
	private PlayerServer playerEntity;

	@Inject(
		method = "handleCustomPayload(Lnet/minecraft/core/net/packet/PacketCustomPayload;)V",
		at = @At("TAIL"),
		remap = false
	)
	private void kaifcraft$onHandleCustomPayload(PacketCustomPayload packet, CallbackInfo ci) {
		if (!"HammerPoinn".equals(packet.channel)) return;

		try (ByteArrayInputStream byteInput = new ByteArrayInputStream(packet.data);
			 DataInputStream dataInputStream = new DataInputStream(byteInput)) {

			int entityId = dataInputStream.readInt();
			float newScale = dataInputStream.readFloat();

			MinecraftServer server = playerEntity.mcServer;
			WorldServer world = server.getDimensionWorld(playerEntity.dimension);
			Entity entity = null;
			for (Entity e : world.loadedEntityList) {
				if (e.id == entityId) {
					entity = e;
					break;
				}
			}

			if (entity == null) {
				System.err.println("[Hammer] Не удалось найти сущность с ID: " + entityId);
				return;
			}

			if (entity instanceof IScalable) {
				((IScalable) entity).setScale(newScale);
				System.out.println("Scale set for "+entityId);
			} else {
				System.err.println("ERROR");
			}


		} catch (IOException e) {
			System.err.println("[Hammer] Ошибка при чтении пакета HammerPoin: " + e);
		}


	}
}
