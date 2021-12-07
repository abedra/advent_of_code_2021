package com.aaronbedra.advent.one;

import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.shoki.impl.StrictQueue;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.aaronbedra.advent.one.DepthChangeCalculator.*;
import static com.aaronbedra.advent.one.Measurement.*;
import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.shoki.impl.StrictQueue.strictQueue;
import static com.jnape.palatable.shoki.interop.Shoki.strictQueue;
import static java.nio.file.Files.lines;
import static java.nio.file.Path.of;
import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;

public class DepthChangeCalculatorTest {
    private static final StrictQueue<Integer> SAMPLE = strictQueue(199, 200, 208, 210, 200, 207, 240, 269, 260, 263);
    private static final StrictQueue<Tuple2<Integer, Measurement>> EXPECTED_SAMPLE_OUTPUT = strictQueue(
            tuple(199, notApplicable()),
            tuple(200, increase()),
            tuple(208, increase()),
            tuple(210, increase()),
            tuple(200, decrease()),
            tuple(207, increase()),
            tuple(240, increase()),
            tuple(269, increase()),
            tuple(260, decrease()),
            tuple(263, increase()));

    @Test
    public void noPreviousMeasurement() {
        assertEquals(
                notApplicable(),
                takeMeasurement(nothing(), 1));
    }

    @Test
    public void measurementDecrease() {
        assertEquals(
                decrease(),
                takeMeasurement(just(2), 1));
    }

    @Test
    public void measurementIncrease() {
        assertEquals(
                increase(),
                takeMeasurement(just(1), 2));
    }

    @Test
    public void processInputs() {
        assertEquals(EXPECTED_SAMPLE_OUTPUT.reverse(), process(SAMPLE));
    }

    @Test
    public void sample() {
        assertEquals(7, calculate(EXPECTED_SAMPLE_OUTPUT));
    }

    @Test
    public void fullInput() throws URISyntaxException, IOException {
        assertEquals(1446, calculate(process(readInputs())));
    }

    @Test
    public void partition() {
        assertEquals(
                strictQueue(
                        just(tuple(199, 200, 208)),
                        just(tuple(200, 208, 210)),
                        just(tuple(208, 210, 200)),
                        just(tuple(210, 200, 207)),
                        just(tuple(200, 207, 240)),
                        just(tuple(207, 240, 269)),
                        just(tuple(240, 269, 260)),
                        just(tuple(269, 260, 263))),
                partitionInputs(SAMPLE));
    }

    @Test
    public void processSlidingWindows() {
        assertEquals(
                strictQueue(607, 618, 618, 617, 647, 716, 769, 792),
                processSlidingWindow(partitionInputs(SAMPLE)));
    }

    private StrictQueue<Integer> readInputs() throws URISyntaxException, IOException {
        return strictQueue(lines(of(requireNonNull(getClass().getClassLoader().getResource("one.input")).toURI()))
                .map(Integer::parseInt)
                .toList());
    }
}