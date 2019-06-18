(ns clojurescript-quiz.prod
  (:require
    [clojurescript-quiz.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
