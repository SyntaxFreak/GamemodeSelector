package net.b1naryfr3ak.gamemodeselector;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class GmSelector extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory.getTitle() == "Gamemode Selector")) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();

        if (item.getType() == Material.MOB_SPAWNER) {
            if (player.hasPermission("selector.creative")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(ChatColor.GREEN + "Your gamemode has been swapped to creative!");
            } else {
                player.sendMessage(ChatColor.RED + "You do not have the required permission to do this!");
            }
        } else if (item.getType() == Material.DIAMOND_SWORD) {
            if (player.hasPermission("selector.survival")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(ChatColor.GREEN + "Your gamemode has been swapped to survival!");
            } else {
                player.sendMessage(ChatColor.RED + "You do not have the required permission to do this!");
            }
        }
        event.setCancelled(true);
        player.closeInventory();
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Inventory inventory = getServer().createInventory(null, 27, "Gamemode Selector");
        ItemStack sword = craftItem(Material.DIAMOND_SWORD, ChatColor.RED + "Survival");
        ItemStack spawner = craftItem(Material.MOB_SPAWNER, ChatColor.BLUE + "Creative");

        inventory.setItem(11, spawner);
        inventory.setItem(13, sword);
        ((Player) sender).openInventory(inventory);
        return true;
    }

    private ItemStack craftItem(Material item, String name) {
        return createItem(new ItemStack(item), name);
    }

    private ItemStack createItem(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}