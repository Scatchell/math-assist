(ns math-assist.random)

(defn number [options]
  (let [{:keys [min max] :or {min 0 max 10}} options]
    (+ min (rand-int (- (+ 1 max) min)))))
