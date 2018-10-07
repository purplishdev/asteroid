package com.asteroid.game.factory;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.asteroid.shared.AsteroidException;
import com.asteroid.game.Constants;
import com.asteroid.game.component.BulletComponent;
import com.asteroid.game.component.CollisionComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.TextureComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import lombok.NonNull;
import lombok.var;

import static com.badlogic.gdx.math.MathUtils.degreesToRadians;

public class BulletFactory {

    private final Vector2 vector = new Vector2();

    private final World world;

    private final TextureRegion texture;

    private final Polygon polygon;

    private final Archetype bulletArchetype;

    public BulletFactory(@NonNull World world, @NonNull TextureRegion texture, @NonNull Polygon polygon) {
        this.world = world;
        this.polygon = polygon;
        this.texture = texture;
        this.bulletArchetype = buildArchetype(world);
    }

    private Archetype buildArchetype(@NonNull World world) {
        return new ArchetypeBuilder()
                .add(BulletComponent.class)
                .add(CollisionComponent.class)
                .add(MovementComponent.class)
                .add(TextureComponent.class)
                .build(world);
    }

    public void create(int shooter) {
        create(shooter, 0.0f, 0.0f, 0.0f);
    }

    public void createGroup(int shooter, float... args) {
        if (args.length % 3 != 0) {
            throw new AsteroidException("Bullet createEffect group arguments should be dividable by 3");
        }

        for (int i = 0; i < args.length; i += 3) {
            create(shooter, args[i], args[i + 1], args[i + 2]);
        }
    }

    public void create(int shooter, float xOffset, float yOffset, float radRotationOffset) {

        var movementMapper = world.getMapper(MovementComponent.class);
        var shooterMovementComponent = movementMapper.get(shooter);

        var shooterPosition = shooterMovementComponent.position;
        var entityRotation = shooterMovementComponent.rotation + radRotationOffset * degreesToRadians;

        var bulletPosition = calculateBulletPosition(shooterPosition, entityRotation,
                                                     100.0f + xOffset, 1.5f + yOffset);

        int entity = world.create(bulletArchetype);

        createMovementComponent(entity, bulletPosition, entityRotation);
        createBulletComponent(entity);
        createTextureComponent(entity);
        createCollisionComponent(entity);
    }

    private void createCollisionComponent(int entity) {

        var width = texture.getRegionWidth();
        var height = texture.getRegionHeight();

        var polygon = new Polygon(this.polygon.getVertices());
        polygon.setOrigin(width * 0.5f, height * 0.5f);

        var mapper = world.getMapper(CollisionComponent.class);
        var component = mapper.create(entity);
        component.polygon = polygon;
    }

    private void createMovementComponent(int entity, @NonNull Vector2 bulletPosition, float bulletRotation) {
        var mapper = world.getMapper(MovementComponent.class);
        var component = mapper.create(entity);
        component.position.set(bulletPosition);
        component.rotation = bulletRotation;
        component.velocity = Constants.Bullet.VELOCITY;
        component.maxVelocity = Constants.Bullet.VELOCITY;
    }

    private void createBulletComponent(int entity) {
        var mapper = world.getMapper(BulletComponent.class);
        var component = mapper.create(entity);
        component.maxLifespan = Constants.Bullet.LIFESPAN;
        component.damage = MathUtils.random(5, 15);
    }

    private void createTextureComponent(int entity) {
        var mapper = world.getMapper(TextureComponent.class);
        var component = mapper.create(entity);
        component.region = texture;
    }

    private Vector2 calculateBulletPosition(@NonNull Vector2 shooterPosition, float radShooterRotation, float xOffset, float yOffset) {
        return vector.set(xOffset, yOffset)
                .rotateRad(radShooterRotation)
                .add(shooterPosition);
    }
}