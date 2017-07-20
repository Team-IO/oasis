package net.teamio.oasis;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by oliver on 2017-07-20.
 */
public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderStuff() {
		ModelLoaderRegistry.registerLoader(OBJLoader.INSTANCE);
		OBJLoader.INSTANCE.addDomain(Constants.MOD_ID.toLowerCase());

		ItemModelMesher modelMesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

		registerItemDefault(modelMesher, Oasis.blockMuddyWater, 0, Constants.MOD_ID + ":fluid.muddy_water");
		//ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Constants.MOD_ID + ":" + Constants.FLUID_MUDDY_WATER, "inventory"));
	}

	/**
	 * Registers & remembers a model location for inventory rendering for the
	 * given item, for a single meta value.
	 *
	 * Specific for items using OBJ models.
	 *
	 * @param modelMesher
	 * @param itemId
	 * @param metaValue
	 * @param modelFile
	 */
	private void registerItemOBJSingleMeta(ItemModelMesher modelMesher, String itemId, int metaValue, String modelFile) {
		// Find item to register
		Item item = GameRegistry.findRegistry(Item.class).getValue(new ResourceLocation(Constants.MOD_ID, itemId));

		// Create & remember model location
		final ModelResourceLocation resourceLocation = new ModelResourceLocation(Constants.MOD_ID + ":" + modelFile, "inventory");
		//locationsToReplace.add(resourceLocation);

		// Register the variant
		modelMesher.register(item, metaValue, resourceLocation);
		// Register the model location
		ModelLoader.setCustomModelResourceLocation(item, metaValue, resourceLocation);
	}

	/**
	 * Registers a model for inventory rendering for a single item.
	 *
	 * Default rendering, not for OBJ models.
	 *
	 * @param modelMesher
	 * @param item
	 * @param meta
	 * @param name
	 */
	private static void registerItemDefault(ItemModelMesher modelMesher, Item item, int meta, String name) {
		modelMesher.register(item, meta, new ModelResourceLocation(name, "inventory"));
	}

	/**
	 * Registers a model for inventory rendering for a single item.
	 *
	 * Default rendering, not for OBJ models.
	 *
	 * @param modelMesher
	 * @param block
	 * @param meta
	 * @param name
	 */
	private static void registerItemDefault(ItemModelMesher modelMesher, Block block, int meta, String name) {
		Item item = Item.getItemFromBlock(block);
		registerItemDefault(modelMesher, item, meta, name);
	}

}
