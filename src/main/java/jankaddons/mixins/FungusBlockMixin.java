package jankaddons.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.FungusBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(FungusBlock.class)
public abstract class FungusBlockMixin extends PlantBlock implements Fertilizable {

    @Shadow public abstract void grow(ServerWorld world, Random random, BlockPos pos, BlockState state);

    protected FungusBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        System.out.println("hi?");
        if (random.nextInt(7) == 0) {
            this.grow(world, random, pos, state);
        }
    }
}
