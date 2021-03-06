(ns math-assist
  (:use [jayq.core :only [$ css html document-ready append]])
  (:require [crate.core :as crate]
            [jayq.util :as util]
            [ajax.core :refer [GET POST]]))

;todo limit total number of questions (eventually restfully?) to get final results/score

(def equations (atom '()))
(def finished-equations (atom '()))

(defn- render-question []
  (let [current-equation (peek @equations)]
    (html ($ "#equations-list")
          (crate/html
            [:li (get current-equation "equation")]))))

(defn- eqn-handler [response]
  (swap! equations #(into '() (concat % response)))
  (render-question))

(defn- get-equations []
  (GET "/equations" {:handler         eqn-handler
                     :response-format :json
                     :params {:user-id "12345"}}))

(defn- check-answer [input]
  (let [current-equation (peek @equations)
        answer (get current-equation "answer")]

    (let [user-answer (js/parseInt input)
          correct (= user-answer answer)]
      (html ($ "#correctness")
            (crate/html
              (if correct
                [:p {:class "correct"} "Correct!"]
                [:p {:class "incorrect"}
                 (str "Incorrect :(. Correct answer is: " answer " --- you entered: " input)])))

      (swap! finished-equations
             ;todo replace answer with user answer
             (let [equation-with-correctness
                   (dissoc (assoc current-equation :correct correct :user-answer user-answer) "answer")]
               #(conj % equation-with-correctness)))

      (html ($ "#last-question")
            (crate/html
              [:p {:class (if correct "correct" "incorrect")}
               (str (get current-equation "equation") "=" (get current-equation "answer"))])))))

(defn- save-equations []
  (POST "/answers"
        {:params        {:equations @finished-equations
                         ;todo make user id specific for each user
                         :user-id   "12345"}
         :handler       println
         :error-handler println
         :format        :json})
  (compare-and-set! finished-equations @finished-equations '())
  )

(defn- next-question []
  (swap! equations pop)
  (if (empty? @equations)
    (do (save-equations)
        (get-equations))
    (render-question)))

(defn- main []
  (html ($ "#main")
        (crate/html
          [:div
           [:div {:id "equations"}
            [:p "equations:"]
            [:div {:id "last-question"}]
            [:div {:id "correctness"}]
            [:ul {:id "equations-list"}]]
           [:input {:style "display: block; border: 2px solid #66cc33;"
                    :id    "equation-input"
                    :type  "text"}]]))

  (.keydown ($ "#equation-input")
            (fn [event]
              (let [k (.-keyCode event)]
                (when (= k 13)
                  (check-answer (.val ($ "#equation-input")))
                  (next-question)
                  (-> ($ "#equation-input")
                      (.val "")
                      (.focus))))))

  (.focus ($ "#equation-input")))

(document-ready
  (fn []
    (get-equations)
    (main)))

