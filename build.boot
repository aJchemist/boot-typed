;; -*- mode: Clojure; -*-
(set-env!
 :source-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.6.0" :scope "provided"]
                 [boot/core "2.0.0-rc1" :scope "provided"]
                 [org.clojure/core.typed "0.2.77" :scope "provided"]
                 [adzerk/bootlaces "0.1.8" :scope "test"]])

;; For bootlace: Set env-vars CLOJARS_USER and CLOJARS_PASS

(require '[adzerk.bootlaces :refer :all]
         '[zilti.boot-typed :refer [typed]])

(def +version+ "0.1.0-SNAPSHOT")

(bootlaces! +version+)

(task-options!
 pom {:project 'zilti/boot-typed
      :version +version+
      :description "Run a core.typed check in boot."
      :url "https://github.com/zilti/boot-typed"
      :scm {:url "https://github.com/zilti/boot-typed"}
      :license {:name "Eclipse Public License"
                :url "http://www.eclipse.org/legal/epl-v10.html"}})
