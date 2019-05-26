(ns math-assist.equation-statistics-test
  (:require [clojure.test :refer :all])
  (:require [math-assist.equation-statistics :refer :all]))

(deftest equation-statistics
  (testing "can determine correctness of 10-20 equations"
    (is (= (correctness {:equations [{:equation "10*10"
                                      :correct  true}

                                     {:equation "13*13"
                                      :correct  true}

                                     {:equation "13*14"
                                      :correct  false}]
                         :range     10})
           [{:start       10
             :end         20
             :correctness 66.7M}])))

  (testing "can determine correctness of 10-15 equations"
    (is (= (correctness {:equations [{:equation "10*10"
                                      :correct  true}

                                     {:equation "13*13"
                                      :correct  true}

                                     {:equation "13*14"
                                      :correct  false}]
                         :range     5})
           [{:start       10
             :end         15
             :correctness 66.7M}])))

  (testing "correctness of both 10-20 and 20-30 equations"
    (is (= (correctness {:equations [{:equation "5*7"
                                      :correct  true}

                                     {:equation "10*12"
                                      :correct  true}

                                     {:equation "12*13"
                                      :correct  false}]
                         :range     10})

           [{:start       0
             :end         10
             :correctness 100M}
            {:start       10
             :end         20
             :correctness 50M}])))

  (testing "difficulty increase when many questions are correct"
    (is (= (difficulty-assessment {:equations [{:equation "5*7"
                                                :correct  true}

                                               {:equation "10*12"
                                                :correct  true}

                                               {:equation "12*13"
                                                :correct  true}]})

           :increase)))

  (testing "difficulty decrease when many questions are incorrect"
    (is (= (difficulty-assessment {:equations [{:equation "5*7"
                                                :correct  true}

                                               {:equation "10*12"
                                                :correct  false}

                                               {:equation "12*13"
                                                :correct  false}]})

           :decrease))))
