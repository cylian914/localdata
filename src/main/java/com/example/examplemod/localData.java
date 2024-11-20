package com.example.examplemod;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

public class localData {
    public static void register(CommandDispatcher<CommandSourceStack> p_139366_) {
        LiteralArgumentBuilder<CommandSourceStack> literalargumentbuilder = Commands.literal("localdata").requires((p_139381_) -> true);
        literalargumentbuilder.then(Commands.argument("pos", BlockPosArgument.blockPos()).executes( (target) -> {
            BlockEntity entity = target.getSource().getUnsidedLevel().getBlockEntity(BlockPosArgument.getBlockPos(target, "pos"));
            if (entity==null) {
                ExampleMod.LOGGER.warn("Entity null");
                target.getSource().sendFailure(Component.literal("Entity null"));
                return 0;
            }
            CompoundTag data = entity.saveWithoutMetadata();
            target.getSource().sendSuccess(() -> {
                return NbtUtils.toPrettyComponent(data);
            }, false);
            return 1;
        }));
        p_139366_.register(literalargumentbuilder);
    }
}
