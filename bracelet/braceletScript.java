package net.runelite.client.plugins.microbot.bracelet;

import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.client.plugins.microbot.Microbot;
import net.runelite.client.plugins.microbot.Script;
import net.runelite.client.plugins.microbot.bracelet.enums.BraceletStates;
import net.runelite.client.plugins.microbot.util.bank.Rs2Bank;
import net.runelite.client.plugins.microbot.util.gameobject.Rs2GameObject;
import net.runelite.client.plugins.microbot.util.inventory.Rs2Inventory;
import net.runelite.client.plugins.microbot.util.menu.NewMenuEntry;
import net.runelite.client.plugins.microbot.util.player.Rs2Player;
import net.runelite.client.plugins.microbot.util.widget.Rs2Widget;
import java.util.concurrent.TimeUnit;

public class braceletScript extends Script {
    public static double version = 1.0;
    public static BraceletStates state = BraceletStates.bank;
    public static String braceletMould = "Bracelet mould";
    public static String goldBracelet = "Gold bracelet";

    private String braceletType;
    private int levelRequired;
    private String gem;
    private int widgetId;
    private String bar;

    public boolean run(braceletConfig config) {
        braceletType = config.braceletType().getName();
        levelRequired = config.braceletType().getLevelRequired();
        gem = config.braceletType().getGem();
        widgetId = config.braceletType().getWidgetId();
        bar = config.braceletType().getBar();

        Microbot.enableAutoRunOn = false;
        mainScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() -> {
            if (!super.run()) return;
            try {
                if (!Microbot.hasLevel(levelRequired, Skill.CRAFTING)) {
                    Microbot.showMessage("Crafting level to low to craft " + config.braceletType().getName());
                    shutdown();
                    return;
                }

                switch (state) {
                    case bank:
                        collectFromBank();
                        break;
                    case furnace:
                        useFurnace();
                        break;
                    case smelt:
                        smelt();
                        break;
                    default:
                        break;
                }

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
        return true;
    }

    public void collectFromBank() {
        if (!Rs2Bank.isOpen()) {
            Rs2Bank.useBank();
            System.out.println("Used Bank");
        }

        if (Rs2Inventory.hasItem(bar) && Rs2Inventory.hasItem(braceletMould)) {
            if (Rs2Inventory.hasItemAmount(gem, 13)) {
                state = BraceletStates.furnace;
            } else if (braceletType.equals(goldBracelet)) {
                state = BraceletStates.furnace;
            }
        }

        if (!Rs2Inventory.hasItem(braceletMould)) {

            Rs2Bank.withdrawItem(braceletMould);
        }
        if (Rs2Inventory.hasItem(braceletType)) {

            Rs2Bank.depositAll(braceletType);
        }

        if (!Rs2Inventory.isFull() && !Rs2Inventory.hasItemAmount(gem, 13)) {

            if (braceletType.equals(goldBracelet)) {
                System.out.println("Picked up gold");
                Rs2Bank.withdrawAll("Gold bar");
                state = BraceletStates.furnace;
            } else {
                System.out.println("Picked up gold & " + gem);
                Rs2Bank.withdrawX(bar, 13);
                Rs2Bank.withdrawX(gem, 13);
                state = BraceletStates.furnace;
            }
        }
    }
    public void useFurnace() {
        state = BraceletStates.doing;

        GameObject furnace = Rs2GameObject.findObject("Furnace");

        Rs2Player.toggleRunEnergy(true);
        Rs2GameObject.interact(furnace, "smelt");

        sleepUntil(()-> Rs2Widget.hasWidget("What would you like to make?"));

        state = BraceletStates.smelt;
    }
    public void smelt() {

        if (!Rs2Inventory.hasItem(bar)) {
            state = BraceletStates.bank;
        }

        if (Rs2Widget.hasWidget("What would you like to make?") && !Rs2Inventory.hasItem(goldBracelet)) {
            if (Rs2Inventory.hasItem(bar)) {
                Widget widget = Rs2Widget.getWidget(widgetId);
                Microbot.doInvoke(new NewMenuEntry("Make <col=ff9040>"+braceletType+"</col>", "", 1, MenuAction.CC_OP, -1, widgetId, false), widget.getBounds());
            }
            System.out.println("smelt");
        }
    }
}