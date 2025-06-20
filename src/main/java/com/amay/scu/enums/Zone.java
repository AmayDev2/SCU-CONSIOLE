package com.amay.scu.enums;

public enum Zone {


    TOM_ZONE_ONE(1, "TOM_ZONE_ONE", 100, 100,0),
    TOM_ZONE_TWO(2, "TOM_ZONE_TWO", 1400, 100,0),
    TOM_ZONE_THREE(3, "TOM_ZONE_THREE", 100, 750,0),
    TOM_ZONE_FOUR(4, "TOM_ZONE_FOUR", 1400, 750,0),

    // AG Zones
    AG_ZONE_ONE(1, "AG_ZONE_ONE", 562, 280,0),
    AG_ZONE_TWO(2, "AG_ZONE_TWO", 937, 280,0),
    AG_ZONE_THREE(3, "AG_ZONE_THREE", 562, 470,0),
    AG_ZONE_FOUR(4, "AG_ZONE_FOUR", 937, 470,0),

    // EFO Zones
    EFO_ZONE_ONE(1, "EFO_ZONE_ONE", 602, 370,0),
    EFO_ZONE_TWO(2, "EFO_ZONE_TWO", 977, 370,0),

    // PAID Zones
    PAID_ZONE_ONE(1, "PAID_ZONE_ONE", 750, 187,0),
    PAID_ZONE_TWO(2, "PAID_ZONE_TWO", 750, 187,0),

    // UNPAID Zones
    UNPAID_ZONE_ONE(1, "UNPAID_ZONE_ONE", 100, 370,0),
    UNPAID_ZONE_TWO(2, "UNPAID_ZONE_TWO", 1400, 370,0);

    private final String zoneName;
    private final int zoneNumber;
    private final int X;
    private final int Y;
    private int count;

    Zone(int zoneNumber,String zoneTwo, int i, int i1,int count) {
        this.zoneNumber=zoneNumber;
        this.zoneName = zoneTwo;
        this.X = i;
        this.Y = i1;
        this.count=count;
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
    public int getCount(){return count;}
    public void setCount(){this.count+=1;}

    public Zone getByNZoneNumber(int number){
        for(Zone zone:Zone.values()){
            if (zone.getZoneNumber()==number){
                return zone;
            }
        }
        return null;

    }

    public Zone getByZone(String zoneName){
        for(Zone zone:Zone.values()){
            if (zone.getZoneName().equals(zoneName)){
                return zone;
            }
        }
        return null;

    }
}
