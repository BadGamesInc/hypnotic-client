package badgamesinc.hypnotic.util;

public class Enchanter {

	public static void addEnchantment(ItemStack itemStack, Enchantment enchantment, int level) {
        CompoundTag tag = itemStack.getOrCreateTag();
        ListTag listTag;

        // not skidded from meteor lmao
        if (!tag.contains("Enchantments", 9)) {
            listTag = new ListTag();
            tag.put("Enchantments", listTag);
        }
        else {
            listTag = tag.getList("Enchantments", 10);
        }

        String enchId = Registry.ENCHANTMENT.getId(enchantment).toString();

        for (Tag _t : listTag) {
            CompoundTag t = (CompoundTag) _t;

            if (t.getString("id").equals(enchId)) {
                t.putShort("lvl", (short) level);
                return;
            }
        }

        CompoundTag enchTag = new CompoundTag();
        enchTag.putString("id", enchId);
        enchTag.putShort("lvl", (short) level);

        listTag.add(enchTag);
    }
}
