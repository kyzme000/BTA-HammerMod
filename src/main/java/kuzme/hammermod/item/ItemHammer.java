package kuzme.hammermod.item;


import kuzme.hammermod.NetHandler;
import kuzme.hammermod.mixin.accessor.MobAccessor;
import kuzme.hammermod.util.IScalable;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import turniplabs.halplibe.helper.EnvironmentHelper;

import java.util.Random;

public class ItemHammer extends Item {

	public final Random random = new Random();

	public ItemHammer(String name, String namespaceId, int id) {
		super(name, namespaceId, id);
		this.maxStackSize = 1;
		this.setMaxDamage(100);
	}

	public boolean hitEntity(ItemStack itemstack, Mob target, Mob attacker) {
		World world = attacker.world;
		if (target instanceof IScalable) {
			IScalable scalable = (IScalable) target;
			float current = scalable.getScale();
			float newScale = current * 0.5f;
			float oldWidth = target.bbWidth;
			float oldHeight = target.bbHeight;
			scalable.setScale(newScale);
			MobAccessor accessor = (MobAccessor) target;
			accessor.invokeSetSize( oldWidth*0.5f, oldHeight*0.5f);
			target.heightOffset *= newScale;
			target.setPos(target.x, target.y + (oldHeight - target.bbHeight), target.z);
			world.sendGlobalMessage("scale: "+((IScalable) target).getScale()+"was: "+current);

			if (EnvironmentHelper.isServerEnvironment()) {
				NetHandler.sendPoinnToNearby(world, target.id, newScale);
			}
		}
		world.playSoundAtEntity(attacker, attacker, "hammermod:hammer",
			0.45F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.6F);
		itemstack.damageItem(1, attacker);
		return true;
	}

}
