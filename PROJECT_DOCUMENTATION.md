# 📖 Gear Train Problem - Technical Documentation

## 🎯 The Challenge

During my Google Foobar challenge experience, I encountered this fascinating mechanical engineering problem disguised as a programming challenge. The problem combines mathematical modeling, algorithm design, and constraint satisfaction in an elegant way.

### Problem Statement

You're given a series of pegs mounted on a board at specific positions. Your task is to place gears on these pegs such that:

1. Each gear is centered on a peg
2. Adjacent gears mesh together (their teeth interlock)
3. The first gear rotates at exactly **twice the speed** of the last gear
4. All gears must have a radius of at least 1 unit

The challenge: determine the radius of the first gear that satisfies these constraints, or report if no solution exists.

## 🧮 Mathematical Foundation

### Core Principles

The solution leverages two fundamental mechanical principles:

1. **Gear Meshing**: When two gears mesh, the distance between their centers equals the sum of their radii:
   ```
   distance(peg[i], peg[i+1]) = radius[i] + radius[i+1]
   ```

2. **Speed Relationship**: For meshing gears, the product of radius and angular velocity is constant:
   ```
   radius[i] × speed[i] = radius[i+1] × speed[i+1]
   ```
   
   This means larger gears rotate slower, and the speed ratio is inversely proportional to the radius ratio.

### Deriving the Solution

For a gear train with n pegs, we establish:

- Let `r₁, r₂, ..., rₙ` be the radii of the n gears
- Let `d₁, d₂, ..., dₙ₋₁` be the distances between consecutive pegs
- We know: `r₁ × ω₁ = rₙ × ωₙ` and `ω₁ = 2ωₙ`
- Therefore: `r₁ = 2rₙ`

This creates a system of linear equations that can be solved algebraically.

### The Mathematical Solution

Through algebraic manipulation, the first gear's radius can be expressed as:

```
r₁ = 2 × Σ((-1)ⁱ × dᵢ) / k

where:
- i ranges from 0 to n-2
- dᵢ is the distance between peg i and peg i+1
- k = 3 for even number of pegs, k = 1 for odd number of pegs
```

## 💻 Implementation Details

### Algorithm Structure

```java
public int[] solution(int[] pegs) {
    // 1. Validate input
    if (pegs.length < 2) return new int[]{-1, -1};
    
    // 2. Calculate the sum with alternating signs
    int sum = 0;
    for (int i = 0; i < pegs.length - 1; i++) {
        sum += Math.pow(-1, i) * (pegs[i + 1] - pegs[i]);
    }
    
    // 3. Apply the formula based on peg count parity
    int numerator, denominator;
    if (pegs.length % 2 == 0) {
        numerator = 2 * sum;
        denominator = 3;
    } else {
        numerator = 2 * sum;
        denominator = 1;
    }
    
    // 4. Simplify the fraction
    int gcd = getGCD(Math.abs(numerator), denominator);
    numerator /= gcd;
    denominator /= gcd;
    
    // 5. Validate all gear radii are >= 1
    if (!validateGearRadii(pegs, numerator, denominator)) {
        return new int[]{-1, -1};
    }
    
    return new int[]{numerator, denominator};
}
```

### Key Components

1. **Input Validation**: Ensures we have at least 2 pegs
2. **Mathematical Calculation**: Implements the derived formula
3. **Fraction Simplification**: Reduces the fraction to lowest terms using GCD
4. **Constraint Validation**: Verifies all gear radii are ≥ 1

### Alternative Approach: Continued Fractions

The `AlternateFinding.java` implementation uses a different strategy:

```java
// 1. Calculate the exact decimal value
double r1 = calculateFirstRadius(pegs);

// 2. Convert to fraction using continued fractions algorithm
Rational fraction = toFraction(r1);

// 3. Validate and return
```

This approach is more numerically stable for edge cases where floating-point precision matters.

## 🔍 Edge Cases and Complexity

### Edge Cases Handled

1. **Two Pegs**: Simplest case with direct formula `r₁ = 2d/3`
2. **Invalid Configurations**: Where gear radii would be < 1
3. **Large Distances**: Ensuring numerical stability
4. **Fractional Results**: Properly handling cases like 16⅔

### Complexity Analysis

- **Time Complexity**: O(n) - Single pass through pegs for calculation and validation
- **Space Complexity**: O(1) - Only stores intermediate calculations

## 🎨 Visualization Component

The included `visual-demo.html` provides an interactive visualization that:

1. **Renders the gear train** with proper proportions
2. **Animates rotation** showing the 2:1 speed ratio
3. **Handles user input** for custom peg configurations
4. **Provides visual feedback** for valid/invalid solutions

### Technical Implementation

The visualization uses:
- **SVG** for scalable graphics
- **JavaScript** for animation and interaction
- **CSS** for responsive design
- **Mathematical calculations** mirroring the Java solution

## 🏗️ Design Decisions

### Why Two Implementations?

1. **Solution.java**: Focuses on mathematical elegance and efficiency
2. **AlternateFinding.java**: Provides numerical robustness with continued fractions

### Fraction vs. Decimal

The problem requires returning a fraction because:
- It preserves exact values (e.g., 1/3 vs 0.333...)
- It matches the mechanical nature of gear teeth (integer ratios)
- It avoids floating-point precision issues

### Testing Strategy

The comprehensive test suite (`Tester.java`) includes:
- Known valid configurations
- Known invalid configurations  
- Edge cases (2 pegs, many pegs)
- Fractional results
- Boundary conditions (gear radius = 1)

## 🚀 Performance Optimizations

1. **Early Validation**: Fails fast for invalid inputs
2. **Single Pass Validation**: Checks all constraints in one loop
3. **Integer Arithmetic**: Uses integer math where possible for precision
4. **GCD Optimization**: Euclidean algorithm for fraction simplification

## 💡 Lessons Learned

This challenge beautifully demonstrates:

1. **Mathematical Modeling**: Translating physical constraints into equations
2. **Algorithm Design**: Finding elegant solutions to complex problems
3. **Numerical Precision**: Handling fractions vs. decimals appropriately
4. **Validation Importance**: Ensuring all constraints are satisfied
5. **Visualization Value**: How visual feedback aids understanding

## 🔗 Real-World Applications

This type of problem appears in:
- **Mechanical Engineering**: Actual gear train design
- **Robotics**: Drive train calculations
- **Clock Making**: Gear ratio calculations
- **Manufacturing**: Precision machinery design

## 📚 Further Reading

For those interested in the underlying concepts:
- Gear train mechanics and kinematics
- Systems of linear equations
- Continued fraction algorithms
- Mechanical advantage in simple machines

---

*This project showcases problem-solving skills, mathematical reasoning, and the ability to create comprehensive solutions with proper documentation and visualization - all key skills demonstrated during the Google Foobar challenge.*