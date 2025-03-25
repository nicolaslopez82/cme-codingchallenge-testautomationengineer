package pagesObjects;

import java.util.Objects;

public class PetroleumIndexObject {

    private String closingIndexDate;
    private String closingIndexPrice;
    private String intradateIndexDate;
    private String intradateIndexPrice;

    public PetroleumIndexObject() {
    }

    public PetroleumIndexObject(String closingIndexDate, String closingIndexPrice, String closingIndexLastUpdateTime, String intradateIndexDate, String intradateIndexPrice, String intradateIndexLastUpdateTime) {
        this.closingIndexDate = closingIndexDate;
        this.closingIndexPrice = closingIndexPrice;
        this.intradateIndexDate = intradateIndexDate;
        this.intradateIndexPrice = intradateIndexPrice;
    }

    public String getClosingIndexDate() {
        return closingIndexDate;
    }

    public void setClosingIndexDate(String closingIndexDate) {
        this.closingIndexDate = closingIndexDate;
    }

    public String getClosingIndexPrice() {
        return closingIndexPrice;
    }

    public void setClosingIndexPrice(String closingIndexPrice) {
        this.closingIndexPrice = closingIndexPrice;
    }

    public String getIntradateIndexDate() {
        return intradateIndexDate;
    }

    public void setIntradateIndexDate(String intradateIndexDate) {
        this.intradateIndexDate = intradateIndexDate;
    }

    public String getIntradateIndexPrice() {
        return intradateIndexPrice;
    }

    public void setIntradateIndexPrice(String intradateIndexPrice) {
        this.intradateIndexPrice = intradateIndexPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PetroleumIndexObject that = (PetroleumIndexObject) o;
        return Objects.equals(closingIndexDate, that.closingIndexDate) && Objects.equals(closingIndexPrice, that.closingIndexPrice) && Objects.equals(intradateIndexDate, that.intradateIndexDate) && Objects.equals(intradateIndexPrice, that.intradateIndexPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(closingIndexDate, closingIndexPrice, intradateIndexDate, intradateIndexPrice);
    }

    @Override
    public String toString() {
        return "PetroleumIndexObject{" +
                "closingIndexDate='" + closingIndexDate + '\'' +
                ", closingIndexPrice='" + closingIndexPrice + '\'' +
                ", intradateIndexDate='" + intradateIndexDate + '\'' +
                ", intradateIndexPrice='" + intradateIndexPrice + '\'' +
                '}';
    }
}
