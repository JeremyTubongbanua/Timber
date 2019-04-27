package me.markiscool.timber;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Set;

/**
 * Listens for block breaks
 * If any wood log is detected and breaker has permission and player is shifting, then it will timber.
 */
public class TreeBreakListener implements Listener {

    @EventHandler (priority = EventPriority.HIGH)
    public void onTreeBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.hasPermission("timber.use") && player.isSneaking()) {
            Block block = event.getBlock();
            TreeChecker tChecker = new TreeChecker();
            if(tChecker.isValidType(block.getType())) {
                Set<Block> tree = tChecker.parseTree(block);
                if(tree != null) {
                    for(Block b : tree) {
                        b.breakNaturally();
                    }
                    player.sendMessage(ChatColor.GREEN + "Timber!");
                }
            }
        }
    }

}
