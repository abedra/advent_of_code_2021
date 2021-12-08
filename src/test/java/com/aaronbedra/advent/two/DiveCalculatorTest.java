package com.aaronbedra.advent.two;

import com.aaronbedra.advent.two.Types.Aim;
import com.aaronbedra.advent.two.Types.Horizontal;
import com.aaronbedra.advent.two.Types.Vertical;
import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.shoki.impl.StrictQueue;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.aaronbedra.advent.testsupport.ReadTestInput.readStringInputs;
import static com.aaronbedra.advent.two.Direction.*;
import static com.aaronbedra.advent.two.DiveCalculator.*;
import static com.jnape.palatable.lambda.adt.Either.right;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.shoki.impl.StrictQueue.strictQueue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static testsupport.matchers.EitherMatcher.isLeftThat;
import static testsupport.matchers.EitherMatcher.isRightThat;

public class DiveCalculatorTest {
    private static final StrictQueue<String> SAMPLE_INPUTS = strictQueue(
            "forward 5",
            "down 5",
            "forward 8",
            "up 3",
            "down 8",
            "forward 2");
    private static final StrictQueue<Either<String, Direction>> SAMPLE_OUTPUTS = strictQueue(
            right(forward(5)),
            right(down(5)),
            right(forward(8)),
            right(up(3)),
            right(down(8)),
            right(forward(2)));

    @Test
    public void validForward() {
        assertThat(parseLine("forward 5"), isRightThat(equalTo(forward(5))));
    }

    @Test
    public void validDown() {
        assertThat(parseLine("down 5"), isRightThat(equalTo(down(5))));
    }

    @Test
    public void validUp() {
        assertThat(parseLine("up 3"), isRightThat(equalTo(up(3))));
    }

    @Test
    public void invalidDirection() {
        assertThat(parseLine("invalid 6"), isLeftThat(equalTo("Invalid direction <invalid>")));
    }

    @Test
    public void invalidPositionalUnit() {
        assertThat(parseLine("forward invalid"), isLeftThat(equalTo("Invalid positional unit <invalid>")));
    }

    @Test
    public void sampleInputParsing() {
        assertEquals(SAMPLE_OUTPUTS, parseInputs(SAMPLE_INPUTS));
    }

    @Test
    public void sampleInputPositionCalculation() {
        assertEquals(tuple(new Horizontal(15), new Vertical(10)), positionAndDepth(SAMPLE_OUTPUTS));
    }

    @Test
    public void sampleInputFinalCalculation() {
        assertEquals(150, calculate(tuple(new Horizontal(15), new Vertical(10))));
    }

    @Test
    public void fullInput() throws URISyntaxException, IOException {
        assertEquals(1660158, calculate(positionAndDepth(parseInputs(readStringInputs("two.input")))));
    }

    @Test
    public void sampleInputPositionCalculationDayTwo() {
        assertEquals(tuple(new Horizontal(15), new Vertical(60), new Aim(10)), positionAndDepthPart2(SAMPLE_OUTPUTS));
    }

    @Test
    public void sampleInputFinalCalculationPart2() {
        assertEquals(900, calculatePart2(tuple(new Horizontal(15), new Vertical(60), new Aim(10))));
    }

    @Test
    public void fullInputPart2() throws URISyntaxException, IOException {
        assertEquals(1604592846, calculatePart2(positionAndDepthPart2(parseInputs(readStringInputs("two.input")))));
    }
}