(ns math-assist.datastore.users-test
  (:require [clojure.test :refer :all])
  (:require [math-assist.datastore.users :refer :all]
            [monger.collection :as mc]
            [math-assist.database :refer [db]]
            [mount.core :as mount]))

(defn- clear-db []
  (let [coll "users"]
    (mc/remove db coll)))

(defn start-mount-fixture [f]
  (mount/start)
  (f))

(defn clear-db-fixture [f]
  (f)
  (clear-db))

(use-fixtures :once start-mount-fixture)

(use-fixtures :each clear-db-fixture)

(deftest save-new-user
  (testing "save user to DB"
    (let [user {:user-id    "123"
                :max-number 5}]
      (save-user user)
      (let [result (get-user "123")]
        (is (= (dissoc result :_id) user))))))

(deftest update-existing-user
  (testing "update user"
    (let [user {:user-id    "123"
                :max-number 5}
          updated-user {:user-id    "123"
                        :max-number 10}]

      (save-user user)
      (save-user updated-user)
      (let [result (get-user "123")]
        (is (= (dissoc result :_id) updated-user))))))

(deftest retrieve-user
  (testing "when user doesn't exist, should return nil"
    (let [user {:user-id    "123"
                :max-number 5}]

      (save-user user)
      (let [result (get-user "555")]
        (is (= result nil))))))


#_(deftest get-equations-by-user
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


