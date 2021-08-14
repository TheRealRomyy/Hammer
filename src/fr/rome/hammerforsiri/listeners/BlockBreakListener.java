package fr.rome.hammerforsiri.listeners;

import fr.rome.hammerforsiri.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Objects;

public class BlockBreakListener implements Listener {

    private final Main main;

    public BlockBreakListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        ItemStack itemInHand = player.getItemInHand();

        if(itemInHand.hasItemMeta() && Objects.requireNonNull(itemInHand.getItemMeta()).hasDisplayName() && itemInHand.getItemMeta().getDisplayName().equals(main.getHammerName())) {

            ItemStack hammer = main.buildHammer();

            ArrayList<Location> locs = new ArrayList<Location>();

            double x = location.getX();
            double y = location.getY();
            double z = location.getZ();

            switch (getDirection(player, block)) {
                case 0: // North or South

                    locs.add(new Location(location.getWorld(), x, y+1, z));
                    locs.add(new Location(location.getWorld(), x, y-1, z));
                    locs.add(new Location(location.getWorld(), x, y, z+1));
                    locs.add(new Location(location.getWorld(), x, y, z-1));
                    locs.add(new Location(location.getWorld(), x, y+1, z+1));
                    locs.add(new Location(location.getWorld(), x, y+1, z-1));
                    locs.add(new Location(location.getWorld(), x, y-1, z+1));
                    locs.add(new Location(location.getWorld(), x, y-1, z-1));

                    break;
                case 1: // East or West

                    locs.add(new Location(location.getWorld(), x, y+1, z));
                    locs.add(new Location(location.getWorld(), x, y-1, z));
                    locs.add(new Location(location.getWorld(), x+1, y, z));
                    locs.add(new Location(location.getWorld(), x-1, y, z));
                    locs.add(new Location(location.getWorld(), x+1, y+1, z));
                    locs.add(new Location(location.getWorld(), x-1, y+1, z));
                    locs.add(new Location(location.getWorld(), x+1, y-1, z));
                    locs.add(new Location(location.getWorld(), x-1, y-1, z));

                    break;
                case 2: // Down

                    locs.add(new Location(location.getWorld(), x, y, z+1));
                    locs.add(new Location(location.getWorld(), x, y, z-1));
                    locs.add(new Location(location.getWorld(), x+1, y, z));
                    locs.add(new Location(location.getWorld(), x-1, y, z));
                    locs.add(new Location(location.getWorld(), x+1, y, z+1));
                    locs.add(new Location(location.getWorld(), x-1, y, z+1));
                    locs.add(new Location(location.getWorld(), x-1, y, z-1));
                    locs.add(new Location(location.getWorld(), x+1, y, z-1));

                    break;
                default:
                    break;
            }

            locs.forEach(location1 -> {
                Block block1 = location1.getBlock();

                if(!block1.getType().equals(Material.AIR) && !block1.getType().equals(Material.BEDROCK)) block1.breakNaturally(hammer);
            });

            if(!player.getGameMode().equals(GameMode.CREATIVE)) itemInHand.setDurability((short) (itemInHand.getDurability() + 1));
            return;
        };
    };

    private static Integer getDirection(Player player, Block block) {
        if(player.getLocation().getY() > block.getLocation().getY()) return 2; // Down
        double rot = (player.getLocation().getYaw() - 90) % 360;
        if (rot < 0) rot += 360.0;

        if (0 <= rot && rot < 67.5) {
            return 0; // North
        } else if (67.5 <= rot && rot < 157.5) {
            return 1; // East
        } else if (157.5 <= rot && rot < 247.5) {
            return 0; // South
        } else if (247.5 <= rot && rot < 337.5) {
            return 1; // West
        } else if (337.5 <= rot && rot < 360.0) {
            return 0; // North
        } else {
            return 3;
        }
    }
}
