package kuzme.hammermod.mixin;

import kuzme.hammermod.util.IScalable;
import net.minecraft.client.render.entity.MobRendererCreeper;
import net.minecraft.core.entity.monster.MobCreeper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MobRendererCreeper.class, remap = false)
public abstract class MobRendererCreeperMixin {
	@Inject(method = "setupScale", at = @At("TAIL"))
	private void injectSetupScale(MobCreeper entity, float f, CallbackInfo ci) {
		if (entity instanceof IScalable) {
			IScalable scalable = (IScalable) entity;
			float scale = scalable.getScale();
			GL11.glScalef(scale, scale, scale);
		}
	}
}
