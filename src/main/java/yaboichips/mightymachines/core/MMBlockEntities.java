package yaboichips.mightymachines.core;

import com.mojang.datafixers.types.Type;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import yaboichips.mightymachines.MightyMachines;
import yaboichips.mightymachines.common.tile.GeneratorTE;
import yaboichips.mightymachines.common.tile.SmasherTE;

import java.util.ArrayList;
import java.util.List;

public class MMBlockEntities {
    public static List<BlockEntityType<?>> blockentity = new ArrayList<>();

    public static final BlockEntityType<GeneratorTE> GENERATOR = register("generator", BlockEntityType.Builder.of(GeneratorTE::new, MMBlocks.GENERATOR));
    public static final BlockEntityType<SmasherTE> SMASHER = register("smasher", BlockEntityType.Builder.of(SmasherTE::new, MMBlocks.SMASHER));


    private static <T extends BlockEntity> BlockEntityType<T> register(String key, BlockEntityType.Builder<T> builder) {
        Type<?> type = Util.fetchChoiceType(References.BLOCK_ENTITY, key);
        BlockEntityType<T> blockEntityType = builder.build(type);
        blockEntityType.setRegistryName(new ResourceLocation(MightyMachines.MOD_ID, key));
        blockentity.add(blockEntityType);
        return blockEntityType;
    }


    public static void init() {
    }
}
