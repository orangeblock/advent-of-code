(ns aoc2017.p18.sol
  (:require [clojure.string :refer [split-lines]]
            [clojure.core.async :as async]))

(def data (vec (map #(re-seq #"[-\w]+" %) 
                    (split-lines (slurp "src/aoc2017/p18/input.txt")))))

(def proc-sends (atom {0 0 1 0}))
(def proc-chans {0 (async/chan) 1 (async/chan)})

(defn get-val [reg-atom reg-or-val]
  (if (re-seq #"\d" reg-or-val)
    (Integer/parseInt reg-or-val)
    (@reg-atom reg-or-val 0)))

(defn- exec-update [reg-atom [cmd op1 op2]]
  (let [get-v (partial get-val reg-atom)]
    (case cmd
      "set" (swap! reg-atom assoc op1 (get-v op2))
      "add" (swap! reg-atom assoc op1 (+ (get-v op1) (get-v op2)))
      "mul" (swap! reg-atom assoc op1 (* (get-v op1) (get-v op2)))
      "mod" (swap! reg-atom assoc op1 (mod (get-v op1) (get-v op2))))))

(defn- run [pid out-pid]
  (let [regs  (atom {"p" pid})
        get-v (partial get-val regs)]
    (loop [ip 0]
      (let [[cmd op1 op2] (nth data ip nil)]
        (case cmd
          nil   nil
          "snd" (do 
                  (swap! proc-sends assoc pid (inc (@proc-sends pid)))
                  (async/put! (proc-chans out-pid) (str (get-v op1)))
                  (recur (inc ip)))
          "rcv" (let [v (async/<!! (proc-chans pid))]
                  (swap! regs assoc op1 (get-v v))
                  (recur (inc ip)))
          "jgz" (if (pos? (get-v op1))
                  (recur (+ ip (get-v op2)))
                  (recur (inc ip)))
          (do (exec-update regs [cmd op1 op2]) (recur (inc ip))))))))

(defn part-one []
  (let [regs  (atom {})
        get-v (partial get-val regs)]
    (loop [ip 0]
      (let [[cmd op1 op2] (nth data ip nil)]
        (case cmd
          nil   (@regs "snd")
          "snd" (do (swap! regs assoc "snd" (get-v op1))
                    (recur (inc ip)))
          "rcv" (if (pos? (get-v op1))
                  (@regs "snd")
                  (recur (inc ip)))
          "jgz" (if (pos? (get-v op1))
                  (recur (+ ip (get-v op2)))
                  (recur (inc ip)))
          (do (exec-update regs [cmd op1 op2]) (recur (inc ip))))))))

(defn part-two []
  (async/go (run 0 1))
  (async/go (run 1 0))
  ; We just sleep cause we know that the threads finish quickly,
  ; so there is no need to detect the deadlock (next-level hack btw).
  (Thread/sleep 3000)
  (@proc-sends 1))
