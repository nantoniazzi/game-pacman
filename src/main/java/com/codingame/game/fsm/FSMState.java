package com.codingame.game.fsm;

public class FSMState {
    private String id;

    public FSMState(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void init() {
    }

    public void update() {
    }

    public void exit() {
    }
    
    public void event(String event, Object data) {
    }
}
