# Technical Task (Software Engineer)

Take as long as you need - as a guideline we would expect it to take up to 60 minutes.

We're happy to review submissions in any language you're comfortable in (we use TypeScript, Kotlin, Dart and Python at Glean).

Please push your solution to a branch and open a Pull Request once you are finished. Get in touch to let us know you are done and we'll aim to review your code within 2 working days.

## Introduction

[Glean](https://glean.co/) lets you record audio and take notes in a **Lecture**. A simplified version of the Lecture
record is as follows:

<pre>
+-------------------------------+
|            Lecture            |
+-------------------------------+
| name  | Text                  | 
| notes | A collection of Notes | 
+-------------------------------+
</pre>

A Note contains some `text`, and is associated with a `timestamp` - an offset in milliseconds from the start of
the recording:

<pre>
+-----------------------------+
|            Note             |
+-----------------------------+
| id        | Unique ID       |
| timestamp | Millis offset   | 
| text      | Text            | 
+-----------------------------+
</pre>

The Lecture is created in a frontend client, and can be saved to the backend and synced to other clients. This
brings the possibility of two different clients (e.g. the web application and a mobile app) making divergent
changes to the same Lecture and having to resolve the differences.

## Task

Implement code to handle two input Lectures, one remote and one local, outputting a combined Lecture. For example,
a function with signature:

```
resolveLectures(remote: Lecture, local: Lecture): Lecture
```

Where:

- **remote**: the Lecture that was last uploaded to the backend.
- **local**: the copy of the Lecture with local changes.
- **return:** resolved version of the Lecture, combining the information from both inputs.

Requirements / assumptions:

- Notes that have the same ID in both Lectures are considered to be the same Note, and may need conflict resolution. Otherwise retain 
  Notes from both Lectures.
- Conflicting text data in both the Lecture and Note should be combined with a slash (see the example).
- Conflicting timestamps in a Note should be resolved by using the timestamp from the remote version.
- All input data is guaranteed not to be null.
- It's not necessary to include any UI or application code - just the resolution logic.
- Include unit tests that cover the logic of your solution.

## Example

The examples below are given in JSON-like syntax, but it's not necessary to implement JSON serialisation/deserialisation.

### Local Lecture

```
{
  name: 'Name 1',
  notes: [
    { id: 1, timestamp: 3200, text: 'A' },
    { id: 2, timestamp: 5600, text: 'C' }
  ]
}
```

### Remote Lecture

```
{
  name: 'Name 2',
  notes: [
   { id: 1, timestamp: 2400, text: 'B' }
  ]
}
```

### Resolved Lecture

```
{
  name: 'Name 1 / Name 2',
  notes: [
   { id: 1, timestamp: 2400, text: 'A / B' },
   { id: 2, timestamp: 5600, text: 'C' }
  ]
}
```


## Notes:

Due to the 60 min guideline. I forgoed writing all the pyramid tests. 
I thought I'd try Kotlin for this. Also I do quite a lot of Python but thought it would be a better idea to try Kotlin.
I am not used to server-side Kotlin so used SpringBoot for easy configuration and gradle set-up etc.
I know this wasn't required for running the tests.
Also again as it's new I used kotlin mockito for the service class test as I'm used to mockito and didn't want to spend
a lot of time learning MockK for this due to the time suggestion.
I didn't spend too long on the code. Over an hour but less than 90 mins. However once I'd got the tests 
working I refactored a little so as to keep to Single Responsibility by using separate delegates form the service.
This lead to a little more time as I had to break the classes down and also the tests.
I'm not sure if this is overkill for this exercise, but I do try to revisit, when possible, to try to keep to 
SOLID and KISS principals.
