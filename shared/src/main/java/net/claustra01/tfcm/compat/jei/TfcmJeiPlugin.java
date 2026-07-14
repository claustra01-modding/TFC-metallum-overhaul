package net.claustra01.tfcm.compat.jei;

import java.util.Collection;
import java.util.List;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;
import net.claustra01.tfcm.TfcmItems;
import net.claustra01.tfcm.TfcmMod;
import net.claustra01.tfcm.TfcmPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class TfcmJeiPlugin implements IModPlugin {
    private static final ResourceLocation UID = TfcmPlatform.id(TfcmMod.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        final IIngredientManager ingredientManager = jeiRuntime.getIngredientManager();
        final Collection<ItemStack> existing = ingredientManager.getAllItemStacks();
        final List<ItemStack> toAdd = TfcmItems.ITEMS.getEntries().stream()
            .map(entry -> entry.get().getDefaultInstance())
            .filter(stack -> !stack.isEmpty())
            .filter(stack -> !containsEquivalent(existing, stack))
            .toList();

        if (!toAdd.isEmpty()) {
            ingredientManager.addIngredientsAtRuntime(VanillaTypes.ITEM_STACK, toAdd);
        }
    }

    private static boolean containsEquivalent(Collection<ItemStack> stacks, ItemStack target) {
        for (ItemStack stack : stacks) {
            if (TfcmPlatform.itemStacksEquivalent(stack, target)) {
                return true;
            }
        }
        return false;
    }
}
