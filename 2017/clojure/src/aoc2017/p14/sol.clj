(ns aoc2017.p14.sol
  (:require [aoc2017.p10.sol :as p10]
            [clojure.string :refer [join]]
            [clojure.pprint :refer [cl-format]]))

(defn- hex-to-binary [hex]
  (->> hex
       (re-seq #"\w\w")
       (map #(Integer/parseInt % 16))
       (map #(cl-format nil "~8,'0b" %))
       (join)))

(def memory
  (->> (range 128)
       (map #(str "uugsqrei" "-" %))
       (map #(p10/knot-hash %))
       (mapv #(vec (hex-to-binary %)))))

; This will hold the global mutable state
(def memory-atom (atom nil))

(defn parse-region [[x y]]
  "Maps the region starting from [x y] by recursively parsing
  adjacent points. Updates global state!"
  (if (= \0 (get-in @memory-atom [x y] \0))
    []
    (do
      (swap! memory-atom assoc-in [x y] \0)
      (vec (concat [[x y]]
                   (parse-region [(inc x) y])
                   (parse-region [(dec x) y])
                   (parse-region [x (inc y)])
                   (parse-region [x (dec y)]))))))

(defn part-one []
  (->> memory
       (apply concat)
       (filter #(= \1 %))
       (count)))

(defn part-two []
  (reset! memory-atom memory)
  (->> (mapcat #(map (fn [y] (vector % y)) (range 128)) (range 128))
       (map parse-region)
       (filter seq)
       (count)))
