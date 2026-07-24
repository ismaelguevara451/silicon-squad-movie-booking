    package com.siliconsquad.siliconsquadmoviebooking.models;

    import java.time.LocalDateTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Objects;

    public class Showtime {

        private int showtimeId;
        private String movie;
        private Auditorium auditorium;
        private LocalDateTime startTime;
        private double ticketPrice;

        public Showtime(
                int showtimeId,
                String movie,
                Auditorium auditorium,
                LocalDateTime startTime,
                double ticketPrice
        ) {
            this.showtimeId = showtimeId;
            this.movie = movie;
            this.auditorium = auditorium;
            this.startTime = startTime;
            setTicketPrice(ticketPrice);
        }

        public int getShowtimeId() {
            return showtimeId;
        }

        public String getShowtimeMovie() {
            return movie;
        }

        public void setShowtimeId(int showtimeId) {
            this.showtimeId = showtimeId;
        }

        public Auditorium getAuditorium() {
            return auditorium;
        }

        public void setAuditorium(Auditorium auditorium) {
            this.auditorium = auditorium;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public double getTicketPrice() {
            return ticketPrice;
        }

        public void setTicketPrice(double ticketPrice) {
            if (ticketPrice < 0) {
                throw new IllegalArgumentException(
                        "Ticket price cannot be negative."
                );
            }

            this.ticketPrice = ticketPrice;
        }

        public String getFormattedStartTime() {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("EEE h:mm a - MM/dd/yyyy");

            return auditorium.getName().toUpperCase()
                + " - "
                + startTime.format(formatter).toUpperCase();

        }

        public boolean hasAvailableSeats() {
            return auditorium != null && auditorium.hasAvailableSeats();
        }


        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }

            if (!(object instanceof Showtime showtime)) {
                return false;
            }

            return showtimeId == showtime.showtimeId;
        }

        @Override
        public int hashCode() {
            return Objects.hash(showtimeId);
        }
    }
