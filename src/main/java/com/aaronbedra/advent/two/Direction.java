package com.aaronbedra.advent.two;

import com.jnape.palatable.lambda.adt.Either;
import com.jnape.palatable.lambda.adt.coproduct.CoProduct3;
import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.lambda.functions.Fn1;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;

import static com.jnape.palatable.lambda.adt.Either.left;
import static com.jnape.palatable.lambda.adt.Either.right;
import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

public abstract class Direction implements CoProduct3<Direction.Forward, Direction.Down, Direction.Up, Direction> {

    abstract Integer getValue();

    public static Either<String, Direction> parse(Tuple2<String, Integer> inputs) {
        return switch (inputs._1()) {
            case "forward" -> right(forward(inputs._2()));
            case "down"    -> right(down(inputs._2()));
            case "up"      -> right(up(inputs._2()));
            default        -> left(format("Invalid direction <%s>", inputs._1()));
        };
    }

    public static Forward forward(Integer value) {
        return new Forward(value);
    }

    public static Down down(Integer value) {
        return new Down(value);
    }

    public static Up up(Integer value) {
        return new Up(value);
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Forward extends Direction {
        Integer value;

        @Override
        public <R> R match(Fn1<? super Forward, ? extends R> aFn,
                           Fn1<? super Down, ? extends R> bFn,
                           Fn1<? super Up, ? extends R> cFn) {
            return aFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Down extends Direction {
        Integer value;

        @Override
        public <R> R match(Fn1<? super Forward, ? extends R> aFn,
                           Fn1<? super Down, ? extends R> bFn,
                           Fn1<? super Up, ? extends R> cFn) {
            return bFn.apply(this);
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Value
    @AllArgsConstructor(access = PRIVATE)
    public static class Up extends Direction{
        Integer value;

        @Override
        public <R> R match(Fn1<? super Forward, ? extends R> aFn,
                           Fn1<? super Down, ? extends R> bFn,
                           Fn1<? super Up, ? extends R> cFn) {
            return cFn.apply(this);
        }
    }
}
