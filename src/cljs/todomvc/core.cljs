(ns todomvc.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [todomvc.events :as events]
   [todomvc.views :as views]
   [todomvc.config :as config]
   [todomvc.routes :as routes]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (routes/init-routes!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
