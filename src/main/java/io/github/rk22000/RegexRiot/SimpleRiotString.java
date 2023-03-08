package io.github.rk22000.RegexRiot;

import java.util.regex.Pattern;

/**
 * This is the core type that is being built up through function calls.
 * Once built this type can be compiled into a java.util.regex.Pattern
 */
public interface SimpleRiotString {
    SimpleRiotString riotStringFrom(String expression);
    SimpleRiotString and(SimpleRiotString extension);
    default SimpleRiotString and(String extension) {
        return this.and(riotStringFrom(extension));
    }

    SimpleRiotString or(SimpleRiotString extension);
    default SimpleRiotString or(String extension) {
        return this.or(riotStringFrom(extension));
    }
//    RiotString times(int repeatCount);
    SimpleRiotString wholeTimes(int atleast, int atmost);
    SimpleRiotString wholeTimes(int repeatCount);

    SimpleRiotString wholeThingOptional();

    SimpleRiotString wholeThingSetified();
    SimpleRiotString wholeThingGrouped();
    SimpleRiotString wholeThingGroupedAs(String name);
    default SimpleRiotString as(String name) {
        return wholeThingGroupedAs(name);
    }
    boolean isUnitChain();
    Pattern compiledPattern();

}

class BasicRiotString implements SimpleRiotString {
    private static final String EMPTY_SEED_EXCEPTION = "Can not create Riot String from empty String.\n " +
            "Use the EMPTY() RiotToken instead.";
    private final String string;
    private final boolean unitChain;
    private BasicRiotString() {
        string = "";
        unitChain = false;
    }
    BasicRiotString(String seed) throws IllegalArgumentException {
        if (seed.isEmpty()) throw new IllegalArgumentException(EMPTY_SEED_EXCEPTION);
        string = seed;
        unitChain = false;
    }
    BasicRiotString(String seed, boolean isUnitChain) {
        if (seed.isEmpty()) throw new IllegalArgumentException(EMPTY_SEED_EXCEPTION);
        string = seed;
        unitChain = isUnitChain;
    }

    static SimpleRiotString emptyBasicRiotString() {
        return new BasicRiotString();
    }
    @Override
    public String toString() {
        return string;
    }

    @Override
    public boolean isUnitChain() {
        return unitChain;
    }

    @Override
    public SimpleRiotString riotStringFrom(String expression) throws IllegalArgumentException {
        if (expression.isEmpty()) throw new IllegalArgumentException(EMPTY_SEED_EXCEPTION);
        return new BasicRiotString(expression, expression.length()==1);
    }

    @Override
    public SimpleRiotString and(SimpleRiotString extension) {
        return new BasicRiotString(
                string + extension,
                string.isEmpty() && extension.isUnitChain() || isUnitChain() && extension.toString().isEmpty()
        );
    }
    @Override
    public SimpleRiotString or(SimpleRiotString extension) {
        return new BasicRiotString(
                string + "|" + extension,
                false
        );
    }

    @Override
    public SimpleRiotString wholeThingSetified() {
        return new BasicRiotString(
                "[" + string + "]",
                true
        );
    }

    @Override
    public SimpleRiotString wholeThingGrouped() {
        return new BasicRiotString(
                "(" + string + ")",
                true
        );
    }
    @Override
    public SimpleRiotString wholeThingGroupedAs(String name) {
        return new BasicRiotString(
                "(?<" + name + ">" + string + ")",
                true
        );
    }
    private SimpleRiotString groupIfNotUnit() {
        SimpleRiotString unitExpression = this;
        if (!unitChain)
            unitExpression = wholeThingGrouped();
        return unitExpression;
    }

    @Override
    public SimpleRiotString wholeTimes(int atleast, int atmost) {
        var most = String.valueOf(atmost);
        if(atmost == -1) {
            most = "";
        }
        return new BasicRiotString(
                groupIfNotUnit() + "{" + atleast + "," + most + "}",
                true
        );
    }

    @Override
    public SimpleRiotString wholeTimes(int repeatCount) {
        return new BasicRiotString(
                groupIfNotUnit() + "{" + repeatCount + "}",
                true
        );
    }

    @Override
    public SimpleRiotString wholeThingOptional() {
        return new BasicRiotString(
                groupIfNotUnit() + "?",
                true
        );
    }

//    @Override
//    public RiotString times(int repeatCount) {
//        return wholeTimes(repeatCount);
//    }
//
//    @Override
//    public RiotString optional() {
//        return wholeThingOptional();
//    }

    @Override
    public Pattern compiledPattern() {
        return Pattern.compile(string);
    }
}

//class ChildRiotString extends BasicRiotString {
//    private final SimpleRiotString mother, father;
//    private final Function<SimpleRiotString, SimpleRiotString> extender;
//    ChildRiotString(String seed) {
//        super(seed);
//        mother = null;
//        father = null;
//        extender = null;
//    }
//    ChildRiotString(SimpleRiotString expression) {
//        super(expression.toString(), expression.isUnitChain());
//        mother = null;
//        father = null;
//        extender = null;
//    }
//    ChildRiotString(SimpleRiotString mother, SimpleRiotString father, Function<SimpleRiotString, SimpleRiotString> extender) {
//        super(extender.apply(father).toString(), extender.apply(father).isUnitChain());
//        this.mother = mother;
//        this.father = father;
//        this.extender = extender;
//    }
//
//    @Override
//    public SimpleRiotString and(SimpleRiotString extension) {
//        return new ChildRiotString(this, extension, super::and);
//    }
//
//    @Override
//    public SimpleRiotString or(SimpleRiotString extension) {
//        return new ChildRiotString(this, extension, super::or);
//    }
//
//
//}