package jankaddons.mixins.scarpet;

import jankaddons.ducks.INoiseChunkGenerator;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.NoiseColumnSampler;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.GenerationShapeConfig;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.random.ChunkRandom;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;

@Mixin(NoiseChunkGenerator.class)
public class NoiseChunkGeneratorMixin implements INoiseChunkGenerator {
    @Shadow
    @Final
    protected Supplier<ChunkGeneratorSettings> settings;
    @Shadow
    @Final
    private Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry;

    @Mutable
    @Shadow
    @Final
    private NoiseColumnSampler noiseColumnSampler;

    @Override
    public void setNoiseChunkGenerator(long seed) {
        ChunkGeneratorSettings generatorSettings = this.settings.get();
        boolean hasNoiseCaves = generatorSettings.hasNoiseCaves();
        GenerationShapeConfig shapeConfig = generatorSettings.getGenerationShapeConfig();
        ChunkRandom.RandomProvider randomProvider = generatorSettings.getRandomProvider();
        this.noiseColumnSampler = new NoiseColumnSampler(shapeConfig, hasNoiseCaves, seed, noiseRegistry, randomProvider);
    }
}
