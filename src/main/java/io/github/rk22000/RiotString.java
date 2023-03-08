package io.github.rk22000;

import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * This is the core type that is being built up through function calls.
 * Once built this type can be compiled into a java.util.regex.Pattern
 */
public interface RiotString {
    static RiotString from(String expression) {
        return new BasicRiotString((expression));
    }
    RiotString and(String extension);
    default RiotString and(RiotString extension) {
        return and(extension.toString());
    }

    RiotString or(String extension);
    default RiotString or(RiotString extension) {
        return or(extension.toString());
    }
    RiotString times(int repeatCount);
    RiotString wholeTimes(int repeatCount);

    RiotString optional();
    RiotString wholeThingOptional();

    RiotString grouped();
    RiotString groupedAs(String name);
    default RiotString as(String name) {
        return groupedAs(name);
    }
    Pattern compiledPattern();

}

class BasicRiotString implements RiotString {
    private final String string;
    private final boolean unitChain;
    BasicRiotString(String seed) {
        string = seed;
        unitChain = false;
    }
    BasicRiotString(String seed, boolean isUnitChain) {
        string = seed;
        unitChain = isUnitChain;
    }

    @Override
    public String toString() {
        return string;
    }
    @Override
    public RiotString and(String extension) {
        return new BasicRiotString(string + extension, false);
    }
    @Override
    public RiotString or(String extension) {
        return new BasicRiotString(
                "(" + string + "|" + extension + ")",
                true
        );
    }
    @Override
    public RiotString grouped() {
        return new BasicRiotString(
                "(" + string + ")",
                true
        );
    }
    @Override
    public RiotString groupedAs(String name) {
        return new BasicRiotString(
                "(?<" + name + ">" + string + ")",
                true
        );
    }
    private RiotString groupIfNotUnit() {
        RiotString unitExpression = this;
        if (!unitChain)
            unitExpression = grouped();
        return unitExpression;
    }
    @Override
    public RiotString wholeTimes(int repeatCount) {
        return new BasicRiotString(
                groupIfNotUnit() + "{" + repeatCount + "}",
                true
        );
    }

    @Override
    public RiotString times(int repeatCount) {
        return new BasicRiotString(
                string + "{" + repeatCount + "}",
                unitChain
        );
    }

    @Override
    public RiotString wholeThingOptional() {
        return new BasicRiotString(
                groupIfNotUnit() + "?",
                true
        );
    }

    @Override
    public RiotString optional() {
        return new BasicRiotString(
                string + "?",
                unitChain
        );
    }

    @Override
    public Pattern compiledPattern() {
        return Pattern.compile(string);
    }
}

class ChildRiotString extends BasicRiotString {

    private final ChildRiotString mother, father;
    private final Function<RiotString, RiotString> extender;
    ChildRiotString(String seed) {
        super(seed);
        mother = null;
        father = null;
        extender = null;
    }

    ChildRiotString(String seed, boolean isUnitChain) {
        super(seed, isUnitChain);
        mother = null;
        father = null;
        extender = null;
    }
    ChildRiotString(ChildRiotString mother, ChildRiotString father, Function<RiotString, RiotString> extender) {
        super(extender.apply(father).toString());
        this.mother = mother;
        this.father = father;
        this.extender = extender;
    }

}