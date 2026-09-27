package dev.homka.risendeath.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

// Он фулл написан ClodeAI
// Позже нужно разобраться и переписать


public class HeartRingParticle extends TextureSheetParticle {

    protected HeartRingParticle(ClientLevel level, double x, double y, double z,
                                double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
        super(level, x, y, z);

        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;

        this.gravity = 0f;       // не падает вниз
        this.hasPhysics = false; // не врезается в блоки — важно, иначе кольцо застрянет в стене
        this.friction = 0.96f;   // old=0.92f new=0.96f    плавное гашение скорости к концу жизни
        this.lifetime = 30;      // old=12 new=30    тиков жизни (0.6 сек при 20 тик/с)
        this.quadSize = 0.12f;

        this.setSpriteFromAge(sprites);
    }

    @Override
    public void tick() {
        super.tick(); // применит движение (xd/yd/zd), friction, gravity, увеличит age

        float progress = (float) this.age / this.lifetime;
        this.quadSize = 0.12f + progress * 0.25f; // "разрастание" кольца
        this.alpha = 1.0f - progress;            // плавное угасание
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new HeartRingParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}