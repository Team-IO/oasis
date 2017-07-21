package net.teamio.oasis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.teamio.oasis.Constants;
import net.teamio.oasis.tileentities.OasisWell;

import javax.annotation.Nullable;

/**
 * Base machine block for all oasis tile entities.
 * <p>
 * Created by oliver on 2017-03-12.
 */
public class OasisMachineBlock extends Block implements ITileEntityProvider {
	public static final PropertyEnum<Constants.BLOCK_MACHINES_META> VARIANT = PropertyEnum.create("variant", Constants.BLOCK_MACHINES_META.class);

	public OasisMachineBlock() {
		super(Material.IRON, MapColor.GRAY);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		Constants.BLOCK_MACHINES_META meta = Constants.BLOCK_MACHINES_META.values()[i];

		switch (meta) {
			case OasisWell:
				return new OasisWell();
		}
		return null;
	}
}
