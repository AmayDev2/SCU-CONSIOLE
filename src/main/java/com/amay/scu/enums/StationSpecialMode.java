package com.amay.scu.enums;

import org.network.monitorandcontrol.SpecialMode;

import java.util.ArrayList;
import java.util.List;

public enum StationSpecialMode {
    STATION_NORMAL(1, "Station Normal", "-fx-background-color: yellow;",  SpecialMode.NORMAL), // 01
    EMERGENCY(2, "Emergency", "-fx-background-color: red;", SpecialMode.EMERGENCY), // 02
    TIME_OVERRIDE(3, "Time Override", "-fx-background-color: yellow;", SpecialMode.TIME_OVERRIDE), // 03
    ENTRY_EXIT_OVERRIDE(4, "Entry/Exit Override", "-fx-background-color: yellow;", SpecialMode.ENTRY_EXIT_OVERRIDE), // 04
    EXCESS_FARE_OVERRIDE(5, "Excess Fare Override", "-fx-background-color: yellow;", SpecialMode.EXCESS_FARE_OVERRIDE), // 05
    FARE_BYPASS_MODE_1(6, "Fare Bypass Mode 1", "-fx-background-color: yellow;", SpecialMode.FARE_BYPASS_MODE_1), // 06
    FARE_BYPASS_MODE_2(7, "Fare Bypass Mode 2", "-fx-background-color: yellow;", SpecialMode.FARE_BYPASS_MODE_2), // 07
    STATION_CLOSED(0, "Station Closed", "-fx-background-color: yellow;", SpecialMode.STATION_CLOSED_MODE), // 00
    HIGH_SECURITY_MODE(8, "High Security Mode", "-fx-background-color: yellow;", SpecialMode.TIME_OVERRIDE); // 08
    ;


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
        System.out.println("in addStationSpecialModeListener");
        listeners.add(listener);
    }

    // Unregister a listener
    public static void removeStationSpecialModeListener(StationSpecialModeListener listener) {
        listeners.remove(listener);
    }

    // Notify all listeners of a mode change
    private static void notifyListeners(StationSpecialMode newMode) {
        System.out.println("In notifyListeners");
        for (StationSpecialModeListener listener : listeners) {
            System.out.println("In listener Mode changed to: " + newMode.getModeName() + " with color " + newMode.getColor());
            listener.onModeChanged(newMode);
        }
    }

    // Method to set the station mode and notify listeners
    public static void setStationSpecialMode(StationSpecialMode newMode) {
        System.out.println("In setStationSpecialMode");
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

    public static StationSpecialMode getSpecialMode(String specialModeName) {
        for (StationSpecialMode mode : StationSpecialMode.values()) {
            if (mode.getSpecialMode().equals(SpecialMode.valueOf(specialModeName))) {
//                setStationSpecialMode(mode);  // Notify listeners
                return mode;
            }
        }
        return null;
    }


}
