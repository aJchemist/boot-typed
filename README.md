boot-typed
==========



A boot task allowing you to check your project using `core.typed`.

Usage:

```
Options:
  -h, --help                           Print this help info.
  -c, --check                          Type check all Clojure namespaces declared in 'namespace'.
  -n, --namespace NAMESPACE            Conj NAMESPACE onto a list of Clojure namespaces to check.
  -j, --check-cljs                     Type check all ClojureScript namespaces declared in 'cljs-namespace'.
  -s, --cljs-namespace CLJS-NAMESPACE  Conj [CLJS NAMESPACE] onto a list of ClojureScript namespaces to check.
  -v, --coverage                       basic type coverage for all namespaces declared in 'namespace'.
  -y, --nsym NSYM                      Conj NSYM onto a list of nsyms to override the namespace/cljs-namespace lists in build.boot, and for coverage.
```

Details
-------

- The coverage check works only for Clojure files and thus uses the namespaces defined using --namespace.
- NSYM overrides both NAMESPACE and CLJS-NAMESPACE for check, check-cljs and coverage.
- You may chain checks together; `boot typed --check --check-cljs --coverage` runs just fine.
- And of course, `boot watch typed -c` and similar works as well.
