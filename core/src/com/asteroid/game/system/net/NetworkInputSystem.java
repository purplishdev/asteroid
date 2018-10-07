package com.asteroid.game.system.net;

import com.artemis.Aspect;
import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.asteroid.game.component.MovementComponent;
import com.asteroid.game.component.NetworkComponent;
import com.asteroid.game.system.InputSystem;
import com.asteroid.shared.net.entity.EntityConnection;
import com.asteroid.shared.net.packet.PlayerMove;

import lombok.NonNull;
import lombok.var;

public class NetworkInputSystem extends BaseEntitySystem {

    private ComponentMapper<NetworkComponent> networkMapper;

    private InputSystem inputSystem;

    public NetworkInputSystem() {
        super(Aspect.all(NetworkComponent.class, MovementComponent.class));
    }

    @Override
    protected void processSystem() { }

    @Override
    protected void inserted(int entity) {
        var network = networkMapper.get(entity);
        if (network == null) {
            return;
        }

        network.connection.addPacketListener(PlayerMove.class, this::move);
    }

    private void move(@NonNull EntityConnection connection, PlayerMove packet) {
        int entity = connection.getEntityId();
        inputSystem.move(entity, packet.deltaVelocity);
        inputSystem.rotate(entity, packet.deltaRotationSpeed);
    }
}