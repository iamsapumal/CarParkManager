package Models.Park;

public enum FloorNumber {
    GROUND_FLOOR(0),
    FIRST_FLOOR(1),
    SECOND_FLOOR(2),
    THIRD_FLOOR(3),
    FOURTH_FLOOR(4),
    FIFTH_FLOOR(5),
    SIXTH_FLOOR(6),
    SEVENTH_FLOOR(7),
    EIGHTH_FLOOR(8);

        private final int value;

        FloorNumber(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
}
