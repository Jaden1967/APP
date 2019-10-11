# Coding Standards:

+ **[File Organization](#file_organization)**

+ **[Indentation](#indentation)**

+ **[Comments](#comments)**

+ **[Declarations](#declarations)**

+ **[Naming](#naming)**



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
## <span id = "declarations">Declarations</span>
what particular syntax to use to declare variables, data structures, classes, etc. in order to maximize code readability. 
