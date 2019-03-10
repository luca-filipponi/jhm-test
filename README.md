# Microbenchmarking

Simple project where i like to add tests everytime that i have a doubt,
about the performance of a certain piece of code. Useful to learn a bit
more on th jvm and how to do microbenchmarking. It uses the sbt-jmh plugin.

## Run

Lot's of code will be generated but the jhm framework so there is need
to compile everything before. Without the sbt plugin there is usually
need to create a jar and the run it.

`sbt clean compile`

Then a benchmark can ber run with:

`sbt "jmh:run -i 20 -wi 10 -f1 -t1"`

-i are the number of iteration (a number > 10 is recommended)
-wi are the number of warmup iteration (a number > 10 is recommended)
-f 1 says to fork once on each benchmark 
-t1 says to run on one thread

This will run all the benchmark in the project (and is really slow),
a single test can be run in this way:

```sbt "jmh:run -i 1 -wi 1 -f1 -t1 .*ExceptionOrNotException.*"```

## Run with Profiler and FlameGraph

### Flight Recorder

It is possible to record the test using

jmh:run -t1 -f 1 -wi 1 -i 1 .*TestBenchmark.* -prof jmh.extras.JFR

The location of the jfr file will be in the stdout. At this point the
file can be loaded using jmc (is a executable inside java bin folder)
And the session can be monitored.

### Flame Graph

For doing the flamegraph there is need of two projects:

[flamegraph][https://github.com/brendangregg/FlameGraph.git]

[jfr-flamegraph][https://github.com/chrishantha/jfr-flame-graph.git]

sbt "jmh:run -t1 -f 1 -wi 1 -i 1 .*ExceptionOrNotException.* -prof jmh.extras.JFR:dir=/Users/luca/project/jhm-test/reports;flameGraphDir=/Users/luca/project/FlameGraph;jfrFlameGraphDir=/Users/luca/project/jfr-flame-graph;flameGraphOpts=--minwidth,2;verbose=true"

Then need to create the flamegraph from the jfr recording:

./build/install/jfr-flame-graph/bin/create_flamegraph.sh -f ~/project/jhm-test/reports/profile.jfr -i > ~/project/jhm-test/reports/flamegraph.svg

## Resources:

* [sbt-jhm][https://github.com/ktoso/sbt-jmh]
* [jhm scala example][https://github.com/ktoso/sbt-jmh/tree/master/plugin/src/sbt-test/sbt-jmh/run/src/main/scala/org/openjdk/jmh/samples]
* [nanotrusting-nanotime][https://shipilev.net/blog/2014/nanotrusting-nanotime/]
