
# Week 1

## Merge N sorted arrays

Merge N sorted arrays into 1 sorted array. Read the input and print the result using the provided starter code.<br>
 
<b>Input</b><br>
-----<br>
The first line of the input contains one integer N, the number of sorted arrays. The next line contains N integers, the lengths of the N arrays. The following N lines contain the sorted elements of the arrays, separated by whitespaces. All elements are integers.<br>
 
<b>Output</b><br>
------<br>
A sorted array containing the elements of all N arrays. The result should be output in one line, with elements separated by whitespaces.<br>
 
<b>Constraints</b>:<br>
------<br>
1 <= N <= 23’000<br>
For all 1 <= p <= N and all  0 <= k < lenp, where lenp is the length of the array Ap, it holds that: <br>
- 0 < len1, …, lenn < 1'000
- the elements Ap[k] are of type int and 0 <= Ap[k] <= 1’000’000
- Ap[i] <= Ap[j] for 0 <= i < j < lenp
 
<b>Example</b><br>
-------<br>
<b>Input</b>:<br>
3<br>
2 3 4<br>
1 3<br>
2 4 5<br>
2 3 3 4<br>
 
<b>Output</b>:<br>
1 2 2 3 3 3 4 4 5 <br>
 
For the example above:<br>
A_1 = [1, 3], A_2 = [2, 4, 5], A_3 = [2, 3, 3, 4]; <br>
The result is [1, 2, 2, 3, 3, 3, 4, 4, 5].<br>


<b>Test 1</b><br>
1<br>
2	3<br>
3	2 4 5<br>

<b>Output</b><br>
2 4 5<br>

## Evaluate mathematical expression

Check whether a string represents a mathematical expression and evaluate it.<br>
In addition to the basic solution introduced during lessons, which supported only single-digit, positive numbers, operations +, -, *, / and parentheses, the solution should also support:<br>
- Floating point numbers: anything which consists of at least 1 digit and at most 1 dot (‘.’). (Negative numbers can be achieved by unary -.)
- Unary - even if not followed by a number.
- Power operation (‘^’). Note that we consider the power operation to be of a higher priority than a unary minus.
- Unary functions abs (absolute value) and sqrt (square root).
- Printing string  "Invalid mathematical expression.", in the following situations:<br>
  if the input is not a valid mathematical expression, e.g. 9*+),<br>
  or if the solution is not a real number, e.g. sqrt(-1),<br>
  or if the left operand (base) of the power operation is negative,<br>
  or if the solution does not exist, e.g. 1/0.<br>
- Arbitrary number of spaces, e.g. " 2 +   (3-2)" is a valid input.
 
<b>Input</b><br>
-----<br>
The first, and only line of input contains a string representing a mathematical expression.<br>
 
<b>Output</b><br>
-----<br>
A single line of output, containing the result of an evaluated expression.<br>
Please always return the output rounded to two decimal places. Use the built-in abs, sqrt and pow functions (and built-in operators) on floating-point values, no need to specially handle overflow, underflow or rounding errors.<br>
<b>Example</b><br>
-----<br>
<b>Input</b>:<br>
(3 +2) * 4<br>
 
<b>Output</b>:<br>
20.00<br>


<b>Test 1</b><br>
abs(2.2-(3\*2))<br>
<b>Output</b><br>
3.80<br>
<b>Test 2</b><br>
-3^4<br>
<b>Output</b><br>
-81.00<br>
<b>Test 3</b><br>
1+2\*3^4<br>
<b>Output</b><br>
163.00<br>
<b>Test 4</b><br>
(-3)^4<br>
<b>Output</b><br>
Invalid mathematical expression.<br>
<b>Test 5</b><br>
12345677\*-(-(-(((98765431.)))))<br>
<b>Output</b><br>
-1219326109891787.00<br>
<b>Test 6</b><br>
(abs( 12 ^ 3- abs(-12.55) \* abs(4*(12  -sqrt(-3.6/-.9)))))^3 +- -(-1842767890.)<br>
<b>Output</b><br>
3286.00<br>



