package net.runelite.client.plugins.microbot.bracelet;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.plugins.microbot.bracelet.enums.Bracelets;

@ConfigGroup("Skilling")
public interface braceletConfig extends Config {
    @ConfigItem(
            keyName = "guide",
            name = "How to use",
            description = "How to use this plugin",
            position = 0,
            section = generalSection
    )
    default String GUIDE() {
        return "Stand near bank in Edgeville with required materials in bank and an empty inventory.";
    }

    @ConfigSection(
            name = "Bracelet",
            description = "What bracelet the script will make.",
            position = 0
    )
    String generalSection = "general";

    @ConfigItem(
            keyName = "Bracelet",
            name = "Bracelet",
            description = "Choose the type of Bracelet to make.",
            position = 0,
            section = generalSection
    )
    default Bracelets braceletType()
    {
        return Bracelets.NONE;
    }
}
