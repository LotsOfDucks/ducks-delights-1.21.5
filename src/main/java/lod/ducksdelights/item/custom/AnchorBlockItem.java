package lod.ducksdelights.item.custom;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.BlocksAttacksComponent;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.Objects;

public class AnchorBlockItem extends BlockItem {


    public AnchorBlockItem(Block block, Settings settings, BlockPos placementPos, ItemUsageContext context) {
        super(block, settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        ConsumableComponent consumableComponent = itemStack.get(DataComponentTypes.CONSUMABLE);
        if (consumableComponent != null) {
            return consumableComponent.consume(user, itemStack, hand);
        } else {
            EquippableComponent equippableComponent = itemStack.get(DataComponentTypes.EQUIPPABLE);
            if (equippableComponent != null && equippableComponent.swappable()) {
                return equippableComponent.equip(itemStack, user);
            } else {
                BlocksAttacksComponent blocksAttacksComponent = itemStack.get(DataComponentTypes.BLOCKS_ATTACKS);
                if (blocksAttacksComponent != null) {
                    user.setCurrentHand(hand);
                    return ActionResult.CONSUME;
                } else {
                    return ActionResult.PASS;
                }
            }
        }
    }
}
