# aoc2017

Advent of code 2017 in Clojure. Learning the language as I go.

## Usage

I haven't made it user friendly, and the code is mostly here for sharing ideas about the problems themselves, perhaps giving you a different perspective if you've solved them already etc, etc... BUT, if you want to run them:

You have to change `core.clj` to require the appropriate namespace and call functions `part-one` and `part-two` (no arguments) to get the results. Problems are under the `aoc2017.p*` directories. An example `core.clj` that imports problem 1 namespace and prints the results:
```clojure
(ns aoc2017.core
  (:require [aoc2017.p1.sol :as p1])
  (:gen-class))

(defn -main
  [& args]
  (do
    (println (str "Part one: " (p1/part-one)))
    (println (str "Part two: " (p1/part-two)))))
```

Then you just do `lein run` from any directory in this project and you're golden. That's assuming you have Leiningen installed -- https://leiningen.org/ -- if not, go get it **right now**!
