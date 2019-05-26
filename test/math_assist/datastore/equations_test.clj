(ns math-assist.datastore.equations-test
  (:require [clojure.test :refer :all])
  (:require [math-assist.datastore.equations :refer :all]
            [monger.core :as mg]
            [monger.collection :as mc]
            [math-assist.database :refer [db]]
            [mount.core :as mount]))

(defn- clear-db []
  (let [coll "equations"]
    (mc/remove db coll)))

(defn start-mount-fixture [f]
  (mount/start)
  (f))

(defn clear-db-fixture [f]
  (f)
  (clear-db))

(use-fixtures :once start-mount-fixture)

(use-fixtures :each clear-db-fixture)

(deftest save-equations
  (testing "save equations list to DB"
    (let [equations [{:equation    "1*5"
                      :answer      5
                      :user-answer 5
                      :correct     true}
                     {:equation    "2*5"
                      :answer      10
                      :user-answer 11
                      :correct     false}]]

      (save equations)
      (let [result (get-all)]
        (is (= (count result) 2))
        (is (= (dissoc (first result) :_id) (first equations)))
        (is (= (dissoc (second result) :_id) (second equations)))))))


(deftest get-equations-by-user
  (testing "get equations by user"
    (let [equations [{:equation    "1*5"
                      :answer      5
                      :user-answer 5
                      :correct     true
                      :user-id     "123"}
                     {:equation    "2*5"
                      :answer      10
                      :user-answer 11
                      :correct     false
                      :user-id     "987"}]]

      (save equations)

      (let [result (get-by-user "123")]
        (is (= (count result) 1))
        (is (= (dissoc (first result) :_id) (first equations)))))))


