boluj is a simple interpreted language whose interpreter currently exists only in Java.

Baggy and not complete.

Use is not recommended.

# Using the library in your projects

The most basic functions for executing code
```java
Interpreter e = new Interpreter(null);
//initialize the standard interpreter

e.parse(code)
//parse the code

BJobject _response = e.exec();
//execute

if(_response.value != "")
   String result = _response.value;
// get the result

e.clearParsedCode();
//clear
```
# Code examples

while
```
while:([condition, body]={
?([#($condition)]>[
(#($body)),
( while:(@($condition),@($body)))
]:[0])
});
```
hashmap
```
array~;

array:{
[buf~],
[g:([i]=[$($i)])],
[s:([i,v]=[(buf=($i)),`($buf~),($i=($v)])]
};
```

# Primitives

number - integer numbers

string - standard strings

logic - a boolean value

class - the class has an internal context

exception - an exception, not an operatable data type

statement - conditional method, block of code

function - a function, has an internal context

# Syntax

Predefined characters are used to separate blocks of code:
```
,
;
)
]
}
(
[
{
    //space and/or newline
```
You can use any of these symbols for your convenience. Parentheses are also used to change the order of execution:
```
a = (3-6+7)
// returned: 4

a = (3-[6+7])
// returned: -10
```
Unfortunately, due to some peculiarities, you will need to use a lot of parentheses. so all brackets have the same function:
```
a = {3+(5+6]-6)
// not recommended, this is just an example
```
Also, due to some features, some code execution may seem strange to you, for example:
```
a = 5+6
// a = 5, but 11 will be returned
```
this is due to the fact that all operators see ONLY the nearest statement and it turns out that the "=" operator sees only "a" and only "5", so you need to ALWAYS use parentheses:
```
a = (5+6)
// a = 11, returned 11
```
you can also use statement instead of variable names:
```
(a+5)=value
// a5=value, returned `value`
```
oh, and do not forget that by the name of the variable, the value cannot be taken from it:
```
a=5
b = a+1
//b=a1, returned `a1`
```
it's also pretty important that ALL operators return a value:
```
a = (b+1) = 7
// a =b1, b1=7, returned 7
```
# Context

(!) Please read this section after reading the "Operators" section

Context or class - the space in which the code is executed. There is a current context and an upper one (the upper context can also have an upper one):
```
a=5;
b=3;
c:(a~, a=6)
```
and then:
```
$a
// returned 5

c:($a)
// returned 6
```
if the variable is not found in the current context, it will be searched in the upper context:
```
c:($b)
// returned 3
```
But there is a context problem - if you initialize a variable, then it is created ONLY in the current context. But this problem can be worked around:
```
buf~
s:([i,v]=[(buf=($i)), `($buf~), ($i=($v))])
```
The ` operator allows code to be executed ONLY in the upper context. Because of this, we needed to make a buffer variable in the upper context, write to it the value of the variable name that is needed from the lower context, and initialize the value from the variable (name), and then only assign the value. After that we can call our function:
```
s:[b,5]
// b=5, 5 returned
```
One way or another, in classes and functions, the context is the same. In functions, ALL variables are static, that is:
```
f:([i]={
[?([$b & ""]>[(b~), b=1]):(b=($b)+1)],
[$i+($b)]
})
```
we have created a function that, each time it is called, increases the value of b by 1 and adds this value to the incoming value (please don???t look at a poor check or there is a variable b). And it turns out:
```
f:(3)
//returned 5 because b=2

f:(3)
//returned 6 because b=3

f:(6)
//returned 10 for b=4
```
Also, in any context, you can create your own functions
# Operators

If you don't understand some of the code examples, that's fine.
## Initialization statement
```
(statement1)~(optional statement2)
```
Code examples:
```
a~
s~(javaPath)
```
(!) Initializes a variable or class and returns it.
## Assignment operator
```
statement1 = statement2
```
Code examples:
```
b = 3

a = (3+5-($b))

f+($value)=(67*[s:($f)]-3
```
(!) Assigns a value to a variable and returns it
## Take operator
```
$statement1
```
Code example:
```
$s
$(10-[$f]+b)
```
(!) Returns the value of a variable
## Add operator
```
statement1 + statement2
```
Supported types:
```
string + string
number + number
number + string
string + number
```
## Subtraction operator
```
statement1 - statement2
```
Supported types:
```
number - number

string - number
//truncate string by number characters
```
## Text file read statement
```
^statement1
```
(!)Returns the read data from the file along the path statement1
## Creating a function

(!) This will only work if statement1 is not initialized as a class
```
statement1:(statement2=statement3)
```
statement 2 - set of variables passed to the function

statement3 - function body
```
s:([i,v,f]=[($f=($v+1)),($i=($i+1))])

f:([i]=[$i+1])
```
## Function execution

(!) This will only work if statement1 is not initialized as a class
```
statement1:(statement2)
```
statement2 - set of variables
```
s:(a,3)
f:()
```
## Class context

(!) This will only work if statement1 is initialized as a class
```
statement1:(statement2)
```
statement2 - block of code to be executed inside the class
```
a:(b=6)
b:(f:[], c = [$i+1])
```
## Parsing java functions
```
statement1:(statement2>statement3)
```
statement1 - function name, must match the name of the required java function

statement2 - set of passed variables, should match the number of variables in the required java function

statement3 - path to java class
```
read:([s]>["org.boluj.DFile"])
```
## Saving the code
```
@statement1
```
Allows you to pass statement1 without executing
```
r:[@($i+1)]
```
## Code execution
```
#statement1
```
Allows to execute statement1 if it is a string or a saved statement
```
r:[(i)=(?[($i < 10)>[#($i)]:[0]])]
```
## Upper context
```
`statement1
```
executes statement1 in the top context
## Fork
```
?([statement1]>[statement2])

?([statement1]>[statement2]:[statement3])
```
statement1 - condition

statement2 - block of code, executed if the condition is true

statement3 - block of code, executed if the condition is not true
## Multiplication operator
```
statement1*statement2
```
Supported types:
```
string*number
number * number
```
## Division operator
```
statement1/statement2
```
Supported types:
```
number / number
```
## Comparison operator
```
statement1 & statement2
```
if statement1 or statement2 is logic, then it works like a logical AND otherwise it compares the values of both for equality.
## logical OR
```
statement1 | statement2
```
## Logical NOT
```
!statement1
```
## greater than operator
```
statement1 > statement2
```
## Less than operator
```
statement1 < statement2
```
