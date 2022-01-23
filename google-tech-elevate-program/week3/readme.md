
# Week 3

## Second largest element

Function secondLargest is supposed to, given an array of integers, return the second largest value, ignoring duplicates.<br>
Fix the below code to return the correct answer. Focus both on code readability and correctness.<br>
Feel free to create your own functions, in addition to secondLargest, if that helps you to achieve better code quality.<br>
<b>Input</b><br>
---------<br>
The first line represents the number of elements in the array. The second to last line represents the array contents in order.<br>
3<br>
1<br>
2<br>
3<br>
 
<b>Output</b><br>
---------<br>
2<br>
 
<b>Description</b><br>
---------<br>
0 The input array must contain at least 2 elements<br>
1 The output must be the second largest element in the array<br>
2 If there is no second largest element, the output should be -1<br>
 
<b>Examples</b><br>
---------<br>
Input  : 12, 35, 1, 10, 34, 1 <br>
Output : 34<br>
Input  : 10, 5, 10 <br>
Output : 5<br>
Input  : 10, 10, 10 <br>
Output : -1<br>

## Evaluate Math Expression

Function evaluateMathExpression is supposed to evaluates a math expression and returns its value.<br>
Expression is a string formatted as: "[someNumber] [operator] [someNumber]"<br>
Fix the below code to return correct answer. Focus on code readability and correctness.<br>

<b>Input</b><br>
---------<br>
4 + 2<br>
 
<b>Output</b><br>
---------<br>
6.0<br>

<b>Description</b><br>
---------<br>
1. There must be spaces around the operator.
2. Input numbers must be integers greater or equal to 0.
3. The operator must be either: '+' (addition), '-' (subtraction), '*' (multiplication), or '/' (division).
4. The result can be a non integer
5. For illegal operations throw NumberFormatException with the message "Can't convert character to operator: +, -, /, *"
6. For illegal number throw NumberFormatException with the message "Can't convert character to digit: {digit}"
7. For wrong input format throw IllegalArgumentException with the message "Must have 3 tokens separated by spaces"
8. For division by zero throw IllegalArgumentException with the message "Can't divide by {num2}"

<b>Examples</b><br>
---------<br>
Input : 4 + 2<br>
Output : 6.0<br>
Input : 2 / 4 <br>
Output : 0.5<br>
Input : 2+2 <br>
Output : "IllegalArgumentException Must have 3 tokens separated by spaces"<br>

## Check Array Order

Function checkArrayOrder is supposed to check if the array is sorted.<br>
Function returns:<br>
1 - if array is in ascending order<br>
-1 - if array is in descending order<br>
0 - if array is not sorted<br>
 
Fix the below code to return the correct answer. Focus on the performance of your solution.<br>
 
<b>Input</b><br>
---------<br>
First line indicates the number of elements of the array. <br>
3<br>
1<br>
2<br>
3<br>
 
<b>Output</b><br>
---------<br>
1<br>
 
<b>Description</b><br>
---------<br>
1. Array has to have at least 2 elements
2. All elements are integers
3. Return 1 if input has all equal elements
 

<b>Examples</b><br>
---------<br>
Input : 1 2 3 4 5<br>
Output : 1<br>
Input : 5 4 3 2 1<br>
Output : -1<br>
Input : 8 7 10 20 1 <br>
Output : 0<br>
Input : 1 1 1 1<br>
Output : 1<br>



