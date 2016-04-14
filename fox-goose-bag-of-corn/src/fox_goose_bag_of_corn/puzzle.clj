(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set :only [union] :as s]))

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])


(defn land-to-boat
  [[land boat finish] element]
  (let [elements (s/union #{element} #{:you})
        elements-to-move (filter elements land)
        new-land (filter (complement elements) land)
        new-boat (into boat elements-to-move)]
    [new-land new-boat finish]))

(defn boat-to-finish
  [[land boat finish]]
  (let [elements-to-move (filter (complement #{:boat}) boat)
        new-finish (into finish elements-to-move)]
    [land [:boat] new-finish]))

(defn land-to-finish
  [pos element]
  (let [to-boat (land-to-boat pos element)]
    [to-boat (boat-to-finish to-boat)]))

(defn finish-to-land
  [pos element]
  (map reverse (land-to-finish (reverse pos) element)))

(defn move
  [[land _ _ :as pos] element]
  ((if ((set land) element)
    land-to-finish
    finish-to-land)
  pos element))

(defn play
  [state element]
  (into state (move (last state) element)))

(defn river-crossing-plan []
  start-pos
(reduce play start-pos [:goose :you :corn :goose :fox :you :goose])

  ;; (reduce into [] 
  ;; [[[[:fox :goose :corn :you] [:boat] []]]
  ;;  (move [[:fox :goose :corn :you] [:boat] []] :goose)
  ;;  [[[:fox :corn ] [:boat :you] [:goose]]
  ;;  [[ :corn :fox :you ] [:boat] [:goose]]
  ;;  [[ :corn ] [:boat  :fox :you] [:goose]]
  ;;   [[ :corn ] [:boat] [:you :goose :fox]]]
  ;;  (move [[ :corn ] [:boat] [:you :goose :fox]] :goose)
  ;;  [[[ :goose] [:boat :you :corn] [:fox]]
  ;;  [[ :goose ] [:boat] [:fox :you :corn]]
  ;;  [[ :goose ] [:boat :you] [ :fox :corn]]
  ;;  [[ :goose :you] [:boat] [:fox :corn]]
  ;;  [[] [:boat :goose :you] [:fox :corn]]
  ;;  [[] [:boat] [:goose :you :fox :corn]]
  ;;  ]])
  )
