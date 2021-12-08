package com.aaronbedra.advent.testsupport;

import com.jnape.palatable.shoki.impl.StrictQueue;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.jnape.palatable.shoki.interop.Shoki.strictQueue;
import static java.nio.file.Files.lines;
import static java.nio.file.Path.of;
import static java.util.Objects.requireNonNull;

public class ReadTestInput {
    public static StrictQueue<Integer> readIntegerInputs(String file) throws URISyntaxException, IOException {
        return strictQueue(lines(of(requireNonNull(ReadTestInput.class.getClassLoader().getResource(file)).toURI()))
                .map(Integer::parseInt)
                .toList());
    }

    public static StrictQueue<String> readStringInputs(String file) throws URISyntaxException, IOException {
        return strictQueue(lines(of(requireNonNull(ReadTestInput.class.getClassLoader().getResource(file)).toURI()))
                .toList());
    }
}
