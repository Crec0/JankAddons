package jankaddons.script;

import carpet.script.CarpetContext;
import carpet.script.Context;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.BooleanValue;
import carpet.script.value.NumericValue;
import carpet.script.value.Value;
import jankaddons.ducks.INoiseChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class Scarpet {
    @ScarpetFunction(maxParams = 2)
    public Value set_world_noise_seed(Context c, NumericValue seed) {
        long seedL = seed.getLong();
        ChunkGenerator generator = ((CarpetContext) c).s.getWorld().getChunkManager().getChunkGenerator();
        ((INoiseChunkGenerator) generator).setNoiseChunkGenerator(seedL);
        return BooleanValue.TRUE;
    }
}
