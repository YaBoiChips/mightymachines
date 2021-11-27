package yaboichips.mightymachines.common.items;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import yaboichips.mightymachines.core.MMItems;
import yaboichips.mightymachines.core.MMKeybinds;

public class Jetpack extends ArmorItem {
    public boolean isFlying = false;

    public Jetpack(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int p_41407_, boolean p_41408_) {
        Player player = (Player) entity;
        setFlying(MMKeybinds.JETPACK_UP.isDown());
        if (isFlying() && player.getItemBySlot(EquipmentSlot.CHEST).getItem() == MMItems.JETPACK) {
            if (world.isClientSide) {
                float f7 = player.getYRot();
                float f = player.getXRot();
                float f1 = -Mth.sin(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                float f2 = -Mth.sin(f * ((float) Math.PI / 180F));
                float f3 = Mth.cos(f7 * ((float) Math.PI / 180F)) * Mth.cos(f * ((float) Math.PI / 180F));
                float f4 = Mth.sqrt(f1 * f1 + f2 * f2 + f3 * f3);
                float i = f1 * (0.05f / f4);
                float k = f3 * (0.05f / f4);
                player.push(i, 0.1, k);
            }
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.CHICKEN_EGG, SoundSource.AMBIENT, 1, 1);
            player.fallDistance = 0;
        }
        super.inventoryTick(stack, world, entity, p_41407_, p_41408_);
    }

    public boolean isFlying() {
        return isFlying;
    }

    public void setFlying(boolean flying) {
        isFlying = flying;
    }
}
