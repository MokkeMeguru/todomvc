(ns todomvc.events
  (:require
   [re-frame.core :as re-frame :refer [after path inject-cofx]]
   [todomvc.db :as db]
   [todomvc.domains :as domains]
   [reitit.frontend.easy :as rfe]
   [reitit.frontend.controllers :as rfc]
   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [clojure.spec.alpha :as s]))

;; effect handlers
;;
(re-frame/reg-fx
 ::navigate!
 (fn [route]
   (println "navigate! to  " route)
   (apply rfe/push-state route)))

;; routing
(re-frame/reg-event-fx
 ::navigate
 (fn [_cofx [_ & route]]
   {:navigate! route}))

(re-frame/reg-event-db
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (rfc/apply-controllers (:controllers old-match) new-match)]
     (when-not (= new-match old-match) (.scrollTo js/window 0 0))
     (assoc db :current-route (assoc new-match :controllers controllers)))))


;; interceptor


(defn check-and-throw
  "throws an exception if `db` doesn't the Spec `a-spec`"
  [a-spec db]
  (when-not (s/valid? a-spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec db)) db))))

(def check-spec-interceptor (after (partial check-and-throw ::domains/db)))

;; local store
(def ls-key "todos-reframe")
(defn todos->local-store
  "Puts todos into localStorage"
  [todos]
  (.setItem js/localStorage ls-key (str todos)))

;; local storage cofx
(re-frame/reg-cofx
 ::local-store-todos
 (fn [cofx _]
   (assoc cofx :local-store-todos
          (into (sorted-map)
                (some->> (.getItem js/localStorage ls-key)
                         (cljs.reader/read-string))))))

(def ->local-store (after todos->local-store))

(def todo-interceptors [check-spec-interceptor
                        (path :todos)
                        ->local-store])

;; helpters
(defn allocate-next-id
  "returns the next todo id"
  [todos]
  ((fnil inc 0) (last (keys todos))))


;; event handlers


(re-frame/reg-event-fx
 ::initialize-db
 [(inject-cofx ::local-store-todos)]
 (fn-traced
  [{:keys [db local-store-todos]} _]
  {:db (assoc db/default-db :todos local-store-todos)}))

(re-frame/reg-event-db
 ::set-showing
 [check-spec-interceptor]
 (fn [db [_ new-filter-key]]
   (assoc db :showing new-filter-key)))

(re-frame/reg-event-db
 ::add-todo
 todo-interceptors
 (fn [todos [_ text]]
   (println "todos" todos)
   (let [id (allocate-next-id todos)]
     (assoc todos id {:id id :title text :done false}))))

(re-frame/reg-event-db
 ::toggle-done
 todo-interceptors
 (fn [todos [_ id]]
   (update-in todos [id :done] not)))

(re-frame/reg-event-db
 ::save
 todo-interceptors
 (fn [todos [_ id title]]
   (assoc-in todos [id :title] title)))

(re-frame/reg-event-db
 ::delete-todo
 todo-interceptors
 (fn [todos [_ id]]
   (dissoc todos id)))

(re-frame/reg-event-db
 ::clear-completed
 todo-interceptors
 (fn [todos _]
   (let [done-ids (->> (vals todos)
                       (filter :done)
                       (map :id))]
     (reduce dissoc todos done-ids))))

(re-frame/reg-event-db
 ::complete-all-toggle
 todo-interceptors
 (fn [todos _]
   (let [new-done (not-every? :done (vals todos))]
     (reduce #(assoc-in %1 [%2 :done] new-done)
             todos
             (keys todos)))))
