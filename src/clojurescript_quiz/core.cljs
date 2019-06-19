(ns clojurescript-quiz.core
    (:require
      [reagent.core :as r]
       [ajax.core :refer [GET]]))

(defn handler [response]
  (prn (str response)))

(defn error-handler [{:keys [status status-text]}]
  (prn (str "error: " status " " status-text)))

(GET "https://opentdb.com/api.php?amount=10&category=26&type=multiple")

;; -------------------------
;; Views

(defn home-page []
  [:div [:h2 "Celebrity Quiz"]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
