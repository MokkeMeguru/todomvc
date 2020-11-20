(ns todomvc.routes-test
  (:require [todomvc.routes :as sut]
            [cljs.test :as t :include-macros true]
            [clojure.spec.alpha :as s]
            [reitit.core :as r]))

(defn match-is [ideal-key]
  (fn [match]
    (= ideal-key
       (-> match
           :data
           :name))))

(defn path-param-is [key ideal-value]
  (fn [match]
    (= ideal-value
       (-> match
           :path-params
           key))))

(t/deftest routing-home
  (t/is (s/valid?
         (match-is ::sut/show-all)
         (r/match-by-path sut/router "/"))))

(t/deftest routing-filtered
  (t/is (s/valid?
         (match-is ::sut/filtered)
         (r/match-by-path sut/router "/done")))
  (t/is (s/valid?
         (path-param-is :filter "done")
         (r/match-by-path sut/router "/done"))))
