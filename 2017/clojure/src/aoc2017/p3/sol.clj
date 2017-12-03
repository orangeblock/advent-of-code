(ns aoc2017.p3.sol)

(defn manhattan-distance
  [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

;;
; Bunch of confusing math that:
;  - finds the side length of the square our number is located in
;  - finds the quadrant of our number (which side? up, down, left or right)
;  - finds the location within that quadrant and...
;  - calculates the cartesian coordinates based on our spiral properties
;
; Pretty sure there's a pefect formula for this somewhere, but I'm lazy.
; Plus, this is still a constant time algorithm!
;;
(defn- spiral-position [n]
  (if (< n 2) 
    [0 0]
    (let [int-root (int (Math/sqrt n))
          side-len (cond
                     (even? int-root) (inc int-root)
                     (= n (* int-root int-root)) int-root
                     :else (+ 2 int-root))
          half-len (int (/ side-len 2))
          sq-start (inc (* (- side-len 2) (- side-len 2)))
          quadrant (int (/ (inc (- n sq-start)) (dec side-len)))
          quad-pos (- (inc (- n sq-start)) (* quadrant (dec side-len)))]
      (case (mod quadrant 4)
        0 [half-len (+ quad-pos (- half-len))]
        1 [(- half-len quad-pos) half-len]
        2 [(- half-len) (- half-len quad-pos)]
        3 [(+ quad-pos (- half-len)) (- half-len)]))))

;;
; A much simpler alternative to the above, this generates the consecutive spiral points
; on the square with given side length. It can be chained further to get the same
; result as spiral-position, but it is much slower as it generates all intermediate
; points. This is only useful for part two of the challenge...
;;
(defn- spiral-points [side-len]
  (if (< side-len 2) 
    '([0 0])
    (let [start-pos [(int (/ side-len 2)) (int (- (dec (/ side-len 2))))]        
          hi        (nth start-pos 0)
          lo        (dec (nth start-pos 1))]
      (concat
        (map #(conj [hi] %) (range (inc lo) hi))
        (map #(vec (cons % [hi])) (range hi lo -1))
        (map #(conj [lo] %) (range hi lo -1))
        (map #(vec (cons % [lo])) (range lo (inc hi)))))))

(defn- point-to-key
  [[x y]]
  (keyword (str x y)))

(defn- cartesian-neighbors
  [[x y]]
  (lazy-seq [ [(inc x) y] [(dec x) y] [x (inc y)] 
              [x (dec y)] [(inc x) (inc y)] [(dec x) (dec y)]
              [(inc x) (dec y)] [(dec x) (inc y)] ]))

(defn- sum-of-neighbors
  [m point]
  (->> (cartesian-neighbors point)
       (map #((point-to-key %) m))
       (filter some?)
       (reduce +)))

(defn part-one []
  (manhattan-distance [0 0] (spiral-position 368078)))
  ; or, for extra cool points:
  ; (manhattan-distance [0 0] (nth (mapcat identity (map spiral-points (iterate #(+ % 2) 1))) (dec 368078))))

(defn part-two []
  (loop [tm     (transient {(point-to-key [0 0]) 1}) 
         total  1
         points (mapcat identity (map spiral-points (iterate #(+ % 2) 3)))]
    (let [sum  (sum-of-neighbors tm (first points))]
      (if (<= total 368078)
        (recur (assoc! tm (point-to-key (first points)) sum)
               sum
               (rest points))
        total))))
