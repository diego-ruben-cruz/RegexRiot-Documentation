# RegexRiot: A Java Library for Building Regular Expressions

**Authors:** Rahul Kandekar and Diego Cruz
**Project Advisor:** Saptarshi Sengupta
**Affiliations:** Department of Computer Science, San Jose State University  
**Keywords:** Regular Expressions, Java, Kotlin, JavaScript, Library, Pattern Matching, Parsing

## Abstract

Regular expressions are essential tools for developers working with text data. However, constructing regular expressions can be a tedious and error-prone task when dealing with longform string literals. To simplify this process for Java/Kotlin developers, we have developed a Java library called RegexRiot. The library offers a straightforward and intuitive way of constructing regular expressions by using a chain of function calls that describe the pattern to match. While similar projects exist for JavaScript, there is a lack of such libraries for Java/Kotlin. This project aims to bridge this gap and provide a solution for developers working with Java/Kotlin.

## Introduction

_Begin your report by introducing your utility library and providing some background on why you decided to create it. This could include a brief overview of the problem your library is designed to solve and any existing solutions you found._

When creating regex expressions, developers will often have a hard time with keeping track of exactly what parts of the regex expression accomplish which tasks. For example, keeping track of number ranges, such as the years 1899 up to 1991, inclusive, can become complicated as the range will include several or-blocks that will then cause the regex expression to fill the line and possibly go on longer than what fits on the screen, or cause a long word wrapping which can bloat the regex. In both cases, if the programmer leaves the code as is, then comes back to it later to modify it for some reason, there is a problem where the developer will likely have to spend time rereading the regex expression to understand it again if the documentation was not put into place at the time.

One solution to make regex building and management easier includes the `magic-regexp` library which simplifies regex into separate blocks that can break down the expression into separate groups, which one can define as subgroups. `magic-regexp` was made as an alternative to the native JavaScript object `RegExp`. Below is an example which demonstrates how a regex for a phone number can be made with magic-regexp.

```javascript
const NEW_PHONE_RE = createRegExp(
  exactly("+")
    .optionally()
    .and("1")
    .as("country")
    .optionally()
    .and(charIn(" -").optionally())
    .and(charIn("123456789").and(digit.times(3)).as("area"))
    .and(charIn("123456789").and(digit.times(6)).as("rest"))
    .at.lineEnd()
    .at.lineStart()
);
```

Unfortunately, such a solution does not currently exist, or is otherwise not widely adopted among the Java programming community. With that in mind, RegexRiot is a Java library heavily inspired from `magic-regexp` that contains several objects, at the core of which is a RiotString object. This particular RiotString object is an immutable object that behaves in an append-only fashion. However, modified copies can be made which will be covered in more detail in **Design** and **Implementation**.

## Design

_This section should focus on the design of your utility library. You could include information on the design patterns and principles you used, the architecture of your library, and any relevant UML diagrams._

The most important features of RegexRiot when conducting research and designing the library were specified as follows:

1. Chain of function calls: The library will provide a chain of function calls that will allow developers to construct regular expressions by describing the pattern they want to match. This approach will make it easy for developers to create complex regular expressions without the need for extensive knowledge of the underlying syntax.

2. Intuitive syntax: The library will use an intuitive syntax that will make it easy for developers to understand and use regular expressions. The syntax will be designed to be easy to read and will provide a clear description of the pattern to match.

3. Support for Java and Kotlin: The library will be developed for Java developers. This will provide a solution for developers working with these languages who are looking for an easy-to-use regular expression library.

4. Wide range of functionalities: The library will provide a wide range of functionalities for pattern matching, including capturing groups, non-capturing groups, character classes, quantifiers, and more. This will provide developers with a powerful tool for working with text data.

With those aforementioned features taken into account, below are some UML diagrams that depict the overall architecture of RegexRiot.

![Class Diagram](/images/RegexRiot_Class_Diagram.png "Class Diagram")

![Object Diagram](/images/RegexRiot_Object_Diagram.png "Object Diagram")

