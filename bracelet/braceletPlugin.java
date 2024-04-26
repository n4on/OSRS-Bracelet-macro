package net.runelite.client.plugins.microbot.bracelet;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
        name = PluginDescriptor.Default + "Bracelet",
        description = "Microbot example plugin",
        tags = {"Skilling", "Bracelet", "microbot"},
        enabledByDefault = false
)
@Slf4j
public class braceletPlugin extends Plugin {
    @Inject
    private braceletConfig config;
    @Provides
    braceletConfig braceletConfig(ConfigManager configManager) {return configManager.getConfig(braceletConfig.class);}

    @Inject
    private OverlayManager overlayManager;
    @Inject
    private braceletOverlay braceletOverlay;

    @Inject
    braceletScript braceletScript;


    @Override
    protected void startUp() throws AWTException {
        if (overlayManager != null) {
            overlayManager.add(braceletOverlay);
        }
        braceletScript.run(config);
    }

    protected void shutDown() {
        braceletScript.shutdown();
        overlayManager.remove(braceletOverlay);
    }
}
