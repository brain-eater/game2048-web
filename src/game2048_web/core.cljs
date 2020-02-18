(ns game2048-web.core
  (:require
    [reagent.core :as r]
    [game2048_web.logic :as game]))

(defn debug [x] (do (println x) x))

;; -------------------------
;; Views

(def initial-state (game/init-board 4))

(defonce board-state (r/atom initial-state))

(defn get-cell-representation [cell]
  (str (if (= 0 cell) "" cell)))

(defn create-cell [cell]
  [:div {:class (str "cell _" cell)} (get-cell-representation cell)])

(defn create-row [row]
  [:div {:class "row"}
   (map create-cell row)])

(defn reset-game []
  (reset! board-state initial-state))


(defn game-over-view [board]
  (if (game/game-over? board)
    [:div {:class "game-over"}
     [:div "Game over"]
     [:button {:class "play-again" :on-click reset-game} "Play again"]]
    [:div]))

(defn game []
  [:div [:h2 "Let's play 2048"]
   [:div {:class "container"}
    [:div {:class "board" :autoFocus 1 :tabIndex 1 :on-key-down
                  #(case (.-which %)
                     38 (swap! board-state (partial game/handle-move "w"))
                     40 (swap! board-state (partial game/handle-move "s"))
                     37 (swap! board-state (partial game/handle-move "a"))
                     39 (swap! board-state (partial game/handle-move "d"))
                     nil)}
     (map create-row (debug @board-state))]
    (game-over-view @board-state)]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [game] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
