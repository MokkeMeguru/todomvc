(ns todomvc.css
  (:require [garden.def :refer [defstyles]]
            [garden.selectors :as s :refer [defpseudoelement]]
            [garden.stylesheet :refer [at-media]]
            [garden.units :as u]))

(defpseudoelement -moz-input-placeholder)
(defpseudoelement -moz-placeholder)
(defpseudoelement -input-placeholder)

(defstyles screen
  [:html :body {:margin 0
                :padding 0}]
  [:button {:margin 0
            :padding 0
            :border 0
            :background "none"
            :font-size (u/percent 100)
            :vertical-align "baseline"
            :font-family "inherit"
            :font-weight "inherit"
            :color "inherit"
            :-webkit-appearance "none"
            :appearance "none"
            :-webkit-font-smoothing "antialiased"
            :-moz-font-smoothing "antialiased"
            :font-smoothing "antialiased"}]
  [:body
   {:font "14pt/1.4em \"Helvetica Neue\", Helvetica, Arial, sans-serif"
    :background "#f5f5f5"
    :color "#4d4d4d"
    :min-width (u/px 230)
    :max-width (u/px 550)
    :margin "0 auto"
    :-webkit-font-smoothing "antialiased"
    :-moz-font-smoothing "antialiased"
    :font-smoothing "antialiased"
    :font-weight 300}]
  [:button (s/input (s/attr= :type "checkbox"))
   {:outline "none"}]
  [:.hidden {:display "none"}]
  [:#todoapp {:background "#fff"
              :margin "130px 0 40px 0"
              :position "relative"
              :box-shadow
              (str "0 2px 4px 0 rgba(0,0,0,0.2),"
                   "0 25px 50px 0 rgba(0, 0, 0, 0.1)")}]
  [:#todoapp
   [(s/input -input-placeholder)
    (s/input -input-placeholder)
    (s/input -moz-input-placeholder)
    {:font-style "italic"
     :font-weight 300
     :color "#e6e6e6"}]]
  [:#todoapp
   [:h1
    {:position "absolute"
     :top "-155px"
     :width (u/percent 100)
     :font-size (u/px 100)
     :font-weight 100
     :text-align "center"
     :color "rgba(175, 47, 47, 0.15)"
     :-webkit-text-rendering "optimizeLegibility"
     :-moz-text-rendering "optimizeLegibility"
     :text-rendering "optimizeLegibility"}]]
  [:#new-todo :.edit
   {:position "relative"
    :margin 0
    :width (u/percent 100)
    :font-size (u/px 24)
    :font-family "inherit"
    :font-weight "inherit"
    :line-height (u/em 1.4)
    :outline "none"
    :color "inherit"
    :padding (u/px 6)
    :border "1px solid #999"
    :box-shadow "inset 0 -1px 5px 0 rgba(0, 0, 0, 0.2)"
    :box-sizing "border-box"
    :-webkit-font-smoothing "antialiased"
    :-moz-font-smoothing "antialiased"
    :font-smoothing "antialiased"}]
  [:#new-todo
   {:padding "16px 16px 16px 60px"
    :border "none"
    :background "rgba(0, 0, 0, 0.003)"
    :box-shadow "inset 0 -2px 1px rgba(0, 0, 0, 0.03)"}]

  [:#main
   {:position "relative"
    :z-index 2
    :border-top "1px solid #e6e6e6"}]
  [(s/label (s/attr= :for "toggle-all"))
   {:display "none"}]
  [:#toggle-all
   {:position "absolute"
    :top (u/px -55)
    :left (u/px -12)
    :width (u/px 60)
    :height (u/px 34)
    :text-align "center"
    :border "none"}]
  [:#toggle-all
   [:&:before
    {:content "\"❯\""
     :font-size (u/px 22)
     :color "#e6e6e6"
     :padding "10px 27px 10px 27px"}]
   [:&:checked
    [:&:before
     {:color "#737373"}]]]
  [:#todo-list
   {:margin 0
    :padding 0
    :list-style  "none"}]
  [:#todo-list
   [:li
    {:position "relative"
     :font-size (u/px 24)
     :border-bottom "1px solid #ededed"}]
   [(s/li (s/last-child))
    {:border-bottom "none"}]
   [:li.editing
    {:border-bottom "none"
     :padding 0}]
   [:li.editing
    [:.edit
     {:display "block"
      :width  (u/px 506)
      :padding "13px 17px 12px 17px"
      :margin "0 0 0 43px"}]
    [:.view
     {:display "none"}]]
   [:li
    [:.toggle
     {:text-align "center"
      :width (u/px 40)
      :height "auto"
      :position "absolute"
      :top 0
      :bottom 0
      :margin "auto 0"
      :border "none"
      :-webkit-appearance "none"
      :appearance "none"}]
    [:.toggle
     {:background-image "url(\"/svg/toggle.svg\")"}

     [:&:checked
      {:background-image "url(\"/svg/toggle-checked.svg\")"}]]
    [:label
     {:white-space "pre-line"
      :word-break "break-all"
      :padding "15px 60px 15px 15px"
      :margin-left (u/px 45)
      :display "block"
      :line-height 1.2
      :transition "color 0.4s"}]]
   [:li.completed
    [:label
     {:color  "#d9d9d9"
      :text-decoration "line-through"}]]
   [:li
    [:.destroy
     {:display "none"
      :position "absolute"
      :top 0
      :right (u/px 10)
      :bottom 0
      :width (u/px 40)
      :height (u/px 40)
      :margin "auto 0"
      :font-size (u/px 30)
      :color "#cc9a9a"
      :margin-bottom (u/px 11)
      :transition "color 0.2s ease-out"}]
    [:.destroy
     [:&:hover
      {:color "#af5b5e"}]
     [:&:after
      {:content "\"×\""}]]]
   [:li
    [:&:hover
     [:.destroy
      {:display "block"}]]]
   [:li
    [:.edit
     {:display "none"}]]
   [:li.editing
    [:.view
     {:display "none"}]]
   [:li.editing
    [:&:last-child
     {:margin-bottom (u/px -1)}]]]
  [:#footer
   {:color "#777"
    :padding "10px 15px"
    :height (u/px 20)
    :text-align "center"
    :border-top "1px solid #e6e6e6"}
   [:&:before
    {:content "\"\""
     :position "absolute"
     :right 0
     :bottom 0
     :left 0
     :height (u/px 50)
     :overflow "hidden"
     :box-shadow
     (str
      "0 1px 1px rgba(0, 0, 0, 0.2),"
      "0 8px 0 -3px #f6f6f6,"
      "0 9px 1px -3px rgba(0, 0, 0, 0.2),"
      "0 16px 0 -6px #f6f6f6,"
      "0 17px 2px -6px rgba(0, 0, 0, 0.2)")}]]
  [:#todo-count
   {:float "left"
    :text-align "left"}]
  [:#todo-count
   [:strong
    {:font-weight 300}]]
  [:#filters
   {:margin 0
    :padding 0
    :list-style "none"
    :position "absolute"
    :right 0
    :left 0}
   [:li
    {:display "inline"}
    [:a
     {:color "inherit"
      :margin (u/px 3)
      :padding "3px 7px"
      :text-decoration "none"
      :border "1px solid transparent"
      :border-radius  (u/px 3)}]]]
  [:#filters
   [:li
    [:a.selected :a:hover
     {:border-color "rgba(175, 47, 47, 0.1)"}]
    [:a.selected
     {:border-color "rgba(175, 47, 47, 0.2)"}]]]
  [:#clear-completed :#clear-completed:active
   {:float "right"
    :position "relative"
    :line-height (u/px 20)
    :text-decoration "none"
    :cursor "pointer"}]
  [:#clear-completed:hover
   {:text-decoration "underline"}]
  [:#info
   {:margin "65px auto 0"
    :color "#bfbfbf"
    :font-size (u/px 10)
    :text-shadow "0 1px 0 rgba(255, 255, 255, 0.5)"
    :text-align "center"}]
  [:#info
   [:p
    {:line-height 1}]
   [:a
    {:color "inherit"
     :text-decoration "none"
     :font-weight 400}]
   [:a:hover
    {:text-decoration "underline"}]]
  (at-media
   {:screen true :-webkit-min-device-pixel-ratio 0}
   [:#toggle-all
    {:background "none"}]
   [:#todo-list
    [:li
     [:.toggle
      {:height (u/px 40)}]]]
   [:#toggle-all
    {:-webkit-transform "rotate(90deg)"
     :transform "rotate(90deg)"
     :-webkit-appearance "none"
     :appearance "none"}])
  (at-media
   {:max-width (u/px 430)}
   [:#footer
    {:height (u/px 50)}]
   [:#filters
    {:bottom (u/px 10)}]))
