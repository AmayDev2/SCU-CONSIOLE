package com.amay.scu.enums;

import org.network.monitorandcontrol.SpecialMode;

import java.util.ArrayList;
import java.util.List;

public enum StationSpecialMode {
    EMERGENCY(1, "Emergency", "-fx-background-color: red;", SpecialMode.EMERGENCY),
    TIME_OVERRIDE(2, "Time Override", "-fx-background-color: yellow;", SpecialMode.TIME_OVERRIDE),
    ENTRY_EXIT_OVERRIDE(3, "Entry/Exit Override", "-fx-background-color: yellow;", SpecialMode.ENTRY_EXIT_OVERRIDE),
    EXCESS_FARE_OVERRIDE(4, "Excess Fare Override", "-fx-background-color: yellow;", SpecialMode.EXCESS_FARE_OVERRIDE),
    FARE_BYPASS_MODE_1(5, "Fare Bypass Mode 1", "-fx-background-color: yellow;", SpecialMode.FARE_BYPASS_MODE_1),
    FARE_BYPASS_MODE_2(6, "Fare Bypass Mode 2", "-fx-background-color: yellow;", SpecialMode.FARE_BYPASS_MODE_2),
    STATION_CLOSED(7, "Station Closed", "-fx-background-color: yellow;", SpecialMode.STATION_CLOSED_MODE);

    private final int modeId;
    private final String modeName;
    private final String color;
    private final SpecialMode specialMode;

    private static final List<StationSpecialModeListener> listeners = new ArrayList<>();

    StationSpecialMode(int modeId, String modeName, String color, SpecialMode specialMode) {
        this.modeId = modeId;
        this.modeName = modeName;
        this.color = color;
        this.specialMode = specialMode;
    }

    public int getModeId() {
        return modeId;
    }

    public String getModeName() {
        return modeName;
    }

    public String getColor() {
        return color;
    }

    public SpecialMode getSpecialMode() {
        return specialMode;
    }

    // Listener interface
    public interface StationSpecialModeListener {
        void onModeChanged(StationSpecialMode newMode);
    }

    // Register a listener
    public static void addStationSpecialModeListener(StationSpecialModeListener listener) {
        listeners.add(listener);
    }

    // Unregister a listener
    public static void removeStationSpecialModeListener(StationSpecialModeListener listener) {
        listeners.remove(listener);
    }

    // Notify all listeners of a mode change
    private static void notifyListeners(StationSpecialMode newMode) {
        for (StationSpecialModeListener listener : listeners) {
            listener.onModeChanged(newMode);
        }
    }

    // Method to set the station mode and notify listeners
    public static void setStationSpecialMode(StationSpecialMode newMode) {
        notifyListeners(newMode);
    }

    public static StationSpecialMode getStationSpecialMode(int modeId) {
        for (StationSpecialMode mode : StationSpecialMode.values()) {
            if (mode.getModeId() == modeId) {
                setStationSpecialMode(mode);  // Notify listeners
                return mode;
            }
        }
        return null;
    }

    public static StationSpecialMode getStationSpecialMode(String modeName) {
        for (StationSpecialMode mode : StationSpecialMode.values()) {
            if (mode.getModeName().equalsIgnoreCase(modeName)) {
                setStationSpecialMode(mode);  // Notify listeners
                return mode;
            }
        }
        return null;
    }
}
