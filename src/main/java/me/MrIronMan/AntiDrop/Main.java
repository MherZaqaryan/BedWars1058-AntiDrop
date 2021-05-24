package me.MrIronMan.AntiDrop;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    public BedWars bedwarsAPI;

    public Metrics metrics;
    public final int bStatsId = 11188;

    @Override
    public void onEnable() {
        loadBedWars1058();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.isCancelled()) return;
        Player player = e.getPlayer();
        if (!bedwarsAPI.getArenaUtil().isPlaying(player)) return;
        if (!bedwarsAPI.getArenaUtil().getArenaByPlayer(player).getStatus().equals(GameState.playing)) return;
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (!block.getType().equals(Material.AIR)) return;
        e.setCancelled(true);
    }

    public void loadBedWars1058() {
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058")) {
            getLogger().severe("BedWars1058 was not found. Disabling addon...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        this.metrics = new Metrics(this, bStatsId);
        bedwarsAPI = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
    }

}
