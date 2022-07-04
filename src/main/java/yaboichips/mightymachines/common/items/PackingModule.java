package yaboichips.mightymachines.common.items;

import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static yaboichips.mightymachines.core.MMBoolMap.hasPacker;

public class PackingModule extends Item {
    public PackingModule(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        hasPacker.put(player.getUUID(), true);
        player.displayClientMessage(new TranslatableComponent(hasPacker.get(player.getUUID()).toString()), true);
        return InteractionResultHolder.consume(stack);
    }
}
