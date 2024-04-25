package net.runelite.client.plugins.microbot.bracelet.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Bracelets
{
    NONE("NONE", 1, "NONE", "NONE", 0),
    GOLD("Gold bracelet", 7, "NONE", "Gold bar", 29229106),
    OPAL("Opal bracelet", 22, "Opal", "Silver bar", 393235),
    Jade("Sapphire bracelet", 23, "Sapphire", "Gold bar", 29229108),
    RED_TOPAZ("Jade bracelet", 29, "Jade", "Silver bar", 393236),
    SAPPHIRE("Emerald bracelet", 30, "Emerald", "Gold bar", 29229109),
    EMERALD("Topaz bracelet", 38, "Topaz", "Silver bar", 393237),
    RUBY("Ruby bracelet", 42, "Ruby", "Gold bar", 29229110),
    DIAMOND("Diamond bracelet", 58, "Diamond", "Gold bar", 29229111),
    DRAGONSTONE("Dragonstone bracelet", 74, "Dragonstone", "Gold bar", 29229112);

    private final String name;
    private final int levelRequired;
    private final String gem;
    private final String bar;
    private final int widgetId;

    @Override
    public String toString()
    {
        return name;
    }
}

