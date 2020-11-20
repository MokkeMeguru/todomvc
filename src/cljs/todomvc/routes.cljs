(ns todomvc.routes
  (:require
   [reitit.coercion :as coercion]
   [reitit.coercion.spec]
   [clojure.spec.alpha :as  s]
   [re-frame.core :as rf :refer [dispatch dispatch-sync]]
   [todomvc.views :as views]
   [reitit.core :as r]
   [reitit.frontend.easy :as rfe]
   [re-frame.core :as re-frame]
   [todomvc.events :as events]))

(def routes
  ["/"
   [""
    {:name ::show-all
     :view views/main-panel
     :controllers
     [{:start (fn []
                (println "at home")
                (dispatch [::events/set-showing :all]))}]}]
   [":filter"
    {:name ::filtered
     :coercion reitit.coercion.spec/coercion
     :parameters {:path {:name string?}}
     :controllers
     [{:parameters {:path [:filter]}
       :start (fn [{:keys [path]}]
                (println "at filtered todo list views")
                (dispatch [::events/set-showing (keyword (:filter path))]))}]}]])

(def router (r/router routes))

(defn on-navigate
  [new-match]
  (when new-match
    (re-frame/dispatch [::events/navigated new-match])))

(defn init-routes!
  []
  (rfe/start!
   router
   on-navigate
   {:use-fragment true}))
