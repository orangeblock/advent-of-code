(ns aoc2017.p3.sol)

(defn manhattan-distance
  [[x1 y1] [x2 y2]]
  (+ (Math/abs (- x1 x2)) (Math/abs (- y1 y2))))

; Bunch of confusing math that:
;  - finds the side length of the square our number is located in
;  - finds the quadrant of our number (which side? up, down, left or right)
;  - finds the location within that quadrant and...
;  - calculates the cartesian coordinates based on our spiral properties
;
; Pretty sure there's a pefect formula for this somewhere, but I'm lazy.
; Plus, this is still a constant time algorithm!
(defn spiral-position [n]
  (if (< n 2) 
    [0 0]
    (let
      [root     (int (Math/sqrt n))
       side-len (cond
                  (even? root) (inc root)
                  (= n (* root root)) root
                  :else (+ 2 root))
       half-len (int (/ side-len 2))
       sq-start (inc (* (- side-len 2) (- side-len 2)))
       quadrant (int (/ (inc (- n sq-start)) (dec side-len)))
       quad-pos (- (inc (- n sq-start)) (* quadrant (dec side-len)))] 
      
      (case (mod quadrant 4)
        0 [half-len (+ quad-pos (- half-len))]
        1 [(- half-len quad-pos) half-len]
        2 [(- half-len) (- half-len quad-pos)]
        3 [(+ quad-pos (- half-len)) (- half-len)]))))

(defn part-one []
  (manhattan-distance [0 0] (spiral-position 368078)))

; TODO: Brain fried
(defn part-two []
  nil)
