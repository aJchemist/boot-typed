;; -*- mode: Clojure; -*-
(set-env!
 :resource-paths #{"src"}
 :dependencies '[[org.clojure/clojure "1.6.0" :scope "provided"]
                 [boot/core "2.0.0-rc1" :scope "provided"]
                 [org.clojure/core.typed "0.2.77" :scope "provided"]
                 [adzerk/bootlaces "0.1.9" :scope "test"]])

;; For bootlace: Set env-vars CLOJARS_USER and CLOJARS_PASS

(require '[adzerk.bootlaces :refer :all]
         '[zilti.boot-typed :refer [typed]])

(def +version+ "0.1.1")

(bootlaces! +version+)

(task-options!
 pom {:project 'zilti/boot-typed
      :version +version+
      :description "Run a core.typed check in boot."
      :url "https://bitbucket.org/zilti/boot-typed"
      :scm {:url "https://bitbucket.org/zilti/boot-typed"}
      :license {"name" "GNU Lesser General Public License"
                "url" "http://www.gnu.org/licenses/lgpl.txt"}})
