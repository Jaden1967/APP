# Coding Standards:

+ **[File Organization](#file_organization)**

+ **[Indentation](#indentation)**

+ **[Java Doc](#Java_Doc)**

+ **[Declarations](#declarations)**

+ **[Naming](#naming)**



## <span id = "file_organization">File Organization</span>

how code is distributed between files, and organized within each file. 


## <span id = "indentation">Indentation</span>

#### Use line breaks wisely

There are generally two reasons to insert a line break:

1. Your statement exceeds the column limit.
2. You want to logically separate a thought.
   Writing code is like telling a story. Written language constructs like chapters, paragraphs, and punctuation (e.g. semicolons, commas, periods, hyphens) convey thought hierarchy and separation. We have similar constructs in programming languages; you should use them to your advantage to effectively tell the story to those reading the code.

#### Coding layout

Nesting level: Code must be indented according to its nesting level.
Use four spaces and set the tab key to four spaces Ending syntax : either monopolize a line or end with a semicolon


Valide code

```java
var team = "omwteam";

function sayTeam () {

    return {
        name: "freddy",
        data: []
    }
}

```

Invalid code

```java
var team = "omwteam"

function sayTeam () {

    return 
    {
        name: "freddy",
        data: []
    }
}
```

##### Length of the line &  Appropriate place to wrap

* length of the line

    The length of a single line should not exceed 80 lines, and more than 80 lines should be forced to wrap.

* Appropriate place to wrap

    When there are more than 80 lines in a row, you need to manually wrap the line, and the line feed part should use two indents;

Valide code
```java
callback(this,document,'test',[],'xxx','dsds',windows,
        'ddsds');
```
Invalid code
```java
callback(this,document,'test',[],'xxx','dsds',windows,
    'ddsds');
```

Invalid code
```java
callback(this,document,'test',[],'xxx','dsds',windows
    ,'ddsds');     
```

   * Add blank lines where appropriate to increase code readability

   * Between methods.

   * Local variables and the first statement in the method

   * Before multi-line or single-line comments

   * Insert blank lines between logical segments within the method to improve readability

// Valid code
```java
var list = [];

if (list && list.length) {

    for (i = 0, l = list.length; i < l; i++) {
        item = list[i];
        type = object[item]

        if (Object.hasOwnProperty(item)) {

            if (type && type === 'object') {
                return true;
            } else {
                return false;
            }
        }
    }
}
```

// Invalid code
```java
var list = [];

if (list && list.length) {
    for (i = 0, l = list.length; i < l; i++) {
        item = list[i];
        type = object[item]
        if ( Object.hasOwnProperty(item) ) {
            if (type && type === 'object') {
                return true;
            } else {
                return false;
            }
        }
    }
}
```
## <span id = "Java_Doc">Java Doc</span>

Documentation for a class may range from a single sentence to paragraphs with code examples. Documentation should serve to disambiguate any conceptual blanks in the API, and make it easier to quickly and *correctly* use your API. A thorough class doc usually has a one sentence summary and, if necessary, a more detailed explanation.

```java
/**
 * A volatile storage for objects based on a key, which may be invalidated and discarded.
 */
class Cache {
  ...
}

/**
 * Splits a string on whitespace.
 *
 * @param s The string to split.  An {@code null} string is treated as an empty string.
 * @return A list of the whitespace-delimited parts of the input.
 */
List<String> split(String s);
```

**@param <name> <description>**

Used for methods and constructors

Describes the usage of a passed parameter

Declare what happens with extreme values (null etc.)

Use one tag per parameter

 **@return <description>**

Used for methods

Describes the return value, if any, of a method

Indicate the potential use(s) of the return value

**@throws <name> <description>**

Used for methods and constructors

Describes the exceptions that may be thrown

Use one tag per exception 

**{@inheritDoc}**

Used for methods 

Copies documentation from super class or interface 

**@author <name>**

Used for interfaces and classes

Indicates the author(s) of the code

Use one tag per author

**@version <text>**

Used for interfaces and classes

Indicates the version information for a given piece of code

Avoid when using modern code versioning/revision systems

**{@link <ClassName#MethodName>}**

Used for any javadoc comment

Generates a hypertext link in the documentation to the specified class or method

**{@code <text>}**

Used for any javadoc comment 

Text is displayed verbatim in a fixed-width font 

Indicates that the text refers to source code 

## <span id = "declarations">Declarations</span>
what particular syntax to use to declare variables, data structures, classes, etc. in order to maximize code readability. 


## <span id = "naming">Naming</span>
## Class Names

Class names should be nouns, as they represent “things” or “objects.”
And the classes should start with the project name. For example, if you are working on a project called `MagicMysteryTour`, you could abbreviate it to `MMT` and use this string as a prefix to all type and class names: `MMTInteger` , `MMTUserInterface`, and so on.

```java
public class MMTFish {...}
```

## Interface Names

Interface names should be adjectives. They should end with “able” or “ible” whenever the interface provides a capability; otherwise, they should be nouns. Interface names follow the same capitalization convention as class names:

```java
public interface MMTSerializable {...}
public interface MMTSystemPanel {...}
```

## Method Names

Method names should contain a verb, as they are used to make an object take action. They should be mixed case, beginning with a lowercase letter, and the first letter of each subsequent word should be capitalized. Adjectives and nouns may be included in method names:

```java
public void locate() {...} // verb
public String getWayPoint() {...} // verb and noun
```

## Attribute names

Attribute names start with a lower case letter and use underscores to separate words.

```java
private String start_time;
```

## Constants

Constants use upper case letters with underscores between words.
like: `MAXIMUM_TEMPERATURE`

## Global names
Global names are prefixed with the project name,like `MAXIMUM_TEMPERATURE`.

## Variable names
Temporary variable names may be single letters such as `i`, `j`, `k`, `m`, and `n` for integers and `c`, `d`, and `e` for characters.
Variable names could be noun phrase with underline connection or hump.

## Generic Type Parameter Names

Generic type parameter names should be uppercase single letters. The letter `T`for type is typically recommended.

The Collections Framework makes extensive use of generics. `E` is used for collection elements, `S` is used for service loaders, and `K` and `V` are used for map keys and values:

```java
public interface Map <K,V> {
   V put(K key, V value);
}
```

## Enumeration Names

Enumeration names should follow the conventions of class names. The enumeration set of objects (choices) should be all uppercase letters:

```java
enum Battery {CRITICAL, LOW, CHARGED, FULL}
```

## Package Names

Package names should be unique and consist of lowercase letters. Underscores may be used if necessary:

```java
package com.oreilly.fish_finder;
```

Publicly available packages should be the reversed Internet domain name of the organization, beginning with a single-word top-level domain name (e.g., *com, net, org*, or *edu*), followed by the name of the organization and the project or division. (Internal packages are typically named according to the project.)

Package names that begin with `java` and `javax` are restricted and can be used only to provide conforming implementations to the Java class libraries.

## Annotation Names

Annotation names have been presented several ways in the Java SE API for predefined annotation types; [adjective|verb][noun]:

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FunctionalInterface {}
```
## File Names

They should be mixed case (camel case) with only the first letter of each word capitalized, like `MailBox.java `.
