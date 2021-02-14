package examples;

import java.math.*;
import java.util.*;

/**
 * User: martyn
 * Date: 07/02/2017
 * Time: 22:53
 */

public class Record {
    private Short allInputStatus;

    private Short allOutputStatus;

    private Integer analogInputValue;

    private String driverId;

    private Date gpsDate;

    private BigDecimal gpsHdop;

    private int heading;

    private Double latitude;

    private Double longitude;

    private Integer odometer;

    private Short reportId;

    private Date rtcDate;

    private Date sentDate;

    private Short speed;

    private String txtMessage;

    private String vinNumber;

    private int gsmStatus;

    private short timeReport;

    public Record(){}
    public Record(Date gpsDate, Date rtcDate, Date sentDate, double longitude, double latitude, int heading,
                  short reportId, int odometer, BigDecimal gpsHop, short allInputStatus, short speed,
                  short allOutputStatus, int analogInputValue, String driverId, String txtMessage) {
        this.gpsDate = gpsDate;
        this.rtcDate = rtcDate;
        this.sentDate = sentDate;
        this.longitude = longitude;
        this.latitude = latitude;
        this.heading = heading;
        this.reportId = reportId;
        this.odometer = odometer;
        this.gpsHdop = gpsHop;
        this.allInputStatus = allInputStatus;
        this.speed = speed;
        this.allOutputStatus = allOutputStatus;
        this.analogInputValue = analogInputValue;
        this.driverId = driverId;
        this.txtMessage = txtMessage;
    }

    public String getDriverId() {
        return driverId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getOdometer() {
        return odometer;
    }

    public Short getReportId() {
        return reportId;
    }

    public Date getRtcDate() {
        return rtcDate;
    }

    public Short getSpeed() {
        return speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Record)) return false;

        Record record = (Record) o;

        if (heading != record.heading) return false;
        if (allInputStatus != null ? !allInputStatus.equals(record.allInputStatus) : record.allInputStatus != null)
            return false;
        if (allOutputStatus != null ? !allOutputStatus.equals(record.allOutputStatus) : record.allOutputStatus != null)
            return false;
        if (analogInputValue != null ? !analogInputValue
                .equals(record.analogInputValue) : record.analogInputValue != null) return false;
        if (!gpsDate.equals(record.gpsDate)) return false;
        if (!getLatitude().equals(record.getLatitude())) return false;
        if (!getLongitude().equals(record.getLongitude())) return false;
        if (!getOdometer().equals(record.getOdometer())) return false;
        if (!getReportId().equals(record.getReportId())) return false;
        if (!getRtcDate().equals(record.getRtcDate())) return false;
        if (!sentDate.equals(record.sentDate)) return false;
        return getSpeed().equals(record.getSpeed());
    }

    @Override
    public int hashCode() {
        int result = allInputStatus != null ? allInputStatus.hashCode() : 0;
        result = 31 * result + (allOutputStatus != null ? allOutputStatus.hashCode() : 0);
        result = 31 * result + (analogInputValue != null ? analogInputValue.hashCode() : 0);
        result = 31 * result + gpsDate.hashCode();
        result = 31 * result + heading;
        result = 31 * result + getLatitude().hashCode();
        result = 31 * result + getLongitude().hashCode();
        result = 31 * result + getOdometer().hashCode();
        result = 31 * result + getReportId().hashCode();
        result = 31 * result + getRtcDate().hashCode();
        result = 31 * result + sentDate.hashCode();
        result = 31 * result + getSpeed().hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Data{");
        sb.append("allInputStatus=").append(allInputStatus);
        sb.append(", allOutputStatus=").append(allOutputStatus);
        sb.append(", analogInputValue=").append(analogInputValue);
        sb.append(", driverId='").append(driverId).append('\'');
        sb.append(", gpsDate=").append(gpsDate);
        sb.append(", gpsHdop=").append(gpsHdop);
        sb.append(", heading=").append(heading);
        sb.append(", latitude=").append(latitude);
        sb.append(", longitude=").append(longitude);
        sb.append(", odometer=").append(odometer);
        sb.append(", reportId=").append(reportId);
        sb.append(", rtcDate=").append(rtcDate);
        sb.append(", sentDate=").append(sentDate);
        sb.append(", speed=").append(speed);
        sb.append(", txtMessage='").append(txtMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
