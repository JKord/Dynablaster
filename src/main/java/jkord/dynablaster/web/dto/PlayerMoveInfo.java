package jkord.dynablaster.web.dto;

import jkord.dynablaster.domain.obj.PlayerObject;

public class PlayerMoveInfo extends MoveInfo {

    public PlayerMoveInfo(PlayerObject player, String direction) {
        this.position = player.getPosition();
        this.direction = direction;
    }
}
