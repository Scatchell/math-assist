(ns math-assist.datastore.equations-test
  (:require [clojure.test :refer :all])
  (:require [math-assist.datastore.equations :refer :all]
            [monger.core :as mg]
            [monger.collection :as mc]))

(defn- clear-db []
  (let [conn (mg/connect)
        db (mg/get-db conn "math-assist")
        coll "equations"]
    (mc/remove db coll)))

(deftest save-test
  (testing "save equations list to DB"
    (let [equations '({:equation    "1*5"
                       :answer      5
                       :user-answer "correct"}
                       {:equation    "2*5"
                        :answer      10
                        :user-answer "incorrect"})]

      (clear-db)

      (save equations)
      (is (= (count (get-all)) 2))
      (is (= (dissoc (first (get-all)) :_id) (first equations)))
      (is (= (dissoc (second (get-all)) :_id) (second equations))))))
