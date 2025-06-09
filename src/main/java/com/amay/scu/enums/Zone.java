package com.amay.scu.enums;

public enum Zone {
    ZONE_ONE(1,"AG_ZONE_ONE",630,190),
    ZONE_TWO(2,"AG_ZONE_ONE",1050,190),
    ZONE_THREE(3,"AG_ZONE_ONE",630,470),
    ZONE_FOUR(4,"AG_ZONE_ONE",1050,470);

    private final String zoneName;
    private final int zoneNumber;
    private final int X;
    private final int Y;

    Zone(int zoneNumber,String zoneTwo, int i, int i1) {
        this.zoneNumber=zoneNumber;
        this.zoneName = zoneTwo;
        this.X = i;
        this.Y = i1;
    }

    public String getZoneName() {
        return zoneName;
    }
    public int getZoneNumber() {
        return zoneNumber;
    }
    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
    }
    public Zone getByNZoneNumber(int number){
        for(Zone zone:Zone.values()){
            if (zone.getZoneNumber()==number){
                return zone;
            }
        }
        return null;

    }
}
