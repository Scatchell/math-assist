(ns math-assist.datastore.equations
  (:require [monger.core :as mg]
            [monger.collection :as mc]))

(defn save [equations]
  (let [conn (mg/connect)
        db (mg/get-db conn "math-assist")
        coll "equations"]
    (doseq [eqn equations] (mc/insert db coll eqn))))

(defn get-all []
  (let [conn (mg/connect)
        db (mg/get-db conn "math-assist")
        coll "equations"]

    (mc/find-maps db coll)))
