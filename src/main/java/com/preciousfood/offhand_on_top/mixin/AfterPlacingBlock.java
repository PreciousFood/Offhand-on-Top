package com.preciousfood.offhand_on_top.mixin;


import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class AfterPlacingBlock {
    @Inject(method = "postPlacement", at = @At("HEAD"))
    private void init(BlockPos pos, World world, PlayerEntity player, ItemStack stack, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (!player.isSneaking()) {
            ItemStack offhand_item = player.getOffHandStack();
            if (offhand_item.getItem() instanceof BlockItem block) {
                BlockState target_state = block.getBlock().getDefaultState();
                BlockPos target = pos.up();
                if (target_state.canPlaceAt(world, target)) {
                    world.setBlockState(target, target_state);
                    player.getOffHandStack().decrementUnlessCreative(1, player);
                }
            }
        }
    }
}