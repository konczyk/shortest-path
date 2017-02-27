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

2    (6)    3 -> 5 -> 4 -> 2
3    (2)    3
4    (4)    3 -> 5 -> 4
5    (3)    3 -> 5
6    (10)   3 -> 5 -> 4 -> 2 -> 6
7    (11)   3 -> 5 -> 4 -> 2 -> 6 -> 7
8    (9)    3 -> 5 -> 8
9    (12)   3 -> 5 -> 4 -> 2 -> 6 -> 7 -> 9
```

Shortest distances and paths from the source vertex 1 to selected vertices in
the graph from the input stream:

```
$ cat data/graph.txt | java -cp target/scala-2.12/shortest-path.jar Client -s 1 -d 2 3 4

2    (6)    3 -> 5 -> 4 -> 2
3    (2)    3
4    (4)    3 -> 5 -> 4
```
