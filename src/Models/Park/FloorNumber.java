package Models.Park;

public enum FloorNumber {
    GROUND_FLOOR(0),
    FIRST_FLOOR(1),
    SECOND_FLOOR(2),
    THIRD_FLOOR(3),
    FOURTH_FLOOR(4),
    FIFTH_FLOOR(5),
    SIXTH_FLOOR(6),
    UPPER_FLOORS(7);
        private final int value;

        FloorNumber(final int newValue) {
            value = newValue;
        }
        public int getValue() { return value; }
}
