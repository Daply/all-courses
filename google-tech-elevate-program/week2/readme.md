
# Week 2

## Auto Complete

Given a dictionary containing N words and a new set of Q query words, your goal is to print the smallest words from the original dictionary for which the query word is a prefix. The Q words were typed really fast and  some typos may have occurred. You should find matches considering at most 1 typo only. A “typo” means an incorrect character was typed instead of the correct one. For example, “the -> tje” or “test -> tast” have 1 typo, but “the -> teh” has 2 typos. Additional and missing characters, such as "the" -> "thje" and "the" -> "te", are not considered typos.<br>
If there are more than 10 matches, print only the smallest 10 matches. Otherwise print all of them.<br>
See the samples for explanation.<br>
 
<b>Input</b><br>
-------<br>
You will be given an integer N (1 <= N <= 105) representing the number of words in the original dictionary, followed by N words. An integer Q (1 <= Q <= 105) representing the number of query words will then be given, followed by Q query words. Each word contains only lowercase latin letters. Words in the dictionary or the queries may appear multiple times.<br>
The length of a single word is at most 105. The sum of all words' lengths in the dictionary is at most 3 * 105 and the sum of all query words is at most 3 * 105.<br>
 
<b>Output</b><br>
---------<br>
For each word in the Q query words, find the smallest words in the original dictionary that have the query word as a prefix. If there are more than 10 matches, print only the smallest 10. Otherwise print all of them. The printed words should be ordered from smallest to largest lexicographically. If the same word appears multiple times in the dictionary, it should be considered only once in the output.<br>
The output per query should be in a single line and words separated by spaces.<br>
If there are no matches at all, print "<no matches>" without the quotations.<br>
See the samples for further elaboration.<br>
 
<b>Examples</b><br>
--------------<br>
<b>Input</b><br>
7<br>
tech<br>
computer<br>
technology<br>
elevate<br>
compute<br>
elevator<br>
company<br>
4<br>
tevh<br>
new<br>
techn<br>
compa<br>
 
<b>Output</b><br>
tech technology<br>
<no matches><br>
technology<br>
company compute computer<br>
 
<b>Notes</b><br>
In the first query, the word was "tevh", which does not match any words. However, considering that 'v' is a typo and that the intended character was possibly 'c', then it matches "tech" and "technology".<br>
The same occurs with the last query "compa". It of course matches "company" with 0 errors. It also matches "compute" and "computer" since the prefixes "compu" and "compa" differ by only one character.<br>

## Google Flights

Given flights prices between different airports, write a function which finds M cheapest connections between a source and a destination airport.<br>
<b>Input</b><br>
-----<br>
The first line contains N, the number of airports pairs with a flight price. 0 < N < 1000000.<br>
Then the subsequent N lines contain three words per line, both ASCII-only and case sensitive. The first word is the source airport, the second word is the destination airport of the flight and the third word is the price of the flight from the source to the destination airport.<br>
The price is always a positive integer number.<br>
 
Please note that flights are one-way, maybe there is no flight in the opposite direction. Flights can be listed in any order, but without duplicates.<br>
 
The next line contains M, the number of connections which should be in the output (unless there are less than M connections, in which case all should be in the output). 0 < M < 1000000.<br>
The subsequent line contains two words, both ASCII-only and case sensitive. The first word is the source airport and the second word is the final destination airport of the trip. In addition to these airports, a trip can contain multiple stopover airports in-between. Trip source and destination can't be the same.<br>
 
<b>Output</b><br>
------<br>
There is one output line for each trip, sorted by price (in ascending order). Each line contains a space-separated list of stopover airports and a total price of a trip.<br>
If there are several trips with the same price, they should be sorted by a number of stops, and if that is the same, then alphabetically by a smaller stop name.<br>
If there are no flights in the solution please return "<no solution>" without the quotations and without a newline at the end.<br>
<b>Example</b><br>
-------<br>
<b>Input</b><br>
13<br>
SFO JFK 500<br>
JFK WAW 800<br>
JFK BSL 700<br>
JFK ZRH 850<br>
ZRH BSL 300<br>
SFO HEL 1000<br>
SFO MUC 1100<br>
SFO LHR 1100<br>
MUC ZRH 500<br>
LHR BSL 1<br>
LHR ZRH 100<br>
BSL ZRH 1<br>
SFO ZRH 5000<br>
10<br>
SFO ZRH<br>
 
<b>Output</b><br>
SFO LHR BSL ZRH 1102<br>
SFO LHR ZRH 1200<br>
SFO JFK BSL ZRH 1201<br>
SFO JFK ZRH 1350<br>
SFO MUC ZRH 1600<br>
SFO ZRH 5000<br>
 
<b>Explanation</b><br>
The cheapest connection between SFO and ZRH is with two stopovers in LHR and BSL:  SFO --> LHR --> BSL --> ZRH.<br>
The total price of this trip is 1102:<br>
  for SFO --> LHR the price is 1100,<br>
  for LHR --> BSL the price is 1,<br>
  for BSL --> ZRH the price is 1.<br>
 
There are five other possibilities to get from SFO to ZRH, with fewer connections, but more expensive.<br>
We were asked to provide 10 connections, but as there are only 6 connections between SFO and ZRH, we output all of them.<br>

## Title Search

You are given a list of N web page titles. Then you are given Q search query lines and your goal is for each search query to print up to 10 page titles where all words in that query are found in the page title (in any order and any number of times).<br>
If there are more than 10 matches, print 10 with the shortest titles (smallest number of total words). Otherwise print all of them. If multiple matching titles have the same number of words, print them in lexicographic order.<br>
See the samples for an explanation.<br>
 
<b>Input</b><br>
-------<br>
You will be given an integer N (1 <= N <= 105) representing the number of titles. The next N lines will contain the titles (not necessarily unique), which consist of 1 or more space separated words. You will then be given  an integer Q (1 <= Q <= 105), representing the number of query lines, followed by Q lines consisting of 1 or more space separated words. Words in the titles or the queries may appear multiple times. If a title appears multiple times in the input, all of them should be considered in the output following the output constraints and format.<br>
All words contain only lowercase latin letters. The length of a single word is at most 105. The sum of all words' lengths in the list of titles is at most 3 * 105 and the sum of all query words is at most 3 * 105.<br>
 
<b>Output</b><br>
---------<br>
For each query, print one number M (M <= 10), the number of found titles. Then on the next M lines print the M titles ordered by ascending total number of words. If there are ties, print the smallest lexicographically title first.<br>
 
<b>Examples</b><br>
--------------<br>
<b>Input</b><br>
8<br>
google code jam is launching<br>
hash code competition results are announced<br>
google launches tech elevate program<br>
code jam final round<br>
youtube newest features<br>
football world cup results<br>
top viewed videos last year<br>
australian open singles results<br>
5<br>
world cup football<br>
results<br>
views<br>
jam code<br>
google program<br>
 
<b>Output</b><br>
1<br>
football world cup results<br>
3<br>
australian open singles results<br>
football world cup results<br>
hash code competition results are announced<br>
0<br>
2<br>
code jam final round<br>
google code jam is launching<br>
1<br>
google launches tech elevate program<br>
 
<b>Notes</b><br>
The 3rd query has the word "views" which doesn't appear in the given titles so the answer is 0<br>
The 4th query has the word "code" which appears in 3 different titles but "jam" appears in only 2 of them. The output is sorted on total number of words in these titles.<br>



