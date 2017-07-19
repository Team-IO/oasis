package net.teamio.oasis;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.teamio.oasis.blocks.MultiblockBlock;
import net.teamio.oasis.blocks.OasisMachineBlock;
import net.teamio.oasis.fluids.MuddyWater;
import net.teamio.oasis.tileentities.MultiblockFormer;
import net.teamio.oasis.tileentities.MultiblockProxy;
import net.teamio.oasis.tileentities.OasisWell;
import net.teamio.oasis.tileentities.WindTrap;
import scala.collection.immutable.Stream;

/**
 * Created by oliver on 2017-03-12.
 */
@Mod(
		modid = Constants.MOD_ID,
		name = Constants.MOD_NAME,
		version = Constants.MOD_VERSION,
		acceptedMinecraftVersions = "[1.11]"
)
public class Oasis {

	private static CreativeTabs creativeTab;
	public static OasisMachineBlock blockMachine;
	public static MultiblockBlock blockMultiblock;
	public static MuddyWater fluidMuddyWater;
	public static BlockFluidClassic blockMuddyWater;

	private static void registerBlock(Block block, ItemBlock item, String name) {
		registerBlock(block, name);
		registerItem(item, name);
	}

	private static void registerBlock(Block block, String name) {
		block.setUnlocalizedName(Constants.MOD_ID + "." + name);
		block.setCreativeTab(creativeTab);
		block.setRegistryName(name);
		GameRegistry.register(block);
	}

	private static void registerItem(Item item, String name) {
		item.setUnlocalizedName(Constants.MOD_ID + "." + name);
		item.setCreativeTab(creativeTab);
		item.setRegistryName(name);
		GameRegistry.register(item);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		/*
		 * Some general stuff that needs to be registered
		 */

		//MinecraftForge.EVENT_BUS.register(new CraftingHandler());
		MinecraftForge.EVENT_BUS.register(new Config());
		//MinecraftForge.EVENT_BUS.register(proxy);

		/*
		 * Read Config
		 */

		Config.init(event.getSuggestedConfigurationFile());
		creativeTab = new CreativeTabs(Constants.MOD_ID) {
			@Override
			@SideOnly(Side.CLIENT)
			public ItemStack getTabIconItem() {
				return new ItemStack(blockMachine, 1, 1);
			}
		};

		registerBlock(blockMachine = new OasisMachineBlock(), new ItemBlock(blockMachine), Constants.BLOCK_MACHINE);
		registerBlock(blockMultiblock = new MultiblockBlock(), new ItemBlock(blockMultiblock), Constants.BLOCK_MULTIBLOCK);

		fluidMuddyWater = new MuddyWater(Constants.FLUID_MUDDY_WATER);
		FluidRegistry.registerFluid(fluidMuddyWater);
		blockMuddyWater = new BlockFluidClassic(fluidMuddyWater, Material.WATER) {
			@Override
			public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
				IBlockState neighbor = world.getBlockState(pos.offset(side));
				// Force rendering if there is a different block adjacent, not only a different material
				if (neighbor.getBlock() != this) {
					return true;
				}
				return super.shouldSideBeRendered(state, world, pos, side);
			}

			@Override
			protected boolean canFlowInto(IBlockAccess world, BlockPos pos) {
				IBlockState state = world.getBlockState(pos);
				Material mat = state.getMaterial();
				if (mat == Material.WATER || mat == Material.LAVA) {
					return false;
				}

				return super.canFlowInto(world, pos);
			}

			@Override
			public boolean canDisplace(IBlockAccess world, BlockPos pos) {
				IBlockState state = world.getBlockState(pos);
				Material mat = state.getMaterial();
				if (mat == Material.WATER || mat == Material.LAVA) {
					return false;
				}

				return super.canDisplace(world, pos);
			}

			@Override
			public boolean displaceIfPossible(World world, BlockPos pos) {
				if (!canDisplace(world, pos)) {
					return false;
				}
				return super.displaceIfPossible(world, pos);
			}
		};
		registerBlock(blockMuddyWater, new ItemBlock(blockMuddyWater), "fluid." + Constants.FLUID_MUDDY_WATER);

		GameRegistry.registerTileEntity(OasisWell.class, Constants.TILEENTITY_OASIS_WELL);
		GameRegistry.registerTileEntity(MultiblockFormer.class, Constants.TILEENTITY_MULTIBLOCK_FORMER);
		GameRegistry.registerTileEntity(MultiblockProxy.class, Constants.TILEENTITY_MULTIBLOCK_PROXY);
		GameRegistry.registerTileEntity(WindTrap.class, Constants.TILEENTITY_MB_WIND_TRAP);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}
}
