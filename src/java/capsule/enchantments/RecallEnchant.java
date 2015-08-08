package capsule.enchantments;

import java.util.List;

import capsule.Helpers;
import capsule.items.CapsuleItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class RecallEnchant extends Enchantment {

	protected RecallEnchant(int enchID, ResourceLocation enchName, int enchWeight, EnumEnchantmentType enchType) {
		super(enchID, enchName, enchWeight, enchType);
		this.setName("recall");
		
		Enchantment.addToBookList(this);
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return canApplyAtEnchantingTable(stack);
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof CapsuleItem || this.type != null && super.canApplyAtEnchantingTable(stack);
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 1;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel)
    {
        return this.getMinEnchantability(enchantmentLevel) + 40;
    }
	
	@Override
	public int getMaxLevel() {
		return 1;
	}

	public void pickupItemBack(EntityItem entity, EntityPlayer player) {

		if (player != null) {
			entity.setNoPickupDelay();
			entity.onCollideWithPlayer(player);
		}

	}

	@SubscribeEvent
	public void onWorldTickEvent(WorldTickEvent wte) {

		if (wte.side == Side.CLIENT || wte.phase != Phase.END)
			return;

		WorldServer world = (WorldServer) wte.world;
		List<EntityItem> recallEntities = world.getEntities(EntityItem.class, Helpers.hasRecallEnchant);
		for (EntityItem entity : recallEntities) {
			if (entity.getThrower() != null && (entity.isCollided || entity.isInLava() || entity.isInWater())) {
				// give the item a last tick
				entity.onUpdate();
				// then recall to inventory
				this.pickupItemBack(entity, world.getPlayerEntityByName(entity.getThrower()));
			}
		}
	}

}
