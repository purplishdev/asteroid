package com.asteroid.system.net;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.asteroid.component.ConnectionComponent;
import com.asteroid.component.MovementComponent;
import com.asteroid.net.packet.PlayerMove;
import com.asteroid.system.InputSystem;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import lombok.var;

public class NetworkInputSystem extends BaseEntitySystem {

    private ComponentMapper<ConnectionComponent> connectionMapper;

    private InputSystem inputSystem;

    public NetworkInputSystem() {
        super(Aspect.all(ConnectionComponent.class, MovementComponent.class));
    }

    @Override
    protected void processSystem() { }

    @Override
    protected void inserted(int entity) {
        var connection = connectionMapper.get(entity);
        if (connection == null) {
            return;
        }

        connection.connection.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object packet) {
                if (packet instanceof PlayerMove) {
                    move(entity, (PlayerMove)packet);
                }
            }
        });
    }

    private void move(int entity, PlayerMove packet) {
        inputSystem.move(entity, packet.deltaVelocity);
        inputSystem.rotate(entity, packet.deltaRotationSpeed);
    }
}