package ac.grim.grimac.checks.impl.inventory;

import ac.grim.grimac.checks.Check;
import ac.grim.grimac.checks.CheckData;
import ac.grim.grimac.checks.type.PacketCheck;
import ac.grim.grimac.player.GrimPlayer;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;

@CheckData(name = "InventoryB", description = "Closed inventory while moving")
public class InventoryB extends Check implements PacketCheck {
    public InventoryB(GrimPlayer player) {
        super(player);
    }

    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.CLOSE_WINDOW) return;
        if (player.serverOpenedInventoryThisTick) return;

        String verbose = InventoryA.getVerbose(player);
        if (verbose.isEmpty()) return;

        // Don't cancel this packet, because it won't do anything except for making chests
        // look like they are still open (desynced),
        // and it can cause incompatibility issues with plugins
        flagAndAlert(verbose);
    }
}
