package com.aaronbedra.advent.two;

import com.aaronbedra.advent.two.Types.Aim;
import com.aaronbedra.advent.two.Types.Horizontal;
import com.aaronbedra.advent.two.Types.Vertical;
import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.lambda.adt.hlist.Tuple3;
import com.jnape.palatable.shoki.impl.StrictQueue;

import java.util.Arrays;

import static com.jnape.palatable.lambda.adt.Either.left;
import static com.jnape.palatable.lambda.adt.Either.trying;
import static com.jnape.palatable.lambda.adt.hlist.HList.tuple;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.functions.builtin.fn3.FoldLeft.foldLeft;
import static com.jnape.palatable.shoki.impl.StrictQueue.strictQueue;
import static java.lang.Integer.parseInt;
import static java.lang.String.format;

public class DiveCalculator {
    public static long calculate(Tuple2<Horizontal, Vertical> position) {
        return position._1().value() * position._2().value();
    }

    public static Tuple2<Horizontal, Vertical> positionAndDepth(StrictQueue<Either<String, Direction>> log) {
        return foldLeft(
                (m, v) -> v.match(
                        s -> m,
                        direction -> direction.match(
                                forward -> tuple(m._1().increment(forward.getValue()), m._2()),
                                down -> tuple(m._1(), m._2().increment(down.getValue())),
                                up -> tuple(m._1(), m._2().decrement(up.getValue())))),
                tuple(new Horizontal(0), new Vertical(0)),
                log);
    }

    public static long calculatePart2(Tuple3<Horizontal, Vertical, Aim> position) {
        return position._1().value() * position._2().value();
    }

    public static Tuple3<Horizontal, Vertical, Aim> positionAndDepthPart2(StrictQueue<Either<String, Direction>> log) {
        return foldLeft(
                (m, v) -> v.match(
                        s -> m,
                        direction -> direction.match(
                                forward -> tuple(m._1().increment(forward.getValue()), m._2().increment(forward.getValue() * m._3().value()), m._3()),
                                down -> tuple(m._1(), m._2(), m._3().increment(down.getValue())),
                                up -> tuple(m._1(), m._2(), m._3().decrement(up.getValue())))),
                tuple(new Horizontal(0), new Vertical(0), new Aim(0)),
                log);
    }

    public static StrictQueue<Either<String, Direction>> parseInputs(StrictQueue<String> inputs) {
        return foldLeft((m, v) -> m.snoc(parseLine(v)), strictQueue(), inputs);
    }

    public static Either<String, Direction> parseLine(String line) {
        String[] s = line.split(" ");
        if (s.length != 2) {
            return left(format("Expected 2 entries, got <%s>", s.length));
        }

        return trying(() -> parseInt(s[1]), constantly(format("Invalid positional unit <%s>", s[1])))
                .flatMap(integer -> Direction.parse(tuple(s[0], integer)));
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString("".split(" ")));
    }
}
