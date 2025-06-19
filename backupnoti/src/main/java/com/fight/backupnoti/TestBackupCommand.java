package com.fight.backupnoti;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestBackupCommand implements CommandExecutor {
    private final BackupNoti plugin;

    public TestBackupCommand(BackupNoti plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.startShutdownWarning(); // เรียกฟังก์ชันจำลองการแจ้งเตือน
        sender.sendMessage("§a[Debug] Triggered backup warning manually.");
        return true;
    }
}
