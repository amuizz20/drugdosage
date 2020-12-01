package com.amtechnology.drugdosage;

class ReminderDetails {
    public String id,medicine,dosage,time,days;

    public ReminderDetails(String id, String medicine, String dosage, String time, String days) {
        this.id = id;
        this.medicine = medicine;
        this.dosage = dosage;
        this.time = time;
        this.days = days;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
