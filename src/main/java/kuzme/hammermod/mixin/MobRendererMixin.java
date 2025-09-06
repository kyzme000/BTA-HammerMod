package kuzme.hammermod.mixin;

import kuzme.hammermod.util.IScalable;
import net.minecraft.client.render.entity.*;
import net.minecraft.core.entity.Mob;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobRenderer.class,  remap = false)
public abstract class MobRendererMixin<T extends Mob> {
	@Inject(method = "setupScale", at = @At("HEAD"))
	private void injectSetupScale(T entity, float partialTick, CallbackInfo ci) {
		if (entity instanceof IScalable) {
			IScalable scalable = (IScalable) entity;
			float scale = scalable.getScale();

			GL11.glScalef(scale, scale, scale);

		}
	}
}
