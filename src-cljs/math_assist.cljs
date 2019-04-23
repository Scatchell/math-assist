(ns math-assist
  (:use [jayq.core :only [$ css html document-ready append]])
  (:require [crate.core :as crate]
            [jayq.util :as util]
            [ajax.core :refer [GET POST]]))

(def equations (atom nil))

(defn- render-next-question []
  (let [current-equation (peek @equations)]
    (html ($ "#equations-list")
          (crate/html
            [:li (get current-equation "equation")]))))

(defn- check-answer [input]
  (let [current-equation (peek @equations)
        answer (get current-equation "answer")]

    (if (= (js/parseInt input) answer)
      (html ($ "#correctness")
            (crate/html
              [:p {:class "correct"} "Correct!"]))

      (html ($ "#correctness")
            (crate/html
              [:p {:class "incorrect"}
               (str "Incorrect :(. Correct answer is: " answer " --- you entered: " input)]))
      )

    (html ($ "#last-question")
          (crate/html
            [:p {:class "&&&"}
             (str (get current-equation "equation") "=" (get current-equation "answer"))]))

    (prn "checking answer for " (get current-equation "equation"))
    (prn "answer" (get current-equation "answer"))
    (prn "input" input))

  (swap! equations pop)
  (render-next-question)
  )


(defn- main []
  (util/log "main")

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
                  (-> ($ "#equation-input")
                      (.val "")
                      (.focus))))))

  (.focus ($ "#equation-input")))

(defn- eqn-handler [response]
  (compare-and-set! equations nil response)
  (render-next-question))

(document-ready
  (fn []
    (GET "/equations" {:handler         eqn-handler
                       :response-format :json})
    (util/log "ready...")
    (main)))

