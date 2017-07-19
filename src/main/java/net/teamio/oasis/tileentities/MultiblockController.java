package net.teamio.oasis.tileentities;

import net.minecraft.tileentity.TileEntity;

/**
 * Abstract controller class for mutliblock machines.
 * <p>
 * Created by oliver on 2017-07-19.
 */
public abstract class MultiblockController extends TileEntity {
	public boolean checkMultiblock() {
		//TODO: implement pattern checking (on block break, e.g.)
		return true;
	}
}
