package jkord.dynablaster.web;

public class MsgRoute {
    public static final String
        PLAYER_MOVE = "/game/hero/%d/move",
        PLAYER_BOMB = "/game/hero/%d/bomb",
        PLAYER_DIE  = "/game/hero/%d/die",
        BOT_MOVE    = "/game/bot/%d/move"
    ;
}