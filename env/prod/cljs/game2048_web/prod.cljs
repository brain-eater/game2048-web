(ns game2048-web.prod
  (:require
    [game2048-web.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
