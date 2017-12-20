(ns aoc2017.p20.sol
  (:require [clojure.string :refer [split-lines]]))

(def data (->> (slurp "src/aoc2017/p20/input.txt")
               (re-seq #"[-\d]+")
               (map #(Integer/parseInt %))
               (partition 3)
               (map vec)
               (partition 3)
               (map vec)
               (vec)))

(defn- update-velocity
  [[_ [vx vy vz] [ax ay az] :as point]]
  (assoc point 1 [(+ vx ax) (+ vy ay) (+ vz az)]))

(defn- update-position 
  [[[px py pz] [vx vy vz] _ :as point]]
  (assoc point 0 [(+ px vx) (+ py vy) (+ pz vz)]))

(defn- update-point [p] (update-position (update-velocity p)))

(defn- remove-collisions [points]
  (let [colliding (->> (map first points)
                       (frequencies)
                       (filter (fn [[k v]] (> v 1)))
                       (map first))]
    (filter (fn [[p _ _]] (not (some #(= p %) colliding))) points)))

(defn part-one []
  (let [accs (map (fn [[_ _ [ax ay az]]] 
                    (+ (Math/abs ax) (Math/abs ay) (Math/abs az))) data)]
    (.indexOf accs (apply min accs))))

(defn part-two []
  (loop [i 0 points data]
    (if (= i 1000) ; obviously not a proper solution ;)
      (count points)
      (recur (inc i) (map update-point (remove-collisions points))))))
