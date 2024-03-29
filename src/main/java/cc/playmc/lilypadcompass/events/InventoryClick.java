package cc.playmc.lilypadcompass.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import cc.playmc.lilypadcompass.LilyPadCompass;

public class InventoryClick implements Listener {

	private LilyPadCompass plugin;

	public InventoryClick(LilyPadCompass plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		ItemStack item = e.getCurrentItem();

		Player p = (Player) e.getWhoClicked();

		if (e.getInventory().getName().equals(plugin.compass.getName())) {
			e.setCancelled(true);
			if (e.getCurrentItem() != null) {
				if (item.getItemMeta() != null) {
					if (item.getItemMeta().getDisplayName() != null) {
						String name = item.getItemMeta().getDisplayName();
						String striped = ChatColor.stripColor(name);

						String command = plugin.commands.get(striped);

						if (plugin.message.get(striped) != null) {
							p.sendMessage(plugin.message.get(striped));
						}

						if (!command.equalsIgnoreCase("na")) {
							p.performCommand(command);
						}

						p.closeInventory();

						if (plugin.server.get(striped) != null) {
							plugin.getLilyUtils().redirect(
									plugin.server.get(striped), p);
						}
					}
				}
			}
		}
	}
}