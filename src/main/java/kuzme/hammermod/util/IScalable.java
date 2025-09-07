package kuzme.hammermod.util;

import com.mojang.nbt.tags.CompoundTag;

public interface IScalable {
	CompoundTag getHammerData();
	void setScale(float scale);
	float getScale();
}
