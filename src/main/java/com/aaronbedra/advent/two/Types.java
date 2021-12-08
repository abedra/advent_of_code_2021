package com.aaronbedra.advent.two;

public class Types {
    public record Horizontal(long value) {
        public Horizontal increment(long value) {
            return new Horizontal(this.value + value);
        }
    }
    public record Vertical(long value) {
        public Vertical increment(long value) {
            return new Vertical(this.value + value);
        }

        public Vertical decrement(long value) {
            return new Vertical(this.value - value);
        }
    }
}
