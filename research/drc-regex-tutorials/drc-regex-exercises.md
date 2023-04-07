# RegEx Exercises

This document takes exercises from [regextutorials.com](http://regextutorials.com/index.html) and attempts to collect RegEx as well as insights into how one could possibly go about implementing them using RegexRiot.

Something to keep in mind is that the following regular expressions are made with JavaScript conventions, and would need further research to run an effective equivalent to Java.

## Exercise 1: Match numbers containing floating point. Skip those that don't.

Example values include

- Acceleration of gravity 9.80665 m/s^2
- Circumference to diameter ratio 3.141592
- Gas constant 8.3144621 J/mol\*K

Answer:

```js
/\d+\.\d+/g;
```

### Evaluation

One of the first challenges I came across was in creating an effective way to capture a decimal point as intended. In JS regex, the decimal point denotes a single occurrence of a character. A forwardslash `\` is required to ensure that we are referring to the character exclusively, rather than the single-character denotation it conventionally has.

Floating point numbers could be a worthwhile addition as a shorthand in RegexRiot, and this would make good data for implementation.

## Exercise 2: Match titles of all films produced before 1990

Example values include

- 2 The Godfather (1972)
- 3 The Godfather: Part II (1974)
- 5 The Good, the Bad and the Ugly (1966)
- 7 12 Angry Men (1957)

Answer:

```js
/\d+\s(\w+\s|\w+\W\s)+\(([0-9]|[1-9][0-9]|[1-9][0-9][0-9]|1[0-8][0-9][0-9]|19[0-8][0-9]|1990)\)/g;
```

Breakdown:

```js
/ // Beginning of js regex
\d+\s // Denotes the "## " as per example values
(\w+\s|\w+\W\s)+ // Denotes the alphanumeric characters of the tile, including punctuation such as ":,"
\( // Denotes the character "("
    // The following is a bunch of OR conditions defining the acceptable boundaries for the
    ([0-9]| // Defines numrange 0-9 as acceptable
    [1-9][0-9]| // Defines numrange 10-99 as acceptable
    [1-9][0-9][0-9]| // Defines numrange 100-999 as acceptable
    1[0-8][0-9][0-9]| // Defines numrange 1000-1899 as acceptable
    19[0-8][0-9]| // Defines numrange 1900-1989 as acceptable
    1990) // Accepts 1990 as edge value
    \) // Denotes character ")"
/g // End of js regex
```

### Evaluation

This particular regex is a far more difficult exercise, because it forces abstract thinking.

Number ranges can become quite time-consuming, as it took me at least 15 minutes. If a programmer does not use regex often, they may be forced to re-learn the material each time they have to use regex for their job. Perhaps RegexRiot should find a way to implement number ranges to do the work by defining a lower bound and an upper bound, inclusive.

The rest wasn't too bad, but it can be a learning curve to better optimize the regex to not use as many characters to achieve the same thing. To do this, I used groups to accept any and all alphanumeric input following the number from the list-like example values.
