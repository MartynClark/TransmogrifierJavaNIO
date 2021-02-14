package examples;

import java.io.*;
import java.util.*;

/**
 * User: martyn
 * Date: 07/02/2017
 * Time: 14:06
 */
public class PositionData implements Serializable {
    private static final long serialVersionUID = 1L;

    private long ImeiNumber;

    private char seqID;

    private int CRCValue;

    private List<Record> records;

    public PositionData(List<Record> records, int crcValue, long imeINumber, char seqID) {
        this.records = records;
        this.CRCValue = crcValue;
        this.ImeiNumber = imeINumber;
        this.seqID = seqID;
    }

    public long getImeiNumber() {
        return ImeiNumber;
    }

    public void setImeiNumber(long imeiNumber) {
        ImeiNumber = imeiNumber;
    }

    public char getSeqID() {
        return seqID;
    }

    public int getCRCValue() {
        return CRCValue;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PositionData)) return false;

        PositionData that = (PositionData) o;

        if (getImeiNumber() != that.getImeiNumber()) return false;
        if (getSeqID() != that.getSeqID()) return false;
        return getCRCValue() == that.getCRCValue();
    }

    @Override
    public int hashCode() {
        int result = (int) (getImeiNumber() ^ (getImeiNumber() >>> 32));
        result = 31 * result + (int) getSeqID();
        result = 31 * result + getCRCValue();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PositionDatas{");
        sb.append("ImeiNumber=").append(ImeiNumber);
        sb.append(", seqID=").append(seqID);
        sb.append(", CRCValue=").append(CRCValue);
        sb.append('}');
        return sb.toString();
    }
}
