(ns zilti.boot-typed
  {:boot/export-tasks true}
  (require [boot.core :as core]
           [boot.pod :as pod]
           [clojure.java.io :as io]))

(def pod-deps '[])

(defn init [fresh-pod impl]
  (doto fresh-pod
    (pod/with-eval-in
      (case ~impl
        :clj (require 'clojure.core.typed)
        :cljs (require 'cljs.core.typed)))))

(defn check [impl worker-pods namespace nsym]
  (let [nsym (if (seq nsym) nsym namespace)]
    (assert (seq nsym))
    (pod/with-eval-in (worker-pods :refresh)
      (let [res (try (~(case impl
                         :clj `clojure.core.typed/check-ns
                         :cljs `cljs.core.typed/check-ns*)
                      '~nsym)
                     (catch Exception e
                       (println (.getMessage e))))]
        (prn res)))))

(defn coverage [impl worker-pods namespace nsym]
  (let [nsym (if (seq nsym) nsym namespace)]
    (assert (seq nsym))
    (pod/with-eval-in (worker-pods :refresh)
      (clojure.core.typed/var-coverage '~nsym))))

(core/deftask typed
  "Run a core.typed check in boot."
  [c check bool "Type check all Clojure namespaces declared in 'namespace'."
   n namespace NAMESPACE #{sym} "a list of Clojure namespaces to check."
   j check-cljs bool "Type check all ClojureScript namespaces declared in 'cljs-namespace'."
   s cljs-namespace CLJS-NAMESPACE #{sym} "a list of ClojureScript namespaces to check."
   v coverage bool "basic type coverage for all namespaces declared in 'namespace'."
   y nsym NSYM #{sym} "a list of nsyms to override the namespace/cljs-namespace lists in build.boot, and for coverage."]
  (let [worker-pods (pod/pod-pool (update-in (core/get-env) [:dependencies] into pod-deps) :init init)]
    (core/cleanup (worker-pods :shutdown))
    (core/with-pre-wrap fileset
      (when check
        (println "Running Clojure check...")
        (check :clj worker-pods namespace nsym))
      (when check-cljs
        (println "Running ClojureScript check...")
        (check :cljs worker-pods namespace nsym))
      (when coverage
        (println "Running type coverage...")
        (coverage :clj worker-pods namespace nsym))
      (core/commit! fileset))))
