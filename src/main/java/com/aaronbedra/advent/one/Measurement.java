package com.aaronbedra.advent.one;

import com.jnape.palatable.lambda.adt.coproduct.CoProduct4;
import com.jnape.palatable.lambda.functions.Fn1;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static lombok.AccessLevel.PRIVATE;

public abstract class Measurement implements CoProduct4<
        Measurement.NotApplicable,
        Measurement.NoChange,
        Measurement.Decrease,
        Measurement.Increase,
        Measurement> {

    public static NotApplicable notApplicable() {
        return NotApplicable.INSTANCE;
    }

    public static NoChange noChange() {
        return NoChange.INSTANCE;
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
                           Fn1<? super NoChange, ? extends R> bFn,
                           Fn1<? super Decrease, ? extends R> cFn,
                           Fn1<? super Increase, ? extends R> dFn) {
            return aFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class NoChange extends Measurement {
        private static final NoChange INSTANCE = new NoChange();

        @Override
        public <R> R match(Fn1<? super NotApplicable, ? extends R> aFn,
                           Fn1<? super NoChange, ? extends R> bFn,
                           Fn1<? super Decrease, ? extends R> cFn,
                           Fn1<? super Increase, ? extends R> dFn) {
            return bFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Decrease extends Measurement {
        private static final Decrease INSTANCE = new Decrease();

        @Override
        public <R> R match(Fn1<? super NotApplicable, ? extends R> aFn,
                           Fn1<? super NoChange, ? extends R> bFn,
                           Fn1<? super Decrease, ? extends R> cFn,
                           Fn1<? super Increase, ? extends R> dFn) {
            return cFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Increase extends Measurement {
        private static final Increase INSTANCE = new Increase();

        @Override
        public <R> R match(Fn1<? super NotApplicable, ? extends R> aFn,
                           Fn1<? super NoChange, ? extends R> bFn,
                           Fn1<? super Decrease, ? extends R> cFn,
                           Fn1<? super Increase, ? extends R> dFn) {
            return dFn.apply(this);
        }
    }
}
