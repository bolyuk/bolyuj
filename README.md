boluj is a simple interpreted language whose interpreter currently exists only in Java.

Baggy and not complete.

Use is not recommended.

# Using the library in your projects

The most basic functions for executing code
```java
Interpreter e = new Interpreter(null);
//initialize the standard interpreter

parse(code)
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
we have created a function that, each time it is called, increases the value of b by 1 and adds this value to the incoming value (please don’t look at a poor check or there is a variable b). And it turns out:
```
f:(3)
//returned 5 because b=2

f:(3)
//returned 6 because b=3

f:(6)
//returned 10 for b=4
```
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
# Add operator
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
# Subtraction operator
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
## Function statement
```
statement1:statement2
```
There are several variations for statement2, on which the functionality of the statement depends



the coder is too lazy, didn't finish the article...
