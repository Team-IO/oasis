package net.teamio.oasis.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.teamio.oasis.tileentities.MultiblockFormer;
import net.teamio.oasis.tileentities.MultiblockProxy;
import net.teamio.oasis.tileentities.WindTrap;

import javax.annotation.Nullable;

/**
 * Base machine block for all oasis tile entities.
 * <p>
 * Created by oliver on 2017-03-12.
 */
public class MultiblockBlock extends Block implements ITileEntityProvider {
	public static PropertyEnum<MultiblockRenderState> STATE = PropertyEnum.create("state", MultiblockRenderState.class, MultiblockRenderState.values());

	public enum MultiblockRenderState implements IStringSerializable {
		Forming,
		Proxy,
		MBWindTrap;

		@Override
		public String getName() {
			return name().toLowerCase();
		}
	}

	public MultiblockBlock() {
		super(Material.IRON, MapColor.GRAY);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new MultiblockFormer();
	}

	@Override
	public int getMetaFromState(IBlockState p_getMetaFromState_1_) {
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new ExtendedBlockState(this, new IProperty[]{STATE}, new IUnlistedProperty[0]);
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public int getLightOpacity(IBlockState p_getLightOpacity_1_, IBlockAccess p_getLightOpacity_2_, BlockPos p_getLightOpacity_3_) {
		return 0;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getActualState(IBlockState actualState, IBlockAccess world, BlockPos pos) {
		MultiblockRenderState state;

		TileEntity te = world.getTileEntity(pos);

		//TODO: make this NOT use instanceof
		if (te instanceof MultiblockProxy) {
			state = MultiblockRenderState.Proxy;
		} else if (te instanceof MultiblockFormer) {
			state = MultiblockRenderState.Forming;
		} else if (te instanceof WindTrap) {
			state = MultiblockRenderState.MBWindTrap;
		} else {
			state = MultiblockRenderState.Forming;
		}

		return actualState.withProperty(STATE, state);
	}
}
