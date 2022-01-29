package jankaddons.mixins.scarpet;

import jankaddons.ducks.INoiseChunkGenerator;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.NoiseSampler;
import net.minecraft.world.level.levelgen.NoiseSettings;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseChunkGeneratorMixin implements INoiseChunkGenerator {
    @Shadow @Final protected Supplier<NoiseGeneratorSettings> settings;

    @Mutable
    @Shadow
    @Final
    private NoiseSampler sampler;

    @Shadow
    @Final
    private Registry<NormalNoise.NoiseParameters> noises;

    @Override
    public void setNoiseChunkGenerator(long seed) {
        NoiseGeneratorSettings generatorSettings = this.settings.get();
        boolean hasNoiseCaves = generatorSettings.isNoiseCavesEnabled();
        NoiseSettings shapeConfig = generatorSettings.noiseSettings();
        WorldgenRandom.Algorithm randomProvider = generatorSettings.getRandomSource();
        this.sampler = new NoiseSampler(shapeConfig, hasNoiseCaves, seed, noises, randomProvider);
    }
}
