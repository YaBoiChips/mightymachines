package yaboichips.mightymachines.util;

import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;

public interface BlockEntityPacketHandler {

    void handleClientPacketNoTypeCheck(ClientboundBlockEntityDataPacket sUpdateTileEntityPacket);
}
