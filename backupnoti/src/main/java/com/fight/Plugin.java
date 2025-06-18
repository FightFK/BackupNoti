package com.fight;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * backupnoti java plugin
 */
public class Plugin extends JavaPlugin
{
  private static final Logger LOGGER=Logger.getLogger("backupnoti");

  public void onEnable()
  {
    LOGGER.info("backupnoti enabled");
  }

  public void onDisable()
  {
    LOGGER.info("backupnoti disabled");
  }
}
