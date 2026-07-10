package com.SprintXXL.primitivematter.content.client.render.devices.pipes;

import com.SprintXXL.primitivematter.content.devices.pipe.BlockPipe;
import com.SprintXXL.primitivematter.content.devices.pipe.raytrace.PipeHitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import static com.SprintXXL.primitivematter.content.devices.pipe.raytrace.PipeRayTracer.tracePipeParts;

@Mod.EventBusSubscriber(Side.CLIENT)
public class PipeHighlightHandler {

    @SubscribeEvent
    public static void onDrawBlockHighlight(DrawBlockHighlightEvent event) {

        RayTraceResult target = event.getTarget();

        if (target == null || target.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }

        BlockPos pos = target.getBlockPos();
        EntityPlayer player = event.getPlayer();
        World world = player.world;

        if (!(world.getBlockState(pos).getBlock() instanceof BlockPipe)) {
            return;
        }

        float partialTicks = event.getPartialTicks();

        Vec3d start = player.getPositionEyes(partialTicks);

        double reach = Minecraft.getMinecraft().playerController.getBlockReachDistance();

        Vec3d end = start.add(player.getLook(partialTicks).scale(reach));

        PipeHitResult pipeHit = tracePipeParts(world, pos, start, end);

        if (pipeHit == null) {
            return;
        }

        event.setCanceled(true);

        drawHighlight(pipeHit.getBox(), pos, player, partialTicks);
    }

    private static void drawHighlight(AxisAlignedBB localBox, BlockPos pos, EntityPlayer player, float partialTicks) {

        double playerX = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
        double playerY = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
        double playerZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

        AxisAlignedBB renderBox = localBox.offset(pos).grow(0.002D).offset(-playerX, -playerY, -playerZ);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA,
                GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE,
                GlStateManager.DestFactor.ZERO
        );

        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        RenderGlobal.drawSelectionBoundingBox(
                renderBox,
                0.0F,
                0.0F,
                0.0F,
                0.4F
        );

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
