package io.github.rk22000.RegexRiot;

import java.util.function.Function;
import java.util.regex.Pattern;

public interface RiotString {
    SimpleRiotString simpleRiotStringFrom(String expression);
    SimpleRiotString toSimpleRiotString();
    RiotString and(SimpleRiotString extension);
    default RiotString and(String extension) {
        return this.and(simpleRiotStringFrom(extension));
    }
    default RiotString and(RiotString extension) {
        return this.and(extension.toSimpleRiotString());
    }
    default RiotString and(RiotSet set) {
        return this.and(set.toRiotString());
    }

    RiotString or(SimpleRiotString extension);
    default RiotString or(String extension) {
        return this.or(simpleRiotStringFrom(extension));
    }
    default RiotString or(RiotString extension) {
        return this.or(extension.toSimpleRiotString());
    }
    RiotString wholeTimes(int atleast, int atmost);
    RiotString wholeTimes(int repeatCount);

    RiotString wholeThingOptional();

    RiotString wholeThingGrouped();
    RiotString wholeThingGroupedAs(String name);
    default RiotString as(String name) {
        return wholeThingGroupedAs(name);
    }
    boolean isUnitChain();
    default boolean isNotUnitChain() {
        return !isUnitChain();
    }
    Pattern compiledPattern();
    RiotString times(int atleast, int atmost);
    RiotString times(int repeatCount);
    RiotString optionally();
    RiotString grouped();
    RiotString groupedAs(String name);
}
class ChildRiotString implements RiotString {

    private final Function<SimpleRiotString, SimpleRiotString> mother;
    private final SimpleRiotString father;

    ChildRiotString(SimpleRiotString father) {
        this.mother = simpleRiotString -> simpleRiotString;
        this.father = father;
    }
    ChildRiotString(Function<SimpleRiotString, SimpleRiotString> mother, SimpleRiotString father) {
        this.mother = mother;
        this.father = father;
    }

    @Override
    public String toString() {
        return mother.apply(father).toString();
    }

    @Override
    public SimpleRiotString simpleRiotStringFrom(String expression) {
        return new BasicRiotString(expression);
    }

    @Override
    public SimpleRiotString toSimpleRiotString() {
        return mother.apply(father);
    }

    @Override
    public RiotString and(SimpleRiotString extension) {
        return new ChildRiotString(
                mother.apply(father)::and,
                extension
        );
    }

    @Override
    public RiotString or(SimpleRiotString extension) {
        return new ChildRiotString(
                mother.apply(father)::or,
                extension
        );
    }

    @Override
    public RiotString wholeTimes(int atleast, int atmost) {
        return new ChildRiotString(toSimpleRiotString().wholeTimes(atleast, atmost));
    }

    @Override
    public RiotString wholeTimes(int repeatCount) {
        return new ChildRiotString(mother.apply(father).wholeTimes(repeatCount));
    }

    @Override
    public RiotString wholeThingOptional() {
        return new ChildRiotString(mother.apply(father).wholeThingOptional());
    }

    @Override
    public RiotString wholeThingGrouped() {
        return new ChildRiotString(mother.apply(father).wholeThingGrouped());
    }

    @Override
    public RiotString wholeThingGroupedAs(String name) {
        return new ChildRiotString(mother.apply(father).wholeThingGroupedAs(name));
    }

    @Override
    public boolean isUnitChain() {
        return mother.apply(father).isUnitChain();
    }

    @Override
    public Pattern compiledPattern() {
        return mother.apply(father).compiledPattern();
    }

    @Override
    public RiotString times(int atleast, int atmost) {
        return new ChildRiotString(
                mother,
                father.wholeTimes(atleast, atmost)
        );
    }

    @Override
    public RiotString times(int repeatCount) {
        return new ChildRiotString(
                mother,
                father.wholeTimes(repeatCount)
        );
    }

    @Override
    public RiotString optionally() {
        return new ChildRiotString(
                mother,
                father.wholeThingOptional()
        );
    }

    @Override
    public RiotString grouped() {
        return new ChildRiotString(
                mother,
                father.wholeThingGrouped()
        );
    }

    @Override
    public RiotString groupedAs(String name) {
        return new ChildRiotString(
                mother,
                father.wholeThingGroupedAs(name)
        );
    }

}
