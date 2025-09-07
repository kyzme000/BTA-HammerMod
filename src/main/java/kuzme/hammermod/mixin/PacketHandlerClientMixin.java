package kuzme.hammermod.mixin;

import com.mojang.nbt.tags.CompoundTag;
import kuzme.hammermod.util.IScalable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.net.handler.PacketHandlerClient;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.net.packet.PacketCustomPayload;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

@Mixin(value = PacketHandlerClient.class, remap = false)
public abstract class PacketHandlerClientMixin {

	@Shadow
	private Minecraft mc;

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

			World world = mc.currentWorld;
			Entity entity = null;
			for (Entity e : world.loadedEntityList) {
				if (e.id == entityId) {
					entity = e;
					break;
				}
			}

			if (entity instanceof IScalable) {
				((IScalable) entity).setScale(newScale);
			}

		} catch (IOException e) {
			System.err.println("[Hammer] Ошибка при чтении клиентского пакета HammerPoinn: " + e);
		}
	}
}

