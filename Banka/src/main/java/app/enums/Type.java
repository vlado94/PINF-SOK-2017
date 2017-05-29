package app.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlEnum
public enum Type {
    TRANSFER, //prenos
    PAYMENTOUT, //uplata
    PAYOUT, //isplata
    PAYMENTIN //naplata
}

