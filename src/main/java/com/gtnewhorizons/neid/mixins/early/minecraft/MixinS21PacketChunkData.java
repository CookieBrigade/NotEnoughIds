package com.gtnewhorizons.neid.mixins.early.minecraft;

import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.gtnewhorizons.neid.Constants;
import com.gtnewhorizons.neid.mixins.interfaces.IExtendedBlockStorageMixin;

@Mixin(S21PacketChunkData.class)
public class MixinS21PacketChunkData {

    @Shadow
    private static byte[] field_149286_i;

    @Inject(method = "<init>()V", at = @At(value = "RETURN"), require = 1)
    private void neid$ConstructorAddition(CallbackInfo CI) {
        field_149286_i = new byte[Constants.BYTES_PER_CHUNK];
    }

    @ModifyConstant(
            method = "func_149275_c()I",
            constant = @Constant(intValue = Constants.VANILLA_BYTES_PER_CHUNK),
            require = 1)
    private static int neid$readPacketData_Constant1(int i) {
        return Constants.BYTES_PER_CHUNK;
    }

    @ModifyConstant(
            method = "readPacketData",
            constant = @Constant(intValue = Constants.VANILLA_BYTES_PER_EBS),
            require = 1)
    private static int neid$readPacketData_Constant2(int i) {
        return Constants.BYTES_PER_EBS;
    }

    @Redirect(
            method = "func_149269_a",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/chunk/storage/ExtendedBlockStorage;getBlockLSBArray()[B"),
            require = 1)
    private static byte[] neid$func_149269_a_GetLSB(ExtendedBlockStorage ebs) {
        return ((IExtendedBlockStorageMixin) ebs).getBlockData();
    }
}