RegexRiot is heavily inspired by the `magix-regexp` library, and as such, follows both the imperative and declarative programming paradigms. RegexRiot follows the imperative programming in that it is based around calling functions to build individual components of the expression, which can then be used to construct an overall group that is a larger chunk of the master expression. With respect to the declarative paradigm, RegexRiot follows in the steps of `magic-regexp` in that it provides a simpler and more readable syntax for the creation and maintenance of regex.

## Implementation

_In this section, describe how you implemented your utility library in Java. You could discuss any notable coding decisions you made, how you organized your code, and any interesting algorithms or data structures you used._

The implementation of RegexRiot heavily depends on the usage of tokens to denote specific primitives such as characters, integers, floats, etc. This was an important coding decision in the context of the project because it allowed for the simplification of regex expressions while also reducing the need for multiple comments. With the use of tokens, it is possible to understand individual blocks of the regex expression, where the programmer would likely only need to comment on subgroups and the whole overall group to clarify what 'chunks' accomplish what tasks in parsing.

In the following example code, RiotTokens is an interface that outlines the tokens that can be used in conjunction with the RiotString.

```java
public interface RiotTokens {
    /**
     * This should be safe to use as argument to use as Argument to RiotSet.chars()
     */
    RiotString DIGIT = newLazyRiot("\\d", true),
                    DOT = newLazyRiot("\\.", true),
                    WORD_CHAR = newLazyRiot("\\w", true),
                    OPEN_BRACE = newLazyRiot("\\(", true),
                    CLOSE_BRACE = newLazyRiot("\\)", true),
                    OPEN_SQ_BRACE = newLazyRiot("\\[", true),
                    CLOSE_SQ_BRACE = newLazyRiot("\\]", true),
                    QUESTION_MARK = newLazyRiot("\\?", true),
                    BOUNDARY = newLazyRiot("\\b", true),
                    SPACE = newLazyRiot("\\s", true),
                    SPACES = oneOrMore(SPACE),
                    PLUS = newLazyRiot("\\+", true);
    /**
     * This is not safe to use as arguments to RiotSet.chars()
     */
    RiotString ANY_CHAR = newLazyRiot("."),
                    LINE_START = newLazyRiot("^", true),
                    Line_END = newLazyRiot("$", true);
    RiotSet HEX = inSetOf(DIGIT)
                    .andChars('a').through('f')
                    .andChars('A').through('F'),
                    HEX_LOWER = inSetOf(DIGIT)
                                    .andChars('a').through('f'),
                    HEX_UPPER = inSetOf(DIGIT)
                                    .andChars('A').through('F');
    int UNLIMITED = -1;

}
```

A similar approach was used in definition of frequency of a certain group, where the number of expected instances was simplified from using symbols, to lexical modifier names. This is shown in the interface below:

```java
public class RiotQuantifiers {
    public static RiotString oneOrNone(RiotString ritex) {
        return ritex.wholeThingOptional();
    }

    public static <T extends RiotString.RiotStringable> RiotString oneOrNone(T expression) {
        return oneOrNone(expression.toRiotString());
    }

    public static <T> RiotString oneOrNone(T expression) {
        return oneOrNone(lazyRiot(expression.toString()));
    }

    public static RiotString zeroOrMore(RiotString ritex) {
        return ritex.wholeZeroOrMoreTimes();
    }

    public static <T extends RiotString.RiotStringable> RiotString zeroOrMore(T expression) {
        return zeroOrMore(expression.toRiotString());
    }

    public static <T> RiotString zeroOrMore(T expression) {
        return zeroOrMore(lazyRiot(expression.toString()));
    }

    public static RiotString oneOrMore(RiotString ritex) {
        return ritex.wholeOnceOrMoreTimes();
    }

    public static <T extends RiotString.RiotStringable> RiotString oneOrMore(T expression) {
        return oneOrMore(expression.toRiotString());
    }

    public static <T> RiotString oneOrMore(T expression) {
        return oneOrMore(lazyRiot(expression.toString()));
    }
}
```

For the sake of simplicity, the above code block shows one approach to how RegexRiot can build expressions, but it is also possible for RiotStrings to take Strings as input, that may have been previously built by other RiotStrings, and build upon them to create a larger regex expression.

When defining ranges, RegexRiot has a native way of handling those by way of defining them as overall 'sets' shown here:

