[![License](http://img.shields.io/badge/license-LGPL-blue.svg?style=flat)](https://www.gnu.org/licenses/lgpl-3.0.en.html#content)
[![Boot](https://img.shields.io/badge/boot-2.0.0-ECC42F.svg?style=flat)](http://boot-clj.com/)
[![Clojars](https://img.shields.io/badge/clojars-0.1.1-blue.svg?style=flat)](https://clojars.org/zilti/boot-typed)
[![Gratipay](//img.shields.io/gratipay/zilti.svg?style=flat)](//gratipay.com/zilti)
[![Flattr this](//api.flattr.com/button/flattr-badge-small.png)](https://flattr.com/submit/auto?user_id=zilti&url=https%3A%2F%2Fbitbucket.org%2Fzilti%2Fboot-typed)

boot-typed
==========

`[zilti/boot-typed "0.1.1"]`

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

TODO
----

* Integration with the boot notification plugins
