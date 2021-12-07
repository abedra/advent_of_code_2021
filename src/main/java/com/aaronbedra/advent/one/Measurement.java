package com.aaronbedra.advent.one;

import com.jnape.palatable.lambda.adt.coproduct.CoProduct3;
import com.jnape.palatable.lambda.functions.Fn1;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

public abstract class Measurement implements CoProduct3<
        Measurement.NotApplicable,
        Measurement.Decrease,
        Measurement.Increase,
        Measurement> {

    public static NotApplicable notApplicable() {
        return NotApplicable.INSTANCE;
    }

    public static Decrease decrease() {
        return Decrease.INSTANCE;
    }

    public static Increase increase() {
        return Increase.INSTANCE;
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class NotApplicable extends Measurement {
        private static final NotApplicable INSTANCE = new NotApplicable();

        @Override
        public <R> R match(Fn1<? super NotApplicable, ? extends R> aFn,
                           Fn1<? super Decrease, ? extends R> bFn,
                           Fn1<? super Increase, ? extends R> cFn) {
            return aFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Decrease extends Measurement {
        private static final Decrease INSTANCE = new Decrease();

        @Override
        public <R> R match(Fn1<? super NotApplicable, ? extends R> aFn,
                           Fn1<? super Decrease, ? extends R> bFn,
                           Fn1<? super Increase, ? extends R> cFn) {
            return bFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Increase extends Measurement {
        private static final Increase INSTANCE = new Increase();

        @Override
        public <R> R match(Fn1<? super NotApplicable, ? extends R> aFn,
                           Fn1<? super Decrease, ? extends R> bFn,
                           Fn1<? super Increase, ? extends R> cFn) {
            return cFn.apply(this);
        }
    }
}
