(ns math-assist.datastore.equations-test
  (:require [clojure.test :refer :all])
  (:require [math-assist.datastore.equations :refer :all]
            [monger.core :as mg]
            [monger.collection :as mc]
            [math-assist.conf :refer [config]]
            [mount.core :as mount]))

(defn- clear-db []
  (let [conn (mg/connect)
        db (mg/get-db conn (:database config))
        coll "equations"]
    (mc/remove db coll)))

(defn start-mount-fixture [f]
  (mount/start)
  (f))

(defn clear-db-fixture [f]
  (f)
  (clear-db))

(use-fixtures :once start-mount-fixture)

(use-fixtures :each clear-db-fixture)

(deftest save-test
  (testing "save equations list to DB"
    (let [equations '({:equation    "1*5"
                       :answer      5
                       :user-answer "correct"}
                      {:equation    "2*5"
                       :answer      10
                       :user-answer "incorrect"})]

      (save equations)
      (is (= (count (get-all)) 2))
      (is (= (dissoc (first (get-all)) :_id) (first equations)))
      (is (= (dissoc (second (get-all)) :_id) (second equations))))))
