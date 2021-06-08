package net.minecraft.enchantment;

import java.util.concurrent.CopyOnWriteArrayList;

// this is fricking stupid why did i add this

public class EnchantmentManager 
{
	public CopyOnWriteArrayList<Enchantment> enchants = new CopyOnWriteArrayList<>();
	
	public EnchantmentManager()
	{
		enchants.add(Enchantment.protection);
		enchants.add(Enchantment.fireProtection);
		enchants.add(Enchantment.featherFalling);
		enchants.add(Enchantment.blastProtection);
		enchants.add(Enchantment.projectileProtection);
		enchants.add(Enchantment.respiration);
		enchants.add(Enchantment.aquaAffinity);
		enchants.add(Enchantment.thorns);
		enchants.add(Enchantment.depthStrider);
		enchants.add(Enchantment.sharpness);
		enchants.add(Enchantment.smite);
		enchants.add(Enchantment.baneOfArthropods);
		enchants.add(Enchantment.knockback);
		enchants.add(Enchantment.fireAspect);
		enchants.add(Enchantment.looting);
		enchants.add(Enchantment.efficiency);
		enchants.add(Enchantment.silkTouch);
		enchants.add(Enchantment.unbreaking);
		enchants.add(Enchantment.fortune);
		enchants.add(Enchantment.power);
		enchants.add(Enchantment.punch);
		enchants.add(Enchantment.flame);
		enchants.add(Enchantment.infinity);
		enchants.add(Enchantment.luckOfTheSea);
		enchants.add(Enchantment.lure);
	}
	
	public CopyOnWriteArrayList<Enchantment> getEnchants() 
	{
		return enchants;
	}
}
