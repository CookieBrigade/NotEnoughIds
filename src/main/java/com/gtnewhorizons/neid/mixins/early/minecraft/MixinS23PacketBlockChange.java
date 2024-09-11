package com.gtnewhorizons.neid.mixins.early.minecraft;

import net.minecraft.network.play.server.S23PacketBlockChange;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import io.netty.buffer.ByteBuf;

@Mixin(S23PacketBlockChange.class)
public class MixinS23PacketBlockChange {

    @Redirect(
            method = "readPacketData",
            at = @At(value = "INVOKE", target = "Lio/netty/buffer/ByteBuf;readUnsignedByte()I", ordinal = 1))
    private int redirectSecondReadUnsignedShort(ByteBuf data) {
        return data.readUnsignedShort(); // Handle the second call differently
    }

    @Redirect(
            method = "writePacketData",
            at = @At(value = "INVOKE", target = "Lio/netty/buffer/ByteBuf;writeByte(I)V", ordinal = 1))
    private void redirectSecondWriteShort(ByteBuf data, int value) {
        data.writeShort(value); // Handle the second call differently
    }
}
