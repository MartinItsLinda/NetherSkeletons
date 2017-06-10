package me.angrypostman.NetherSkeletons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class NetherSkeletons extends JavaPlugin {

    private static NetherSkeletons plugin = null;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        NetherSkeletons.plugin = this;

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new Listener() {

            @EventHandler(priority = EventPriority.HIGHEST)
            public void onSpawnerPlace(BlockPlaceEvent event) {

                Player player = event.getPlayer();

                Block block = event.getBlock();
                World world = block.getWorld();
                Environment environment = world.getEnvironment();

                if (block.getType() != Material.MOB_SPAWNER) return;
                if (environment != Environment.NETHER) return;

                CreatureSpawner spawner = (CreatureSpawner) block.getState();
                if (spawner.getSpawnedType() == EntityType.SKELETON) {

                    event.setCancelled(true);

                    String message = getConfig().getString("spawn_place_denied");
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));

                }

            }

        }, this);

    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(plugin);
        NetherSkeletons.plugin = null;
    }

}