```java
public interface RiotSet extends RiotString.RiotStringable {
    static <T>RiotSet inSetOf(T seed) {
        return RiotSetImplementations.lazyInclusiveSetOf(seed.toString());
    }
    static <T>RiotSet outSetOf(T seed) {
        return RiotSetImplementations.lazyExclusiveSetOf(seed.toString());
    }
    <T>RiotSet and(T extension);
    @FunctionalInterface
    interface RiotSetRange {
        RiotSet through(char endCharInclusive);
    }
    RiotSetRange andChars(char startCharInclusive);
    RiotSet complement();
    default RiotString toRiotString() {
        return newLazyRiot(toString(), true);
    }
    default RiotString andThen(RiotString expression) {
        return toRiotString().then(expression);
    }
}
```

Those are the core components of the inner workings of RegexRiot, with additional functions covered to account for taking in a String as input as opposed to objects defined by RegexRiot. Further details on how to use the library will be outlined in the **Usage** section of the report.

## Usage

_Provide instructions on how to use your utility library. This could include code examples and explanations of the library's API._

RegexRiot is a library that is designed to be able to branch out regex into subgroups and be able to mark them with a certain alias such that they can be identified upon first reading through the code. Then, additional comments made can clarify upon larger subgroups as to how they contribute to the larger overall master expression.

Let's walk through an example of parsing an input for one name, or another name in the context of a riotString. Suppose we were searching to match either `Bugs Bunny` or `DaffyDaffy` in the input. The first thing we would do is create a RiotString through calling `riot(String seed)` as shown below:

```java
ritex = riot("Bugs")
```

But we are not finished yet, so we do not immediately use the `;` character. We want to include the `" Bunny"` portion of the string, so we shall append `.and(String extension)` like so:

```java
ritex = riot("Bugs").then(" Bunny")
```

To assign an alias to `Bugs Bunny`, we shall use `.as(String name)` to mark it with the tag `name01` like so:

```java
ritex = riot("Bugs").then(" Bunny").as("name01")
```

Now that we have made the first name, we must make use of the `.or(RiotString extension)` to begin making the `DaffyDaffy` portion of the regex, like so:

```java
ritex = riot("Bugs").then(" Bunny").as("name01")
    .or(riot("Daffy"))
```

Note that a new `riot()` expression was enclosed, which allows a similar technique to be used as was the case with `Bugs Bunny`. The main difference in creating this sub-instance of a RiotString is that we must use the `.times(int repeatCount)` method to define that `Daffy` will occur twice, like so:

```java
ritex = riot("Bugs").then(" Bunny").as("name01")
    .or(riot("Daffy").times(2))
```

We will finish this regex by assigning the name `name02` to the regex associated with `DaffyDaffy`.

```java
ritex = riot("Bugs").then(" Bunny").as("name01")
    .or(riot("Daffy").times(2).as("name02"));
```

The final expression will appear as shown above, but playing around with the formatting of the expression as shown below can allow for comments to surround enclosing blocks of `.or()` or `.and()` to allow for greater clarification when building more complicated regex.

```java
ritex = riot("Bugs").then(" Bunny").as("name01")
    .or(
        riot("Daffy").times(2).as("name02")
    );
resultingRegex = "(?<name01>Bugs Bunny)|(?<name02>(Daffy){2})";
```

Let us make a more complicated expression where it should match 24-bit hexadecimal grayscale colors such as `#000000`, `#a9a9a9`, `#848484` as well as 12-bit hexadecimal grayscale colors such as `#FFF` and `#000`.

Making the base, we should have the symbol `#` appear on ALL instances of the color, like so:

```java
ritex = riot("#")
```

From there, we need to make use of `.and(RiotString extension)`, `.or(RiotString extension)`, as well as the `.times(int repeatCount)` or `.times(int atLeast, int atMost)`. The resulting expression will result in the following expression:

```java
ritex = riot("#").then(
        DIGIT.or(.inSetOf("").andChars('A').through('F'))
        .or(inSetOf("").andChars('a').through('f'))
    ).times(1, 2)
```

