(ns clojurescript-quiz.core
    (:require
      [reagent.core :as r :refer [atom]]
       [ajax.core :refer [GET]]))

(defn fetch-data! [data]
  (GET "https://opentdb.com/api.php?amount=10&category=26&type=multiple"
   {:handler #(reset! data %)
     :error-handler (fn [{:keys [status status-text]}]
                      (js/console.log status status-text))}))


;; -------------------------
;; Views

(defn home-page []
(let [data (atom nil)]
  (fetch-data! data)
  (fn []
  (let [{:strs [results]} @data]
  (prn (first results))
  (let [{:strs [category type difficulty question correct_answer incorrect_answers]} (first results)]
    ; (prn question))
 
    [:div 
    [:div [:h1 "Celebrity Quiz"]]
    [:div [:h3 question]
    [:p str correct_answer]
    ; [:p str incorrect_answers]
    ]]
  )
))))


;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
