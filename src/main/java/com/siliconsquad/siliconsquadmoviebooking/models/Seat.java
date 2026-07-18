package com.siliconsquad.siliconsquadmoviebooking.models;

import java.util.Objects;

public class Seat {

    public enum SeatType {
        STANDARD,
        PREMIUM,
        ACCESSIBLE
    }

    private int seatId;
    private String rowLabel;
    private int seatNumber;
    private SeatType seatType;
    private boolean reserved;

    public Seat(
            int seatId,
            String rowLabel,
            int seatNumber,
            SeatType seatType
    ) {
        if (rowLabel == null || rowLabel.isBlank()) {
            throw new IllegalArgumentException(
                    "Row label cannot be empty."
            );
        }

        if (seatNumber <= 0) {
            throw new IllegalArgumentException(
                    "Seat number must be greater than zero."
            );
        }

        this.seatId = seatId;
        this.rowLabel = rowLabel.toUpperCase();
        this.seatNumber = seatNumber;
        this.seatType =
                seatType == null ? SeatType.STANDARD : seatType;
        this.reserved = false;
    }

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        if (rowLabel == null || rowLabel.isBlank()) {
            throw new IllegalArgumentException(
                    "Row label cannot be empty."
            );
        }

        this.rowLabel = rowLabel.toUpperCase();
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        if (seatNumber <= 0) {
            throw new IllegalArgumentException(
                    "Seat number must be greater than zero."
            );
        }

        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType =
                seatType == null ? SeatType.STANDARD : seatType;
    }

    public boolean isReserved() {
        return reserved;
    }

    public boolean isAvailable() {
        return !reserved;
    }

    public boolean reserve() {
        if (reserved) {
            return false;
        }

        reserved = true;
        return true;
    }

    public void release() {
        reserved = false;
    }

    public String getSeatLabel() {
        return rowLabel + seatNumber;
    }

    @Override
    public String toString() {
        return getSeatLabel()
                + " - "
                + seatType
                + (reserved ? " - Reserved" : " - Available");
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Seat seat)) {
            return false;
        }

        return seatId == seat.seatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(seatId);
    }
}
