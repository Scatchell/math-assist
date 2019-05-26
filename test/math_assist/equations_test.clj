(ns math-assist.equations-test
  (:require [clojure.test :refer :all]
            [math-assist.equations :refer :all]
            [math-assist.random :as random]))

(deftest equations
  (testing "generate equation"
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
           "4*5")))

  (testing "convert equation to object"
    (is (= (eqn-to-object {:type    "*"
                           :numbers [4 5]
                           :answer  20})
           {:equation "4*5"
            :answer   20})))

  (testing "convert equation data to config"
    (is (= (config-from-equation "10*12")
           {:type    "*"
            :numbers 2
            :max     12})))

  (testing "convert equation data to config with more than 2 numbers"
    (is (= (config-from-equation "10*12*14*16*19")
           {:type    "*"
            :numbers 5
            :max     19}))))
