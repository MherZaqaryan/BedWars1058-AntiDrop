package club.mher.antidrop;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.arena.GameState;
import org.bstats.bukkit.Metrics;
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
        if (!Bukkit.getPluginManager().isPluginEnabled("BedWars1058")) {
            getLogger().severe("BedWars1058 was not found, disabling...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        bedwarsAPI = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        Bukkit.getPluginManager().registerEvents(this, this);
        this.metrics = new Metrics(this, bStatsId);
    }

    @EventHandler(ignoreCancelled = true)
    public void onItemDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (!bedwarsAPI.getArenaUtil().isPlaying(player)) {
            return;
        }
        if (!bedwarsAPI.getArenaUtil().getArenaByPlayer(player).getStatus().equals(GameState.playing)) {
            return;
        }
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (!block.getType().equals(Material.AIR)) {
            return;
        }
        e.setCancelled(true);
    }

}