(ns math-assist.core-test
  (:require [clojure.test :refer :all]
            [math-assist.equations :refer :all]
            [math-assist.random :as random]))

(deftest a-test
  (testing ""
    (with-redefs [random/number (constantly 10)]
      (is (= (equation {:numbers 2
                        :type    "*"
                        :max     10})
             {:type    "*"
              :numbers [10 10]
              :answer  100}))))

  (testing "convert equation to string with result"
    (is (= (eqn-to-string {:show-result true}
                          {:type    "*"
                           :numbers [4 5]
                           :answer  20})
           "4*5=20")))

  (testing "convert equation to string without result"
    (is (= (eqn-to-string {:show-result false}
                          {:type    "*"
                           :numbers [4 5]
                           :answer  20})
           "4*5"))))
