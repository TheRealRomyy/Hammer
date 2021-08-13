package fr.rome.hammerforsiri;

import fr.rome.hammerforsiri.commands.GiveHammerCommand;
import fr.rome.hammerforsiri.listeners.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {

    public String prefix = Objects.requireNonNull(this.getConfig().getString("prefix")).replace("&", "§") + " ";
    public String hammerName = Objects.requireNonNull(this.getConfig().getString("hammerName")).replace("&", "§");

    @Override
    public void onEnable() {
        System.out.println("[Hammer] Plugin successfully loaded !");

        saveDefaultConfig();

        Objects.requireNonNull(getCommand("hammer")).setExecutor(new GiveHammerCommand(this));

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new BlockBreakListener(this), this);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getHammerName() {
        return hammerName;
    }

    public ItemStack buildHammer() {
        ItemStack hammer = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta hammerM = hammer.getItemMeta();

        assert hammerM != null;
        hammerM.setDisplayName(this.getHammerName());
        hammer.setItemMeta(hammerM);

        return hammer;
    };

    public ItemStack buildFakeHammer() {
        ItemStack hammer = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta hammerM = hammer.getItemMeta();

        assert hammerM != null;
        hammerM.setDisplayName("§cFake Hammer");
        hammer.setItemMeta(hammerM);

        return hammer;
    };
}
