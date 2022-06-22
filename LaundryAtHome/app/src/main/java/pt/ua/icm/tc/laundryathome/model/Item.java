package pt.ua.icm.tc.laundryathome.model;

public class Item {
    String address;
    String itemType;
    String isDark;
    String number;

    public Item(String address, String itemType, String isDark, String number) {
        this.address = address;
        this.itemType = itemType;
        this.isDark = isDark;
        this.number = number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getIsDark() {
        return isDark;
    }

    public void setIsDark(String isDark) {
        this.isDark = isDark;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String toJson() {
        return "{\"address\":\"" + address + "\",\"itemType\":\"" + itemType + "\",\"isDark\":\"" + isDark + "\",\"number\":\"" + number + "\"}";
    }

}
