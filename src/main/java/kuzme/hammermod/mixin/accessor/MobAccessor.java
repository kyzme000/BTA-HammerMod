package kuzme.hammermod.mixin.accessor;


import net.minecraft.core.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = Mob.class, remap = false)
public interface MobAccessor {
	@Invoker("setSize")
	void invokeSetSize(float width, float height);
}
