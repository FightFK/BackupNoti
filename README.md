# 🛡️ BackupNoti

**BackupNoti** is a simple Minecraft plugin for Spigot/Paper servers that sends **shutdown notifications** to players before the server stops — especially useful when used in combination with **Unix/Linux crontab** to automate backups.

---

## 🔧 Features

- Notifies players **before server shutdown**
- Divides the notification into **2 phases**:
  - ⏱️ **Phase 1:** Minute countdown (default: 2 minutes)
  - ⌛ **Phase 2:** 30-second countdown with second-by-second alerts
- Designed to work alongside a scheduled server shutdown via `crontab` (e.g., for automatic world backups)

---

## 🖥️ How It Works

### 1. Install the plugin

Place the compiled `.jar` file into your server's `plugins/` directory and restart the server.
