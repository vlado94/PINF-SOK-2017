package app.enums;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Type {
    TRANSFER, //prenos
    PAYMENTOUT, //uplata
    PAYOUT, //isplata
    PAYMENTIN //naplata
}

