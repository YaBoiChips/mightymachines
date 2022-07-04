package yaboichips.mightymachines.common.items;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import yaboichips.mightymachines.common.capabilities.IPackerCapability;
import yaboichips.mightymachines.common.capabilities.PackerCap;
import yaboichips.mightymachines.core.MMCapabilities;

import static yaboichips.mightymachines.common.capabilities.IPackerCapability.PACKING_BLOCKS;

public class Boots extends ArmorItem {
    public boolean hasPackingCapabilities;


    public Boots(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        initCapabilities(stack, stack.getTag().copy());
        if (stack.getCapability(MMCapabilities.PACKER_CAPABILITY).isPresent() && player.isCrouching()) {
            if (PACKING_BLOCKS.get(world.getBlockState(player.getOnPos())) != null) {
                world.setBlock(player.getOnPos(), PACKING_BLOCKS.get(world.getBlockState(player.getOnPos())), 2);
            }
        }
    }


//    @Nullable
//    @Override
//    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
//        System.out.println(hasPackingCapabilities);
//        IPackerCapability packer = new PackerCap(stack);
//        if (hasPackingCapabilities) {
//                return new ICapabilityProvider() {
//                    @NotNull
//                    @Override
//                    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//                        if (cap == MMCapabilities.PACKER_CAPABILITY) {
//                            return LazyOptional.of(() -> (T) packer);
//                        }
//                        return LazyOptional.empty();
//                    }
//                };
//            }
//        return super.initCapabilities(stack, nbt);
//    }
}
