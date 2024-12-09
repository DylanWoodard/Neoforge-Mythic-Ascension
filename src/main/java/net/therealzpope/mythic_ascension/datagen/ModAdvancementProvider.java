package net.therealzpope.mythic_ascension.datagen;

import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.therealzpope.mythic_ascension.MythicAscension;
import net.therealzpope.mythic_ascension.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModAdvancementProvider implements AdvancementProvider.AdvancementGenerator {
    Advancement.Builder builder = Advancement.Builder.advancement();

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        builder.parent(AdvancementSubProvider.createPlaceholder("minecraft:story/root"));

        builder.display(
                // The advancement icon. Can be an ItemStack or an ItemLike.
                new ItemStack(ModItems.BLASTING_ROD.get()),
                // The advancement title and description. Don't forget to add translations for these!
                Component.translatable("advancements.mythic_ascension.blasting_rod_advancement.title"),
                Component.translatable("advancements.mythic_ascension.blasting_rod_advancement.description"),
                // The background texture. Use null if you don't want a background texture (for non-root advancements).
                null,
                // The frame type. Valid values are AdvancementType.TASK, CHALLENGE, or GOAL.
                AdvancementType.GOAL,
                // Whether to show the advancement toast or not.
                true,
                // Whether to announce the advancement into chat or not.
                true,
                // Whether the advancement should be hidden or not.
                false
        );
        builder.addCriterion("craft_blasting_rod", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLASTING_ROD));

        builder.requirements(AdvancementRequirements.allOf(List.of("craft_blasting_rod")));

        builder.rewards(AdvancementRewards.Builder.experience(100));

        builder.save(saver, ResourceLocation.fromNamespaceAndPath(MythicAscension.MOD_ID, "blasting_rod_advancement"), existingFileHelper);
    }
}
