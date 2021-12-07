package com.aaronbedra.advent.one;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.shoki.impl.StrictQueue;

import static com.aaronbedra.advent.one.Measurement.*;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.functions.builtin.fn2.GT.gt;
import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldLeft.foldLeft;
import static com.jnape.palatable.shoki.impl.StrictQueue.strictQueue;

public class DepthChangeCalculator {
    public static long calculate(StrictQueue<Tuple2<Integer, Measurement>> measurements) {
        return foldLeft(
                (m, v) -> v._2().match(
                        notApplicable -> m,
                        decrease -> m,
                        increase -> m + 1),
                0,
                measurements);
    }

    public static StrictQueue<Tuple2<Integer, Measurement>> process(StrictQueue<Integer> inputs) {
        return foldLeft(
                (m, v) -> m.cons(tuple(v, takeMeasurement(m.head().fmap(Tuple2::_1), v))),
                strictQueue(),
                inputs);
    }

    public static Measurement takeMeasurement(Maybe<Integer> previousMeasurement, Integer currentMeasurement) {
        return previousMeasurement.match(
                constantly(notApplicable()),
                previous -> gt(currentMeasurement, previous) ? decrease() : increase());
    }
}