In the above code, `DIGIT` is a previously defined token as explained in the **Implemenation** section of the report, and `chars(char inclusiveStartChar).through(char inclusiveEndChar)` is a utility range method that was also previously defined. However, this does not immediately account for the fact that following the hexadecimal declaration denoted by `#`, there can be either three characters or six characters, so we can define the previous code block as a group, and have it possibly repeat twice like so:

```java
ritex = riot("#").then(
        DIGIT.or(.inSetOf("").andChars('A').through('F'))
        .or(inSetOf("").andChars('a').through('f'))
    ).times(1, 2).grouped().then(group(1)).times(2);
resultingRegex = "#((?:\\d|[A-F]|[a-f]){1,2})\\1{2}";
```

This provides a basic outline for the functionality of RegexRiot as it had been designed. What's left is to discuss the types of testing that had been done, and make a few comments on the current state of the library as it is initially distributed nearing the end of May 2023.

## Testing

_Describe how you tested your utility library. You could include information on any testing frameworks you used, the types of tests you conducted, and the results of your tests._

Testing was largely conducted with the help of a website known as [regextutorials](https://regextutorials.com), which contain both tutorials and practice exercises on how to make JavaScript Regex, which helps to understand some of the decisions made for the magic-regexp library during its implementation.

In the following examples, modifications would have to be made such that the double backslash `\\` would be reduced to a single backslash `\` to be an acceptable regex expression in JavaScript when verifying the output on [regextutorials](https://regextutorials.com).

Below is an example of how RegexRiot would generate a regex expression to match numbers that contain a floating point as per [regextutorials.com](http://regextutorials.com/excercise.html?Floating%20point%20numbers).

```java
        answer = "\\d+\\.\\d+"; // What RegexRiot would generate
        ritex = oneOrMore(DIGIT).then(DOT).then(oneOrMore(DIGIT));
```

Below is an example of how RegexRiot would generate a regex expression to match the titles of all films produced before 1990 as per the regextutorials website [regextutorials.com](http://regextutorials.com/excercise.html?Years%20before%201990).

```java
answer = "^.+\\((19[0-8]\\d|\\d{3}|\\d{2}|\\d)\\)"; // What RegexRiot would generate
ritex = LINE_START.then(oneOrMore(ANY_CHAR))
    .then(OPEN_BRACE)
    .then(
        riot("19").then(
                inSetOf("").andChars('0').through('8')
        ).then(DIGIT).or(
                DIGIT.times(3)
        ).or(
                DIGIT.times(2)
        ).or(
                DIGIT
        ).wholeThingGrouped()
    )
    .then(CLOSE_BRACE);
```

Below is an example of how RegexRiot would generate a regex expression to match the 12 and 24 bit colors whose red/green/blue components are equal to each other as per the regextutorials website [regextutorials.com](http://regextutorials.com/excercise.html?Grayscale%20colors).

```java
answer = "#((?:\\d|[A-F]|[a-f]){1,2})\\1{2}"; // What RegexRiot would generate
ritex = riot("#")
        .then(
                DIGIT.or(
                        inSetOf("").andChars('A').through('F')
                ).or(
                        inSetOf("").andChars('a').through('f')
                )
        ).times(1, 2)
        .grouped()
        .then(group(1))
        .times(2);
```

## Future Work and Concluding Statements

_Conclude your report with a discussion of potential future work for your utility library. This could include new features, bug fixes, or improvements to existing functionality._

RegexRiot has been released as a `.jar` package that is immediately available on GitHub along with the entirety of the source code which prospective users can download and build on their local machines. It is clear that this library remains in infancy, because testing was modeled on a small sample size from [regextutorials](https://regextutorials.com) where the intention was to teach regex well enough to know the basics, but not enough to be able to comprehensively teach users how to cover particular edge cases. In a few of the exercises, the regex generated by us may contain test escapes that were not accounted for by [regextutorials](https://regextutorials.com).

Future releases may be present in the form of a public Maven repository, or an entirely different approach can be used and perhaps a package manager could be made. However, it remains to be seen whether a package manager would be necessary for a library of such scope.

RegexRiot is an open-source software, and as such, we invite others to build it from source, and contribute to improve upon it. The documentation for the software is present in the form of javadoc, but perhaps there is an opportunity to make a presentable documentation in a `readme.md` right from the repository page in GitHub.
