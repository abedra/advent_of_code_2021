package com.aaronbedra.advent.two;

import org.junit.Test;

import static com.aaronbedra.advent.two.Direction.*;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static testsupport.matchers.EitherMatcher.isLeftThat;
import static testsupport.matchers.EitherMatcher.isRightThat;

public class DirectionTest {
    @Test
    public void parseForward() {
        assertThat(parse(tuple("forward", 5)), isRightThat(equalTo(forward(5))));
    }

    @Test
    public void parseDown() {
        assertThat(parse(tuple("down", 5)), isRightThat(equalTo(down(5))));
    }

    @Test
    public void parseUp() {
        assertThat(parse(tuple("up", 5)), isRightThat(equalTo(up(5))));
    }

    @Test
    public void parseInvalid() {
        assertThat(parse(tuple("invalid", 5)), isLeftThat(equalTo("Invalid direction <invalid>")));
    }
}