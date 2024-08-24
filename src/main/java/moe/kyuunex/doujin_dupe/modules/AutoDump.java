package moe.kyuunex.doujin_dupe.modules;

import java.util.List;

import moe.kyuunex.doujin_dupe.DoujinDupeAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.InvUtils;
import meteordevelopment.meteorclient.utils.player.SlotUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerType;

public class AutoDump extends Module {
    private final SettingGroup sgDefault = settings.getDefaultGroup();
    public final Setting<Integer> rate =
        sgDefault.add(
            new IntSetting.Builder()
                .name("rate")
                .description("The rate to move items per tick.")
                .defaultValue(6)
                .sliderRange(1, 20)
                .build());
    public final Setting<List<Item>> items =
        sgDefault.add(
            new ItemListSetting.Builder()
                .name("items")
                .description("A list of items to dump.")
                .defaultValue(Items.SHULKER_BOX)
                .build());
    public final Setting<List<ScreenHandlerType<?>>> screens =
        sgDefault.add(
            new ScreenHandlerListSetting.Builder()
                .name("screens")
                .description("The screens to dump items into.")
                .defaultValue(List.of(ScreenHandlerType.GENERIC_9X3, ScreenHandlerType.GENERIC_9X6))
                .build());

    public AutoDump() {
        super(DoujinDupeAddon.CATEGORY, "auto-dump", "Automatically dump items into chests, skid of meteors but won't time you out.");
    }

    @EventHandler
    public void onTick(TickEvent.Post tickEvent) {
        if (!canUseScreen()) {
            return;
        }

        int r = 0;

        for (int i = SlotUtils.indexToId(SlotUtils.MAIN_START); i < SlotUtils.indexToId(SlotUtils.MAIN_START) + 4 * 9; i++) {
            if (r >= rate.get()) break;
            if (!mc.player.currentScreenHandler.getSlot(i).hasStack()) continue;
            if (!items.get().contains(mc.player.currentScreenHandler.getSlot(i).getStack().getItem())) continue;

            r++;
            InvUtils.shiftClick().slotId(i);
        }
    }

    public boolean canUseScreen() {
        try {
            return mc.player != null && screens.get().contains(mc.player.currentScreenHandler.getType());
        } catch (Exception e) {
            return false;
        }
    }
}
