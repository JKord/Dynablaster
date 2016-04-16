package jkord.dynablaster.web;

public class MsgRoute {
    public static final String
        PLAYER_MOVE = "/game/hero/%d/move",
        PLAYER_BOMB = "/game/hero/bomb",
        PLAYER_DIE  = "/game/hero/%d/die",
        BOT_MOVE    = "/game/bot/%d/move",
        GAME_START  = "/game/start/%d"
    ;

    public static final String
        LOBBY_GET = "/api/game/lobby/%d/get",
        LOBBY_UPDATE = "/game/lobby/%d/update"
    ;
}
