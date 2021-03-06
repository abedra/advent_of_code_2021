package com.aaronbedra.advent.one;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.lambda.adt.hlist.Tuple3;
import com.jnape.palatable.shoki.impl.StrictQueue;

import static com.aaronbedra.advent.one.Measurement.*;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.lambda.adt.hlist.Tuple3.fromIterable;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Eq.eq;
import static com.jnape.palatable.lambda.functions.builtin.fn2.GT.gt;
import static com.jnape.palatable.lambda.functions.builtin.fn2.Slide.slide;
import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldLeft.foldLeft;
import static com.jnape.palatable.shoki.impl.StrictQueue.strictQueue;

public class DepthChangeCalculator {
    public static long calculate(StrictQueue<Tuple2<Integer, Measurement>> measurements) {
        return foldLeft(
                (m, v) -> v._2().match(
                        notApplicable -> m,
                        noChange -> m,
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

    public static StrictQueue<Tuple2<Integer, Measurement>> processSlidingWindow(
            StrictQueue<Maybe<Tuple3<Integer, Integer, Integer>>> inputs
    ) {
        return foldLeft(
                (m, v) -> v.match(constantly(m), t -> {
                    int sample = t._1() + t._2() + t._3();
                    return m.cons(tuple(sample, takeMeasurement(m.head().fmap(Tuple2::_1), sample)));
                }),
                strictQueue(),
                inputs);
    }

    public static Measurement takeMeasurement(Maybe<Integer> previousMeasurement, Integer currentMeasurement) {
        return previousMeasurement.match(
                constantly(notApplicable()),
                previous -> eq(currentMeasurement, previous)
                        ? noChange()
                        : gt(currentMeasurement, previous)
                            ? decrease()
                            : increase());
    }

    public static StrictQueue<Maybe<Tuple3<Integer, Integer, Integer>>> partitionInputs(StrictQueue<Integer> inputs) {
        return foldLeft((m, v) -> m.snoc(fromIterable(v)), strictQueue(), slide(3, inputs));
    }
}
