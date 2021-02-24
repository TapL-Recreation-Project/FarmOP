package me.swipez.farmingop;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class AnvilListener implements Listener {
    @EventHandler
    public void onAnvilMake(PrepareAnvilEvent e){
        int bookslot = 0;
        int otheritem = 0;
        for (int i=0;i<e.getInventory().getSize();i++){
            if (e.getInventory().getItem(i) != null){
                if (e.getInventory().getItem(i).getType() == Material.ENCHANTED_BOOK){
                    bookslot = i;
                }
            }
        }
        EnchantmentStorageMeta emeta = (EnchantmentStorageMeta) e.getInventory().getItem(bookslot).getItemMeta();
        if (bookslot == 0){
            otheritem = 1;
        }
        else if (bookslot == 1){
            otheritem = 0;
        }
        ItemStack item = e.getInventory().getItem(otheritem);
        EnchantFix(item, emeta, e);
    }
    public void EnchantFix(ItemStack item, EnchantmentStorageMeta enchantmentStorageMeta, PrepareAnvilEvent e){
        ItemMeta itemmeta = item.getItemMeta();
        Map<Enchantment, Integer> bookenchantmentlist = enchantmentStorageMeta.getStoredEnchants();
        Map<Enchantment, Integer> itemenchantlist = item.getEnchantments();
        List<Enchantment> booklist = (List<Enchantment>) bookenchantmentlist.keySet();
        List<Enchantment> itemlist = (List<Enchantment>) itemenchantlist.keySet();
        if (item.containsEnchantment(booklist.get(0))){
            int itemenchantvalue = itemenchantlist.get(booklist.get(0));
            itemmeta.addEnchant(booklist.get(0), itemenchantvalue+bookenchantmentlist.get(booklist.get(0)), true);
        }
        else {
            itemmeta.addEnchant(booklist.get(0), bookenchantmentlist.get(booklist.get(0)), true);
        }
        item.setItemMeta(itemmeta);
        if (e.getResult() == null){
            e.setResult(item);
        }
    }
}
