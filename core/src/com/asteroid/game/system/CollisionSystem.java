package com.asteroid.game.system;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.asteroid.game.component.CollidedComponent;
import com.asteroid.game.component.CollisionComponent;
import com.asteroid.game.component.DeathComponent;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.RemoveComponent;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

import lombok.var;

import static com.badlogic.gdx.math.MathUtils.radiansToDegrees;

public class CollisionSystem extends BaseEntitySystem {

    private ComponentMapper<CollisionComponent> collisionMapper;

    private ComponentMapper<MovementComponent> movementMapper;

    private ComponentMapper<CollidedComponent> collidedMapper;

    public CollisionSystem() {
        super(Aspect.all(CollisionComponent.class, MovementComponent.class)
                      .exclude(RemoveComponent.class, DeathComponent.class));
    }

    @Override
    protected void processSystem() {
        var entities = getEntityIds();

        for (int i = 0; i < entities.size(); ++i) {
            var firstEntity = entities.get(i);

            for (int j = i + 1; j < entities.size(); ++j) {
                var secondEntity = entities.get(j);
                checkForCollision(firstEntity, secondEntity);
            }
        }
    }

    private void checkForCollision(int firstEntity, int secondEntity) {
        var firstCollision = collisionMapper.get(firstEntity);
        var secondCollision = collisionMapper.get(secondEntity);

        var firstPolygon = updatePolygon(firstEntity, firstCollision.polygon);
        var secondPolygon = updatePolygon(secondEntity, secondCollision.polygon);

        var collision = Intersector.overlapConvexPolygons(firstPolygon, secondPolygon);

        if (collision) {
            var firstCollided = collidedMapper.create(firstEntity);
            firstCollided.entity = secondEntity;

            var secondCollided = collidedMapper.create(secondEntity);
            secondCollided.entity = firstEntity;
        }
    }

    private Polygon updatePolygon(int entity, Polygon polygon) {
        var movement = movementMapper.get(entity);

        var xOrigin = polygon.getOriginX();
        var yOrigin = polygon.getOriginY();

        polygon.setPosition(movement.position.x - xOrigin, movement.position.y - yOrigin);
        polygon.setRotation(movement.rotation * radiansToDegrees);

        return polygon;
    }
}