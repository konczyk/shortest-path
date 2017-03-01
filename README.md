# Shortest Path

Compute shortest paths using heap-based Dijkstra's shortest-path algorithm.

## Sample client

Client can be run with either
* Scala, using `scala target/scala-2.12/shortest-path.jar [OPTIONS]`, or
* Java, using `java -cp target/scala-2.12/shortest-path.jar Client [OPTIONS]`

Build a jar file:
```
$ ./sbt assembly
```

Client options:
```
$ java -cp target/scala-2.12/shortest-path.jar Client -h
```

Shortest distances and paths from the source vertex 1 to all the vertices in
the graph from the input stream:
```
$ cat data/graph.txt | java -cp target/scala-2.12/shortest-path.jar Client -s 1

2    (6.00)    3 -> 5 -> 4 -> 2
3    (2.00)    3
4    (4.00)    3 -> 5 -> 4
5    (3.00)    3 -> 5
6    (10.00)   3 -> 5 -> 4 -> 2 -> 6
7    (11.00)   3 -> 5 -> 4 -> 2 -> 6 -> 7
8    (9.00)    3 -> 5 -> 8
9    (12.00)   3 -> 5 -> 4 -> 2 -> 6 -> 7 -> 9
```

Shortest distances and paths from the source vertex 1 to selected vertices in
the graph from the input stream:

```
$ cat data/graph.txt | java -cp target/scala-2.12/shortest-path.jar Client -s 1 -d 2 3 4

2    (6.00)    3 -> 5 -> 4 -> 2
3    (2.00)    3
4    (4.00)    3 -> 5 -> 4
```

Shortest distances and paths from the source vertex 1 to selected vertices in
the large graph (10k vertices and ~123k edges) from the input stream:

```
$ zcat data/large.txt.gz | java -cp target/scala-2.12/shortest-path.jar Client -s 1 -d 1156 3287 6006

1156 (0.07)    9826 -> 7897 -> 3845 -> 7239 -> 1156
3287 (0.08)    6063 -> 9693 -> 1478 -> 5979 -> 3287
6006 (0.18)    7596 -> 1127 -> 4847 -> 9571 -> 5721 -> 8844 -> 8518 -> 3047 -> 8261 -> 2610 -> 6006
```
