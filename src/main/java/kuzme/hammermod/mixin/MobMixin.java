package kuzme.hammermod.mixin;

import com.mojang.nbt.tags.CompoundTag;
import kuzme.hammermod.util.IScalable;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.world.World;
import org.checkerframework.common.aliasing.qual.Unique;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Mob.class, remap = false)
public abstract class MobMixin extends Entity implements IScalable {
	private float scaleFactor;

	@Unique
	private final CompoundTag hammerData = new CompoundTag();

	public MobMixin(World world) {
		super(world);
		this.scaleFactor = 1.0f;
		this.hammerData.putFloat("Scale", this.scaleFactor);
	}

	@Override
	public CompoundTag getHammerData() {
		return hammerData;
	}

	@Override
	public void setScale(float scale) {
		if (scale <= 0f) scale = 1.0f;
		this.scaleFactor = scale;
		this.hammerData.putFloat("Scale", scale);
	}

	@Override
	public float getScale() {
		if (scaleFactor <= 0f) scaleFactor = 1.0f;
		return this.scaleFactor;
	}

	@Inject(method = "addAdditionalSaveData", at = @At("HEAD"))
	private void injectSave(CompoundTag tag, CallbackInfo ci) {
		tag.putFloat("Scale", this.getScale());
	}

	@Inject(method = "readAdditionalSaveData", at = @At("HEAD"))
	private void injectLoad(CompoundTag tag, CallbackInfo ci) {
		if (tag.containsKey("Scale")) {
			this.setScale(tag.getFloat("Scale"));
		}
	}
}
