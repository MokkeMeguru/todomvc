(ns todomvc.subs
  (:require
   [re-frame.core :as re-frame :refer [subscribe]]))

;; layer 2
(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::showing
 (fn [db _]
   (:showing db)))

(defn sorted-todos
  [db _]
  (:todos db))

(re-frame/reg-sub ::sorted-todos sorted-todos)

;; layer 3
(re-frame/reg-sub
 ::todos
 (fn [_ _]
   (subscribe [::sorted-todos]))
 (fn [sorted-todos _ _]
   (vals sorted-todos)))

(re-frame/reg-sub
 ::visible-todos
 :<- [::todos]
 :<- [::showing]
 (fn [[todos showing] _]
   (let [filter-fn (case showing
                     :active (complement :done)
                     :done :done
                     :all identity
                     identity)]
     (filter filter-fn todos))))

(re-frame/reg-sub
 ::all-complete?
 :<- [::todos]
 (fn [todos _]
   (count (filter :done todos))))

(re-frame/reg-sub
 ::completed-count
 :<- [::todos]
 (fn [todos _]
   (count (filter :done todos))))

(re-frame/reg-sub
 ::footer-counts
 :<- [::todos]
 :<- [::completed-count]
 (fn [[todos completed] _]
   [(- (count todos) completed) completed]))
