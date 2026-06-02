package Exceptions;
public class ReservationLimitException extends Exception {
    public ReservationLimitException(String message) {
        super(message);
    }
}