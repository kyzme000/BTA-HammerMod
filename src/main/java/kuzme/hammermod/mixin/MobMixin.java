package kuzme.hammermod.mixin;

import com.mojang.nbt.tags.CompoundTag;
import kuzme.hammermod.util.IScalable;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin extends Entity implements IScalable {
	private float scaleFactor = 1.0f;

	public MobMixin(World world) { super(world); }

	@Override
	public void setScale(float scale) {
		this.scaleFactor = scale;
	}

	@Override
	public float getScale() {
		return this.scaleFactor;
	}

	@Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
	private void injectSave(CompoundTag tag, CallbackInfo ci) {
		tag.putFloat("ScaleFactor", this.scaleFactor);
	}

	@Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
	private void injectLoad(CompoundTag tag, CallbackInfo ci) {
		if (tag.containsKey("ScaleFactor")) {
			this.scaleFactor = tag.getFloat("ScaleFactor");
		}
	}
}
