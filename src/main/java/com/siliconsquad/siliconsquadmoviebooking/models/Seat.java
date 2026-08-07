package com.siliconsquad.siliconsquadmoviebooking.models;

import java.util.Objects;

/**
 * Represents a seat inside a movie theater auditorium.
 * Stores the seat's location, type, and reservation status.
 * @author G. Mkrtchyan
 * @since July 6, 2026
 */
public class Seat {

    /**
     * Represents the different types of seats available.
     */
    public enum SeatType {
        STANDARD,
        PREMIUM,
        ACCESSIBLE
    }

    /** The unique identifier for the seat. */
    private int seatId;

    /** The row label where the seat is located. */
    private String rowLabel;

    /** The seat number within the row. */
    private int seatNumber;

    /** The type of the seat. */
    private SeatType seatType;

    /** Indicates whether the seat has been reserved. */
    private boolean reserved;

    /**
     * Creates a new seat with the specified information.
     *
     * @param seatId the unique identifier of the seat
     * @param rowLabel the row label where the seat is located
     * @param seatNumber the seat number within the row
     * @param seatType the type of seat (STANDARD, PREMIUM, or ACCESSIBLE)
     * @throws IllegalArgumentException if the row label is empty or the seat number is less than or equal to zero
     */
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

    /**
     * Returns the unique seat identifier.
     *
     * @return the seat ID
     */
    public int getSeatId() {
        return seatId;
    }

    /**
     * Sets the seat identifier.
     *
     * @param seatId the new seat ID
     */
    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    /**
     * Returns the row label of the seat.
     *
     * @return the row label
     */
    public String getRowLabel() {
        return rowLabel;
    }

    /**
     * Sets the row label for the seat.
     *
     * @param rowLabel the new row label
     * @throws IllegalArgumentException if the row label is empty
     */
    public void setRowLabel(String rowLabel) {
        if (rowLabel == null || rowLabel.isBlank()) {
            throw new IllegalArgumentException(
                    "Row label cannot be empty."
            );
        }

        this.rowLabel = rowLabel.toUpperCase();
    }

    /**
     * Returns the seat number.
     *
     * @return the seat number
     */
    public int getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the seat number.
     *
     * @param seatNumber the new seat number
     * @throws IllegalArgumentException if the seat number is less than or equal to zero
     */
    public void setSeatNumber(int seatNumber) {
        if (seatNumber <= 0) {
            throw new IllegalArgumentException(
                    "Seat number must be greater than zero."
            );
        }

        this.seatNumber = seatNumber;
    }

    /**
     * Returns the type of the seat.
     *
     * @return the seat type
     */
    public SeatType getSeatType() {
        return seatType;
    }

    /**
     * Sets the seat type.
     *
     * @param seatType the new seat type
     */
    public void setSeatType(SeatType seatType) {
        this.seatType =
                seatType == null ? SeatType.STANDARD : seatType;
    }

    /**
     * Returns whether the seat is reserved.
     *
     * @return true if reserved; otherwise false
     */
    public boolean isReserved() {
        return reserved;
    }

    /**
     * Returns whether the seat is available.
     *
     * @return true if available; otherwise false
     */
    public boolean isAvailable() {
        return !reserved;
    }

    /**
     * Attempts to reserve the seat.
     *
     * @return true if the reservation was successful; otherwise false
     */
    public boolean reserve() {
        if (reserved) {
            return false;
        }

        reserved = true;
        return true;
    }

    /**
     * Releases the reservation for the seat.
     */
    public void release() {
        reserved = false;
    }

    /**
     * Returns the seat label (row + seat number).
     *
     * @return the seat label
     */
    public String getSeatLabel() {
        return rowLabel + seatNumber;
    }

    /**
     * Returns a string representation of the seat.
     *
     * @return a formatted description of the seat
     */
    @Override
    public String toString() {
        return getSeatLabel()
                + " - "
                + seatType
                + (reserved ? " - Reserved" : " - Available");
    }

    /**
     * Compares this seat with another object.
     *
     * @param object the object to compare
     * @return true if both seats have the same seat ID
     */
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

    /**
     * Returns the hash code of the seat.
     *
     * @return the seat hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(seatId);
    }
}
