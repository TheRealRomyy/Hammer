package fr.rome.hammerforsiri.commands;

import fr.rome.hammerforsiri.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveHammerCommand implements CommandExecutor {

    private final Main main;

    public GiveHammerCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player player) {

            if(!player.hasPermission("hammer.give")) {
                player.sendMessage(main.getPrefix() + "§cTu n'as pas accès a cette commande !");
                return false;
            }

            ItemStack hammer = main.buildHammer();
            player.getInventory().addItem(hammer);

            player.sendMessage(main.getPrefix() + "§aTu as reçu le hammer !");

            return true;

        } else {
            System.out.println(main.getPrefix() + "§cLa console ne peut pas faire /hammer !");
            return false;
        }
    }
}
