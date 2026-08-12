# 🔧 Gear Train Problem Solver

A Java solution for the Google Foobar challenge that calculates gear configurations where the first gear rotates at exactly twice the speed of the last gear.

## 🎯 Problem Description

Given an array of peg positions, determine the radius of the first gear such that:
- All gears mesh properly (adjacent gears touch at exactly one point)
- The first gear rotates at exactly 2x the speed of the last gear
- All gear radii must be ≥ 1
- Return the radius as a simplified fraction `[numerator, denominator]`
- Return `[-1, -1]` if no valid solution exists

## 🚀 Quick Start

### Running the Solution

```bash
# Compile the Java files
javac -d out/production/GearTrainProblem src/*.java

# Run the test suite
java -cp out/production/GearTrainProblem Main
```

### Visual Demo

Open `visual-demo.html` in a web browser to see an interactive visualization of the gear train problem with animation.

## 📁 Project Structure

```
GearTrainProblem/
├── src/
│   ├── Solution.java          # Primary implementation
│   ├── AlternateFinding.java  # Alternative approach using continued fractions
│   ├── Main.java              # Entry point
│   └── Tester.java            # Test cases
├── visual-demo.html           # Interactive web visualization
├── README.md                  # This file
└── PROJECT_DOCUMENTATION.md   # Detailed technical documentation
```

## 💡 Example

```java
// Input: peg positions
int[] pegs = {4, 30, 50};

// Output: [12, 1] meaning the first gear has radius 12
// This creates a gear train where:
// - Gear 1: radius = 12, at position 4
// - Gear 2: radius = 14, at position 30  
// - Gear 3: radius = 6, at position 50
// First gear rotates 2x faster than the last gear
```

## 🔬 Algorithm Overview

The solution uses a system of linear equations derived from:
1. **Gear meshing constraint**: Distance between pegs = radius₁ + radius₂
2. **Speed relationship**: For meshing gears, radius₁ × speed₁ = radius₂ × speed₂
3. **Target constraint**: speed₁ = 2 × speedₙ

This creates a solvable system that yields the first gear's radius.

## 🧪 Testing

Run the test suite with:
```bash
java -cp out/production/GearTrainProblem Tester
```

The test suite includes:
- Basic valid configurations
- Invalid configurations (no solution)
- Edge cases with 2 pegs
- Complex cases with fractional results
- Even and odd peg count scenarios

## 🛠️ Development

### Adding New Test Cases

Edit `Tester.java` and add test cases to the `test()` method:

```java
testCase(new int[]{/* peg positions */}, new int[]{/* expected numerator, denominator */});
```

### Alternative Implementations

The project includes two solution approaches:
1. **Solution.java**: Direct mathematical solution with simple fraction reduction
2. **AlternateFinding.java**: Uses continued fraction algorithm for precise decimal-to-fraction conversion

## 📊 Complexity

- **Time Complexity**: O(n) where n is the number of pegs
- **Space Complexity**: O(n) for storing gear radii during validation

## 🏆 About the Challenge

This problem was part of Google's Foobar coding challenge, a secret recruiting tool that presents increasingly difficult programming challenges. The gear train problem tests mathematical reasoning, algorithm design, and the ability to work with constraints and edge cases.

## 📄 License

The owner-authored implementation, visualisation code and documentation are
licensed under GNU GPL version 3 only (`GPL-3.0-only`). See `LICENSE` and
`LICENSING.md`.

Google Foobar challenge prompts, expressions, and Google-supplied test vectors
are excluded from this grant and retain their original rights. This independent
project is not affiliated with or endorsed by Google.
