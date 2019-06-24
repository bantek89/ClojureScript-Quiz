(ns clojurescript-quiz.core
    (:require
      [reagent.core :as r :refer [atom]]
       [ajax.core :refer [GET]]))


; (defstyles component-style []
;   {:color "red"
;    :width "100%"})


(defn fetch-data! [data]
  (GET "https://opentdb.com/api.php?amount=10&category=26&type=multiple"
   {:handler #(reset! data %)
     :error-handler (fn [{:keys [status status-text]}]
                      (prn status status-text))}))

(def correct-count (atom 0))

(defn check-correct [answer correct_answer]
  (if (= correct_answer answer)  (js/alert "that's correct!" (swap! correct-count inc)) (js/alert "sorry that's incorrect")) 
)


;; -------------------------
;; Views

(defn home-page []
(let [data (atom nil)]
  (fetch-data! data)
  (fn []
  (let [{:strs [results]} @data]
  (prn (first results))
  (let [{:strs [category type difficulty question correct_answer incorrect_answers]} (first results)]

  (def answers (concat [correct_answer] incorrect_answers))
  (prn(shuffle answers))
    [:div 
    [:div [:h1 "Celebrity Quiz"]]
    [:div [:h3 question]
    (for [answers answers]   
    [:p {:key answers :on-click (fn [] (check-correct answers correct_answer) (fetch-data! data))} answers])]
    [:h4 "Your score is " @correct-count]
      ]
  )
))))


;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
