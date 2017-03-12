package net.teamio.oasis;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.teamio.oasis.blocks.OasisMachineBlock;

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

	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {

	}
}
