(ns todomvc.views
  (:require
   [re-frame.core :as re-frame :refer [subscribe dispatch]]
   [todomvc.subs :as subs]
   [clojure.string :as string]
   [reagent.core :as r]
   [todomvc.events :as events]))

(defn todo-input [{:keys [title on-save on-stop]}]
  (let [val (r/atom title)
        stop #(do (reset! val "")
                  (when on-stop (on-stop)))
        save #(let [v (-> @val str string/trim)]
                (on-save v)
                (stop))]
    (fn [props]
      [:input (merge (dissoc props :on-save :on-stop :title)
                     {:type "text"
                      :value @val
                      :auto-focus true
                      :on-blur save
                      :on-change #(reset! val (-> % .-target .-value))
                      :on-key-down #(case (.-which %)
                                      13 (save)
                                      27 (stop)
                                      nil)})])))
(defn todo-item
  []
  (let [editing (r/atom false)]
    (fn [{:keys [id done title]}]

      [:li {:class (str (when done "completed ")
                        (when @editing "editing"))}
       [:div.view
        [:input.toggle
         {:type "checkbox"
          :checked done
          :on-change #(dispatch [::events/toggle-done id])}]
        [:label
         {:on-double-click #(reset! editing true)}
         title]
        [:button.destroy
         {:on-click #(dispatch [::events/delete-todo id])}]]
       (when @editing
         [todo-input
          {:class "edit"
           :title title
           :on-save #(if (seq %)
                       (dispatch [::events/save id %])
                       (dispatch [::events/delete-todo id]))
           :on-stop #(reset! editing false)}])])))

(defn task-list
  []
  (let [visible-todos @(subscribe [::subs/visible-todos])
        all-complete? @(subscribe [::subs/all-complete?])]
    [:section#main
     [:input#toggle-all
      {:type "checkbox"
       :checked all-complete?
       :on-change #(dispatch [::events/complete-all-toggle])}]
     [:label
      {:for "toggle-all"}
      "Mark all as complete"]
     (when (seq? visible-todos)
       [:ul#todo-list
        (for [todo visible-todos]
          ^{:key (:id todo)}
          [todo-item todo])])]))

(defn footer-controls
  []
  (let [[active done] @(subscribe [::subs/footer-counts])
        showing @(subscribe [::subs/showing])
        a-fn (fn [filter-key text]
               [:a {:class (when (= filter-key showing) "selected")
                    :href (str "#/" (name filter-key))}
                text])]
    [:footer#footer
     [:span#todo-count
      [:strong active] " " (case active 1 "item" "items") " left"]
     [:ul#filters
      [:li (a-fn :all "All")]
      [:li (a-fn  :active "Active")]
      [:li (a-fn :done "Completed")]]
     (when (pos? done)
       [:button#clear-completed {:on-click #(dispatch [::events/clear-completed])}
        "Clear completed"])]))

(defn task-entry
  []
  [:header#header
   [:h1 "todos"]
   [todo-input
    {:id "new-todo"
     :placeholder "what needs to be done?"
     :on-save #(when (seq %)
                 (dispatch [::events/add-todo %]))}]])

(defn todo-app
  []
  [:<>
   [:section#todoapp
    [task-entry]
    (when (seq @(subscribe [::subs/todos]))
      [task-list])
    [footer-controls]]
   [:footer#info
    [:p "Double click to edit a todo"]]])

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 "Hello from " @name]
     [todo-app]]))
