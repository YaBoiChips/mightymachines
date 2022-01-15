package yaboichips.mightymachines.common.tile;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import yaboichips.mightymachines.common.blocks.ManualSmasherBlock;
import yaboichips.mightymachines.common.recipes.SmashingRecipe;
import yaboichips.mightymachines.common.tile.menus.ManualSmasherMenu;
import yaboichips.mightymachines.core.MMBlockEntities;
import yaboichips.mightymachines.core.MMRecipes;
import yaboichips.mightymachines.util.BlockEntityPacketHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ManualSmasherTE extends RandomizableContainerBlockEntity implements BlockEntityPacketHandler, WorldlyContainer {
    private static final int[] SLOTS_FOR_UP = new int[]{0};
    private static final int[] SLOTS_FOR_DOWN = new int[]{1};
    private static final int[] SLOTS_FOR_SIDES = new int[]{0};
    private final RecipeType<? extends SmashingRecipe> recipeType;
    private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap<>();
    private final IItemHandlerModifiable items = createHandler();
    public int work;
    protected int numPlayersUsing;
    private NonNullList<ItemStack> contents = NonNullList.withSize(2, ItemStack.EMPTY);
    private final LazyOptional<IItemHandlerModifiable> itemHandler = LazyOptional.of(() -> items);


    public ManualSmasherTE(BlockPos pos, BlockState state) {
        super(MMBlockEntities.MANUAL_SMASHER, pos, state);
        recipeType = MMRecipes.SMASHING;
    }

    //Controllers
    public static void tick(Level world, BlockPos pos, BlockState state, ManualSmasherTE tile) {
        makePlates(tile, world);
    }

    public static void makePlates(ManualSmasherTE tile, Level world) {
        ItemStack base = tile.contents.get(0);
        if (!base.isEmpty()) {
            Recipe<?> recipe = world.getRecipeManager().getRecipeFor((RecipeType<SmashingRecipe>) tile.recipeType, tile, world).orElse(null);
            int i = tile.getMaxStackSize();
            if (tile.getWork() == 4) {
                world.playSound(null, tile.getBlockPos(), SoundEvents.NETHER_BRICKS_PLACE, SoundSource.BLOCKS, 1, 1);
                tile.smash(recipe, tile.contents, i);
                tile.setRecipeUsed(recipe);
                tile.setWork(5);
            }
        }
        if (tile.getWork() == 5) {
            tile.setWork(0);
        }
    }

    //Container
    @Override
    public NonNullList<ItemStack> getItems() {
        return this.contents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.contents = itemsIn;
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.smasher_container");
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new ManualSmasherMenu(id, player, this);
    }

    @Override
    public int getContainerSize() {
        return 2;
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.numPlayersUsing = type;
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            --this.numPlayersUsing;
            this.onOpenOrClose();
        }
    }

    protected void onOpenOrClose() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof ManualSmasherBlock) {
            this.level.blockEvent(this.worldPosition, block, 1, this.numPlayersUsing);
            this.level.updateNeighborsAt(this.worldPosition, block);
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nonnull Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return super.getCapability(cap);
    }

    private IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (itemHandler != null) {
            itemHandler.invalidate();
        }
    }

    //nbt
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbtTag = super.getUpdateTag();
        return nbtTag;
    }


    @Override
    public void handleClientPacketNoTypeCheck(ClientboundBlockEntityDataPacket packet) {
        load(packet.getTag());
    }

    @Override
    public CompoundTag save(CompoundTag compound) {
        super.save(compound);
        compound.putInt("Work", this.getWork());
        CompoundTag compoundtag = new CompoundTag();
        this.recipesUsed.forEach((nbt, string) -> {
            compoundtag.putInt(nbt.toString(), string);
        });
        compound.put("RecipesUsed", compoundtag);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.contents);
        }
        return compound;
    }

    @Override
    public void load(CompoundTag compound) {
        this.setWork(compound.getInt("Work"));
        super.load(compound);
        CompoundTag compoundtag = compound.getCompound("RecipesUsed");
        for (String s : compoundtag.getAllKeys()) {
            this.recipesUsed.put(new ResourceLocation(s), compoundtag.getInt(s));
        }
        if (!this.tryLoadLootTable(compound)) {
            ContainerHelper.loadAllItems(compound, this.contents);
        }
    }

    private boolean smash(@Nullable Recipe<?> recipe, NonNullList<ItemStack> stack, int i) {
        if (recipe != null) {
            ItemStack itemstack = stack.get(0);
            ItemStack itemstack1 = ((Recipe<WorldlyContainer>) recipe).assemble(this);
            ItemStack itemstack2 = stack.get(1);
            if (itemstack2.isEmpty()) {
                stack.set(1, itemstack1.copy());
            } else if (itemstack2.is(itemstack1.getItem())) {
                itemstack2.grow(itemstack1.getCount());
            }
            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    public void setRecipeUsed(@Nullable Recipe<?> p_58345_) {
        if (p_58345_ != null) {
            ResourceLocation resourcelocation = p_58345_.getId();
            this.recipesUsed.addTo(resourcelocation, 1);
        }
    }

    //Getters/Setters
    public int getWork() {
        return work;
    }

    public void setWork(int work) {
        this.work = work;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.DOWN) {
            return SLOTS_FOR_DOWN;
        } else {
            return direction == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES;
        }
    }

    @Override
    public boolean canPlaceItemThroughFace(int i, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(i, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return true;
    }
}