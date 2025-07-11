package com.amay.scu.enums.filters;

public enum FilterEnums {


    Issuance("Issuance","issuance"),
    Penalty("Penalty","penalty"),
    Refund("Refund","refund"),
    Cancel("Cancel","cancel"),

    //Fare Medium
    CASH("CASH","Cash"),
    UPI("UPI","UPI"),
    POS("POS","POS"),

    //Fare Media
    QR("QR","QR"),
    NCMC("NCMC","NCMC"),
    MQR("MQR","MQR"),

    TOM("TOM","01"),
     EFO("EFO","02"),
    TVM("TVM","03"),

    SJT("SJT","01"),
    RJT("RJT","02"),
    GROUP("GRT","03"),
    PAID("PAID","05"),
    FREE("FREE","04"),
    TEST("AG","06"),
    ;

    private final String title;
    private final String value;

    FilterEnums(String Title, String value) {
        this.title = Title;
        this.value = value;
    }

    public String getTitle() {
        return title;
    }
    public String getValue() {
        return value;
    }
}
