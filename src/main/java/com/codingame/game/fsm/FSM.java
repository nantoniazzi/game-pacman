package com.codingame.game.fsm;

import java.util.HashMap;
import java.util.Map;

public class FSM {
    private String previousStateId;
    private String currentStateId;
    private String nextStateId;
    private Map<String, FSMState> stateMap = new HashMap<>();

    public void init(String initialStateId) {
        FSMState state = stateMap.get(initialStateId);
        if (state == null) throw new IllegalArgumentException("Unknown initial state id " + initialStateId);

        this.currentStateId = initialStateId;
        state.init();
    }

    public void addState(FSMState state) {
        this.stateMap.put(state.getId(), state);
    }
    
    public String getPreviousStateId() {
        return previousStateId;
    }
    
    public String getCurrentStateId() {
        return currentStateId;
    }
    
    public String getNextStateId() {
        return nextStateId;
    }

    public void setState(String id) {
        FSMState nextState = stateMap.get(id);
        if (nextState == null) throw new IllegalArgumentException("Unknown state " + id);
        nextStateId = id;
    }

    public void sendEvent(String eventId, Object data) {
        FSMState state = stateMap.get(currentStateId);
        state.event(eventId, data);
    }

    public void update() {
        FSMState currentState = stateMap.get(currentStateId);
        
        if(nextStateId != null && !currentStateId.equals(nextStateId)) {
            currentState.exit();

            previousStateId = currentStateId;
            currentStateId = nextStateId;
            nextStateId = null;

            currentState = stateMap.get(currentStateId);
            currentState.init();
        }

        currentState.update();
    }
}
