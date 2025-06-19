package com.fight.backupnoti;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BackupNoti extends JavaPlugin {
    private boolean isShutdownWarningActive = false;

    @Override
    public void onEnable() {
        getLogger().info("\u001B[36m[BackupNoti] is enabled.\u001B[0m");
        getCommand("testbackup").setExecutor(new TestBackupCommand(this));

        // รอให้ถึงวินาทีที่ 0 ก่อนเริ่ม loop หลัก
        new BukkitRunnable() {
            @Override
            public void run() {
                int second = Calendar.getInstance().get(Calendar.SECOND);
                if (second == 0) {
                    startMainBroadcastLoop();
                    this.cancel();
                }
            }
        }.runTaskTimer(this, 0L, 10L); // เช็คทุก 0.5 วินาที
    }

    @Override
    public void onDisable() {
        getLogger().info("\u001B[36m[BackupNoti] is disabled.\u001B[0m");
    }

    private void startMainBroadcastLoop() {
        new BukkitRunnable() {
            @Override
            public void run() {
                checkTimeAndBroadcast();
            }
        }.runTaskTimer(this, 0L, 20L); // ทุก 1 วินาที
    }

    private void checkTimeAndBroadcast() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

     
        if (hour == 23 && minute == 55 && second == 0) {
            if (!isShutdownWarningActive) {
                startShutdownWarning();
            }
        }
    }

    private void broadcastShutdownWarning(int secondsLeft) {
        String unit = (secondsLeft >= 60) ? "minute" : "second";
        int display = (secondsLeft >= 60) ? secondsLeft / 60 : secondsLeft;
        String plural = (display > 1 ? "s" : "");

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
            player.sendTitle("§c⚠ Server Shutdown","§eIn " + display + " " + unit + plural + "\n§eFor Daily Backup",10, 40, 10);
            getLogger().info("Shutdown Warning In "+display);
        });
    }

  public void startShutdownWarning() {
    isShutdownWarningActive = true;

    new BukkitRunnable() {
        int secondsLeft = 300; // 5 นาที

        @Override
        public void run() {
            // เตือนเมื่อเหลือ 5, 4, 3, 2, 1 นาที
            if (secondsLeft == 300 || secondsLeft == 240 || secondsLeft == 180 ||
                secondsLeft == 120 || secondsLeft == 60) {
                broadcastShutdownWarning(secondsLeft);
            }

            // เตือนทุกวินาทีเมื่อเหลือ ≤ 30 วินาที
            if (secondsLeft <= 30 && secondsLeft > 0) {
                broadcastShutdownWarning(secondsLeft);
            }

            // วินาทีสุดท้าย
            if (secondsLeft == 0) {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.playSound(player.getLocation(), "entity.ender_dragon.growl", 1.0f, 1.0f);
                    player.sendTitle("§4⚠ SHUTDOWN NOW", "§cDaily Backup in progress", 10, 60, 10);
                });
                Bukkit.broadcastMessage("§4[SERVER SHUTDOWN]§r §cServer is shutting down NOW for daily backup!");
                this.cancel();
                return;
            }

            secondsLeft--;
        }
    }.runTaskTimer(this, 0L, 20L); // ทุก 1 วินาที
}

}
