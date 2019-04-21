(ns math-assist.core
  (:require [math-assist.random :as random]
            [ring.adapter.jetty :as jetty]))

(def types {"*" *})

(defn equation
  "I don't do a whole lot."
  [conf]
  (let [num-of-numbers (:numbers conf)
        action (types (:type conf))
        numbers (repeatedly num-of-numbers #(random/number (:max conf)))
        result (reduce action numbers)
        ]
    {:type    "*"
     :numbers numbers
     :answer  result})
  )

(defn- remove-last-char [string]
  (apply str (drop-last string)))

(defn eqn-to-string [conf eqn]
  (let [numbers (reverse (:numbers eqn))
        result (reduce (types (:type eqn)) numbers)
        eqn-string (reduce (fn [eqn-string num]
                             (str num (:type eqn) eqn-string))
                           ""
                           numbers)]
    (let [equation (remove-last-char eqn-string)]
      (if (:show-result conf)
        (str equation "=" result)
        equation)))
  )

(defn handler [request]
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    "Hello Clojure, Hello Ring!"})

(defn -main []
  (jetty/run-jetty handler {:port 3000}))

(defn repl-run []
  (let [eqns (repeatedly 10 #(equation {:type    "*"
                                        :numbers 2
                                        :max     20}))]

    (for [eqn eqns] (prn (eqn-to-string {} eqn)))
    )
  )
