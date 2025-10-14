package DTO;

import java.util.Date;

public class SwapTransaction {
    private int swapId;
    private int userId;
    private Integer stationId;
    private Date timeSwap;
    private String serviceType;

    public SwapTransaction() {}

    public SwapTransaction(int swapId, int userId, Integer stationId, Date timeSwap, String serviceType) {
        this.swapId = swapId;
        this.userId = userId;
        this.stationId = stationId;
        this.timeSwap = timeSwap;
        this.serviceType = serviceType;
    }

    public int getSwapId() { return swapId; }
    public void setSwapId(int swapId) { this.swapId = swapId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Integer getStationId() { return stationId; }
    public void setStationId(Integer stationId) { this.stationId = stationId; }

    public Date getTimeSwap() { return timeSwap; }
    public void setTimeSwap(Date timeSwap) { this.timeSwap = timeSwap; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
}
