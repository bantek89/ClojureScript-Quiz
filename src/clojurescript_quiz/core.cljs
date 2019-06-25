(ns clojurescript-quiz.core
    (:require
      [reagent.core :as r :refer [atom]]
       [ajax.core :refer [GET]]
       [cljss.core :refer-macros [defstyles]]
       [cljss.core :refer [inject-global]]))


;; -------------------------
;; Styles

(inject-global
  {:body     {:background-color "lightblue"
              :text-align "center"}
   })

(defstyles component-answer []
  {:background-color "yellow"
  :width "40%"
  :border-radius "20px"
  :border "3px solid black"
  :margin "auto"
  :margin-bottom "30px"
  :padding "15px"})


(defn fetch-data! [data]
  (GET "https://opentdb.com/api.php?amount=50&category=9&type=multiple"
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
    (let [{:strs [category type difficulty question correct_answer incorrect_answers]} (first results)]
    (def answers (concat [correct_answer] incorrect_answers))
  
      [:div 
      [:div [:h1 "Let's Quiz!"]]
      [:div [:h3 question]
      (for [answers (shuffle answers)]   
      [:p {:key answers :class (component-answer) :on-click (fn [] (check-correct answers correct_answer) (fetch-data! data))} answers])]
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
