package me.swipez.farmingop;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class FarmListener implements Listener {

    Farmingop plugin;

    public FarmListener(Farmingop plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onFarm(BlockBreakEvent e){
        if (plugin.gamestarted) {
            String data = e.getBlock().getBlockData().toString();
            Bukkit.broadcastMessage(data);
            Bukkit.broadcastMessage(e.getBlock().getType() + "");
            if (shouldDrop(e)){
                List<String> mats = plugin.getConfig().getStringList("fallitems");
                List<String> counts = plugin.getConfig().getStringList("fallitemscount");
                List<String> enchants = plugin.getConfig().getStringList("enchantslist");
                List<String> peffects = plugin.getConfig().getStringList("peffects");
                int min = 0;
                int max = mats.size() - 1;
                double random = Math.random() * (max - min + 1) + min;
                int stackcount = Integer.parseInt(counts.get((int) random));
                ItemStack ritem = new ItemStack(Material.valueOf(mats.get((int) random).toUpperCase()), stackcount);
                if (ritem.getType() == Material.ENCHANTED_BOOK) {
                    ItemMeta meta = ritem.getItemMeta();
                    EnchantmentStorageMeta emeta = (EnchantmentStorageMeta) meta;
                    int mine = 0;
                    int maxe = enchants.size() - 1;
                    double rench = Math.random() * (maxe - mine + 1) + mine;
                    emeta.addStoredEnchant(Enchantment.getByKey(NamespacedKey.minecraft(enchants.get((int) rench).toLowerCase())), 10, true);
                    ritem.setItemMeta(emeta);
                }
                if (ritem.getType() == Material.POTION) {
                    ItemMeta meta = ritem.getItemMeta();
                    PotionMeta pmeta = (PotionMeta) meta;
                    int mine = 0;
                    int maxe = peffects.size() - 1;
                    double rench = Math.random() * (maxe - mine + 1) + mine;
                    pmeta.addCustomEffect(new PotionEffect((PotionEffectType.getByName(peffects.get((int) rench).toUpperCase())), 1200, 2), true);
                    pmeta.setColor(Color.YELLOW);
                    pmeta.setDisplayName("OP Potion");
                    ritem.setItemMeta(pmeta);
                }
                e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), ritem);
                e.getBlock().setType(Material.AIR);
                e.setCancelled(true);
            }
        }
    }
    private boolean shouldDrop(BlockBreakEvent e) {
        boolean bool = false;
        List<Material> age7 = new ArrayList<>();
        age7.add(Material.WHEAT);
        age7.add(Material.CARROTS);
        age7.add(Material.POTATOES);
        List<Material> age3 = new ArrayList<>();
        age3.add(Material.BEETROOTS);
        age3.add(Material.NETHER_WART);
        List<Material> stemmed = new ArrayList<>();
        stemmed.add(Material.PUMPKIN);
        stemmed.add(Material.MELON);
        List<Material> stems = new ArrayList<>();
        stems.add(Material.ATTACHED_PUMPKIN_STEM);
        stems.add(Material.ATTACHED_MELON_STEM);

        return age7.contains(e.getBlock().getType()) && e.getBlock().getBlockData().toString().contains("age=7") || age3.contains(e.getBlock().getType()) && e.getBlock().getBlockData().toString().contains("age=3") || (stemmed.contains(e.getBlock().getType()) && ((stems.contains(e.getBlock().getRelative(BlockFace.EAST).getType()) && e.getBlock().getRelative(BlockFace.EAST).getBlockData().toString().contains("facing=west")) || (stems.contains(e.getBlock().getRelative(BlockFace.WEST).getType()) && e.getBlock().getRelative(BlockFace.WEST).getBlockData().toString().contains("facing=east")) || (stems.contains(e.getBlock().getRelative(BlockFace.NORTH).getType()) && e.getBlock().getRelative(BlockFace.NORTH).getBlockData().toString().contains("facing=south")) || (stems.contains(e.getBlock().getRelative(BlockFace.SOUTH).getType()) && e.getBlock().getRelative(BlockFace.SOUTH).getBlockData().toString().contains("facing=north"))));
    }
}

