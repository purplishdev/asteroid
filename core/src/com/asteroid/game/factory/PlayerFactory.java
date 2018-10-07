package com.asteroid.factory;

import com.artemis.Archetype;
import com.artemis.ArchetypeBuilder;
import com.artemis.World;
import com.asteroid.Constants;
import com.asteroid.component.CollisionComponent;
import com.asteroid.component.HealthComponent;
import com.asteroid.component.LabelComponent;
import com.asteroid.component.MovementComponent;
import com.asteroid.component.PlayerComponent;
import com.asteroid.component.TextureComponent;
import com.asteroid.component.TrailComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

import lombok.NonNull;
import lombok.var;

import static com.badlogic.gdx.math.MathUtils.degreesToRadians;

public class PlayerFactory {

    private World world;

    private final TextureRegion texture;

    private final Polygon polygon;

    private Archetype playerArchetype;

    public PlayerFactory(@NonNull TextureRegion texture,
                         @NonNull Polygon polygon) {
        this.texture = texture;
        this.polygon = polygon;
    }

    public void setWorld(@NonNull World world) {
        this.world = world;
        this.playerArchetype = buildArchetype(world);
    }

    private Archetype buildArchetype(@NonNull World world) {
        return new ArchetypeBuilder()
                .add(PlayerComponent.class)
                .add(HealthComponent.class)
                .add(LabelComponent.class)
                .add(CollisionComponent.class)
                .add(MovementComponent.class)
                .add(TextureComponent.class)
                .add(TrailComponent.class)
                .build(world);
    }

    public int create(String name, float x, float y, float degRotation) {
        int player = world.create(playerArchetype);

        createMovementComponent(player, x, y, degRotation);
        createCollisionComponent(player);
        createLabelComponent(player, name);
        createTextureComponent(player);
        createHealthComponent(player);
        createPlayerComponent(player);
        createTrailComponent(player);

        return player;
    }

    private void createTrailComponent(int player) {
        var mapper = world.getMapper(TrailComponent.class);
        var component = mapper.create(player);
        component.type = Particle.PLAYER_TRAIL;
        component.x = -35.0f;
        component.y = 0.0f;
    }

    private void createPlayerComponent(int player) {
        var mapper = world.getMapper(PlayerComponent.class);
        var component = mapper.create(player);
        component.deathCount = 0;
    }

    private void createHealthComponent(int player) {
        var mapper = world.getMapper(HealthComponent.class);
        var component = mapper.create(player);
        component.value = 100;
        component.maxValue = 100;
    }

    private void createLabelComponent(int player, String name) {
        var mapper = world.getMapper(LabelComponent.class);
        var component = mapper.create(player);
        component.text = name;
        component.xOffset = 0.0f;
        component.yOffset = 90.0f;
    }

    private void createCollisionComponent(int player) {
        var width = texture.getRegionWidth();
        var height = texture.getRegionHeight();

        var polygon = new Polygon(this.polygon.getVertices());
        polygon.setOrigin(width * 0.5f, height * 0.5f);

        var mapper = world.getMapper(CollisionComponent.class);
        var component = mapper.create(player);
        component.polygon = polygon;
    }

    private void createTextureComponent(int player) {
        var mapper = world.getMapper(TextureComponent.class);
        var component = mapper.create(player);
        component.region = texture;
    }

    private void createMovementComponent(int player, float x, float y, float degRotation) {
        var mapper = world.getMapper(MovementComponent.class);
        var component = mapper.create(player);
        component.position.set(x, y);
        component.velocity = 0.0f; // random(0.0f, Constants.Player.MAX_VELOCITY);
        component.minVelocity = 0.0f;
        component.maxVelocity = Constants.Player.MAX_VELOCITY;
        component.minDeltaVelocity = Constants.Player.DECELERATION_SPEED;
        component.maxDeltaVelocity = Constants.Player.ACCELERATION_SPEED;
        component.rotation = degRotation * degreesToRadians;
        component.minRotationSpeed = -Constants.Player.MAX_ROTATION_SPEED;
        component.maxRotationSpeed = Constants.Player.MAX_ROTATION_SPEED;
        component.minDeltaRotationSpeed = -Constants.Player.ROTATION_SPEED;
        component.maxDeltaRotationSpeed = Constants.Player.ROTATION_SPEED;
    }
}