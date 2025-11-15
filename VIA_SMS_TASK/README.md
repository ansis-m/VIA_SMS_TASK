# Calculator

Simple Java calculator supporting 6 operations: `+`, `-`, `*`, `/`, `%`, `^`

## Usage

```java
Calculator calc = new Calculator();
calc.calculate(5, 3, "+");
calc.calculate(2, 3, "^");
```

## Design Decisions

### Division by Zero Returns Infinity/NaN

We **don't throw exceptions** for division by zero. Instead, we follow IEEE 754 standard:

```java
calc.calculate(5, 0, "/");    // Infinity
calc.calculate(0, 0, "/");    // NaN
calc.calculate(5, 0, "%");    // NaN
```

**Why?**
- Java's `double` type already behaves this way naturally
- Mathematical correctness: limit of x/y as y→0 is infinity
- Results propagate predictably through calculations

### Static Helper Method

The core `calculate(double, double, Operation)` method is static because:
- It's a pure function with no side effects
- Doesn't depend on instance state
- Could be called from any context

The calculator class itself **should probably be fully static** since it has no state, but the task specification showed instance usage so we kept the public API as instance methods.