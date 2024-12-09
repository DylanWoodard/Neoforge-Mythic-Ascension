package net.therealzpope.mythic_ascension.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.therealzpope.mythic_ascension.block.ModBlocks;
import net.therealzpope.mythic_ascension.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MITHRAL_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.MITHRAL_INGOT)
                .unlockedBy(getHasName(ModItems.MITHRAL_INGOT.get()), has(ModItems.MITHRAL_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RITUAL_BOWL.get())
                .pattern("ABA")
                .pattern(" A ")
                .pattern(" C ")
                .define('A', Items.COPPER_INGOT)
                .define('B', Items.HONEYCOMB)
                .define('C', Blocks.SMOOTH_STONE_SLAB)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.SILVER_BELL.get())
                .pattern(" A ")
                .pattern(" B ")
                .pattern("BCB")
                .define('A', Items.STICK)
                .define('B', ModItems.SILVER_INGOT.get())
                .define('C', Items.IRON_NUGGET)
                .unlockedBy(getHasName(ModItems.SILVER_INGOT.get()), has(ModItems.SILVER_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TRANSCRIPTION_TABLE.get())
                .pattern(" A ")
                .pattern(" A ")
                .define('A', Items.CARROT_ON_A_STICK)
                .unlockedBy(getHasName(Items.CARROT_ON_A_STICK), has(Items.CARROT_ON_A_STICK))
                .save(recipeOutput);

    }
}
