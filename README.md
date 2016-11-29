# Nbvcxz - Password strength estimator

Nbvcxz is java library (and standalone console program) which is heavily inspired by the work in [zxcvbn](https://github.com/dropbox/zxcvbn)

## Special Feature

* Internationalization support for all feedback, and console output.
* Dictionaries can be customized, and custom dictionaries can be added very easily.
* Default dictionaries have excluded single character words due to many false positives
* Additional PasswordMatchers and Matches can be implemented and configured to run without re-compiling.
* Easy to configure how this library works through the ConfigurationBuilder.
    * You can set minimum entropy scores, locale, year patterns, custom leet tables, custom adjacency graphs, custom dictionaries, and custom password matchers.

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/tostino/nbvcxz/issues).

## License

MIT License

* http://www.opensource.org/licenses/mit-license.php

## Requires Java

* Java 1.8+

## Application using this library

- [GoSimple](https://gosimple.me/)