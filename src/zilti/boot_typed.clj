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

(defn Check [impl worker-pods namespaces nsyms]
  (let [nsyms (if (coll? nsyms) nsyms namespaces)]
    (assert (coll? nsyms))
    (pod/with-eval-in (worker-pods :refresh)
      (case ~impl
        :clj (require 'clojure.core.typed)
        :cljs (require 'cljs.core.typed))
      (let [res (try (~(case impl
                         :clj `clojure.core.typed/check-ns
                         :cljs `cljs.core.typed/check-ns*)
                      '~nsyms)
                     (catch Exception e
                       (println (.getMessage e))))]
        (prn res)))))

(defn Coverage [impl worker-pods namespaces nsyms]
  (let [nsyms (if (coll? nsyms) nsyms namespaces)]
    (assert (coll? nsyms))
    (pod/with-eval-in (worker-pods :refresh)
      (case ~impl
        :clj (require 'clojure.core.typed)
        :cljs (require 'cljs.core.typed))
      (clojure.core.typed/var-coverage '~nsyms))))

(core/deftask typed
  "Run a core.typed check in boot."
  [c check bool "Type check all Clojure namespaces declared in 'namespace'."
   n namespaces NAMESPACE #{sym} "a list of Clojure namespaces to check."
   j check-cljs bool "Type check all ClojureScript namespaces declared in 'cljs-namespace'."
   s cljs-namespaces NAMESPACE #{sym} "a list of ClojureScript namespaces to check."
   v coverage bool "basic type coverage for all namespaces declared in 'namespace'."
   y nsyms NSYM #{sym} "a list of nsyms to override the namespace/cljs-namespace lists in build.boot, and for coverage."]
  (let [worker-pods (pod/pod-pool (update-in (core/get-env) [:dependencies] into pod-deps))]
    (core/cleanup (worker-pods :shutdown))
    (core/with-pre-wrap fileset
      (when check
        (println "\n\nRunning Clojure check...\n")
        (Check :clj worker-pods namespaces nsyms))
      (when check-cljs
        (println "\n\nRunning ClojureScript check...\n")
        (Check :cljs worker-pods cljs-namespaces nsyms))
      (when coverage
        (println "\n\nRunning type coverage...\n")
        (Coverage :clj worker-pods namespaces nsyms))
      (println "\n\nDone with all tasks.\n")
      (core/commit! fileset))))
