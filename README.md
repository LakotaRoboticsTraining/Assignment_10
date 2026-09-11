# Lesson 10: Polymorphism

Goal: Use a parent type variable to hold subclass objects, and see the correct overridden method run.

Time: About 30–40 minutes

You will learn:

- What polymorphism means in plain language
- Assigning a subclass object to a superclass variable
- Why motor.report() can print different text depending on the actual object
- A simple array (or several variables) of the parent type
- How this shows up in WPILib (Command, Subsystem)
Before this lesson: Lesson 9 (inheritance, @Override).

### Why this matters for robots

The command scheduler does not care whether you have a ShootCommand or an AlignCommand. Both are Commands. It can initialize / execute / end whatever command you gave it. That is polymorphism: one interface, many behaviors.

### The big idea

Polymorphism = “same message, different action depending on the real object.”

You call report() on a Motor variable. If the object is actually a FalconMotor, the Falcon report() runs.

### Parent type, child object

```java
Motor left = new FalconMotor("left", 0.4, 1);
```



```java
left.report();
```



- The variable type is Motor (what you can call: anything Motor has)
- The object type is FalconMotor (what it really is)
```java
report() uses the object’s version if it was @Overriden
```

```java
Motor spare = new Motor("spare", 0.0);
spare.report();    // Motor's report
```



```java
Motor left = new FalconMotor("left", 0.4, 1);
left.report();     // FalconMotor's report
```



Same call shape: something.report(). Different output.

### A list of motors

You do not need a new Java list type yet. Several parent-type variables (or an array) are enough:

```java
Motor[] motors = new Motor[2];
motors[0] = new Motor("spare", 0.0);
motors[1] = new FalconMotor("left", 0.4, 1);
```



```java
for (int i = 0; i < motors.length; i++) {
    motors[i].report();
}
```



The loop only knows each item is a Motor.  Each object still behaves as its real class.

(motors.length is the array size. Indexing starts at 0.)

### What you cannot do (yet)

If canId exists only on FalconMotor, this will not compile:

```java
Motor left = new FalconMotor("left", 0.4, 1);
// left.canId;   // not allowed — Motor doesn't declare canId
```



The variable type Motor only sees Motor members. (Casting to FalconMotor exists later; skip it for now.)

### Robot connection

```java
Command auton = new ShootCommand();   // variable type Command, object ShootCommand
```



The scheduler calls auton.initialize()  ShootCommand’s version runs.

### Common mistakes

1. Thinking the variable type decides the method body — the object does, if the method is overridden

```java
2. Forgetting @Override so you accidentally added a *new* method instead of replacing the parent one
```

3. Trying to use subclass-only fields through a parent-type variable

4. Confusing overload (Lesson 6) with override (Lesson 9–10)

- Overload: same name, different parameters, same class
- Override: same name + same parameters, subclass replaces parent

## Try it yourself

Build on Lesson 9. Finish `VideoGame`, `Arcade.playGame` / `downloadGame`, and `Main`.

### Challenge 1 â€” Parent variable / polymorphic play

`VideoGame` must `@Override play()` so the text includes `Playing the video game` and the name.

Implement `Arcade.playGame(String name)`:

- If the game is in the library, call `play()` (correct subclass version)
- If missing, print that the arcade doesn't have that name (include the name)

### Challenge 2 â€” Both types / download

`VideoGame` implements `Downloadable` with `download()` text including `Download the video game` and the name.

Implement `downloadGame(String name)`:

- If the game is a `VideoGame`, call `download()` and return `true`
- Otherwise return `false`

### Challenge 3 â€” Loop it in main

In `Main`, add Pokemon + Spaceball, then:

- `playGame("Pac-Man")` (missing)
- `playGame("Pokemon")` and `playGame("Spaceball")`
- `downloadGame` for both and print whether each download succeeded

### Check your understanding

```java
1. In Motor m = new FalconMotor(...), what is the variable type? The object type?
```

2. Which report() runs if FalconMotor overrides it?

3. Can you read canId from m if m is declared as Motor?

4. How is override different from overload?

Answers

1. Variable type Motor; object type FalconMotor.

2. FalconMotor's report().

3. No, not through m. Motor does not have canId.

4. Overload = same name, different parameters. Override = subclass replaces parent method with the same signature.

### Looking ahead

In Lesson 11, you will organize classes into packages (frc.robot.subsystems, frc.robot.commands) and use import—the same structure as the team robot project.

Lesson complete. When you can store a subclass in a parent-type variable and see the overridden method run, you are ready for Lesson 11.
