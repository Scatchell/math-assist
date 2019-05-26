(ns math-assist.equation-statistics-test
  (:require [clojure.test :refer :all])
  (:require [math-assist.equation-statistics :refer :all]))

(deftest equation-statistics
  (testing "can determine correctness of 10-20 equations"
    (is (= (correctness [{:equation    "10*10"
                          :user-answer 100
                          :correct     true}

                         {:equation    "13*13"
                          :user-answer 169
                          :correct     true}

                         {:equation    "13*13"
                          :user-answer 2
                          :correct     false}])
           [{:start       10
             :end         20
             :correctness 66.7M}])))

  (testing "categorize numbers"
    (is (= (category-of-number 7) {:start 0 :end 10}))
    (is (= (category-of-number 12) {:start 10 :end 20})))

  (testing "correctness of both 10-20 and 20-30 equations"
    (is (= (correctness [{:equation    "5*7"
                          :user-answer 100
                          :correct     true}

                         {:equation    "10*12"
                          :user-answer 169
                          :correct     true}

                         {:equation    "12*13"
                          :user-answer 2
                          :correct     false}])

           [{:start       0
             :end         10
             :correctness 100M}
            {:start       10
             :end         20
             :correctness 50M}]))))
