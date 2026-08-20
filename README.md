# Nbvcxz - Password strength estimator - [![build](https://github.com/GoSimpleLLC/nbvcxz/actions/workflows/build.yml/badge.svg)](https://github.com/GoSimpleLLC/nbvcxz/actions/workflows/build.yml)
`nbvcxz` is java library (and standalone console program) which is heavily inspired by the work in [zxcvbn](https://github.com/dropbox/zxcvbn).

Password strength estimation is a bit of an art and science.  Strength estimation is accomplished by running 
a password through different algorithms looking for matches in any part of the password on: `word lists` (with fuzzy matching), 
`common dates`, `common years`, `spacial patterns`, `repeating characters`, `repeating sets of characters`,
and `alphabetic sequences`.
 
Each of these represent ways an attacker may try to crack a password.  To be vigilant, we must adapt to
new methods in password cracking and implement new methods to identify passwords susceptible to 
each new method.

# Table of Contents
  * [Maven Central](#maven-central)
  * [Compile](#compile)
  * [Differentiating Features](#differentiating-features)
  * [Compatibility](#compatibility)
  * [A Rant On Arbitrary Password Policies](#a-rant-on-arbitrary-password-policies)
  * [How to use](#how-to-use)
    + [Standalone](#standalone)
    + [Library](#library)
        * [Configure and create object](#configure-and-create-object)
          + [All defaults](#all-defaults)
          + [Localization](#localization)
          + [Custom configuration](#custom-configuration)
        * [Estimate password strength](#estimate-password-strength)
          + [Simple](#simple)
          + [Feedback](#feedback)
        * [Generate passphrase/password](#generate-passphrasepassword)
          + [Passphrase](#passphrase)
          + [Password](#password)
  * [Bugs and Feedback](#bugs-and-feedback)
  * [License](#license)
  * [Requires Java](#requires-java)
  * [A Note On Long Passwords](#a-note-on-long-passwords)
  * [Application using this library](#application-using-this-library)

## Maven Central
```xml
<dependency>
    <groupId>me.gosimple</groupId>
    <artifactId>nbvcxz</artifactId>
    <version>1.5.1</version>
</dependency>
```

## Compile

### Debian based
```sh
apt-get install git
apt-get install openjdk-8-jdk
apt-get install maven
git clone https://github.com/GoSimpleLLC/nbvcxz.git
cd nbvcxz
mvn package
```

The project will be built, and the jar file will be placed in the target sub-directory.

The build targets 1.7 bytecode, and JDK 20 dropped support for `-source 7`, so building currently
requires a JDK between 8 and 19.  The library itself still runs on 1.7 and later.

## Differentiating Features
* Internationalization support for all text output by the library (for feedback, console output, etc).
    * Currently supported languages
        - **English (default)**
        - Afrikaans (af)
        - Dutch (nl)
        - Finnish (fi)
        - French (fr)
        - German (de)
        - Hungarian (hu)
        - Italian (it)
        - Portuguese (pt)
        - Russian (ru)
        - Spanish (es)
        - Swedish (sv)
        - Telugu (te)
        - Ukrainian (uk)
        - Chinese (zh)
* Match combination is solved exactly.  The lowest entropy combination of matches is found in a single linear pass over the password, with no search and no heuristic fallback.
* Estimates are bounded by a configurable wall clock budget, and a timed out estimate fails safe.  Stopping early can only find fewer matches, which can only make the entropy come out too high, so a timed out `Result` reports `isTimedOut()`, `isMinimumEntropyMet()` of false, and a basic score of 0 regardless of the entropy it arrived at.  Set it with `ConfigurationBuilder setEstimateTimeout(Long)`, or to 0 to run unbounded.
* Support for ranked and un-ranked dictionaries.
* Dictionary matching has the ability to use Levenshtein Distance (LD) calculations to match passwords which are non-exact matches to a dictionary entry.
    * LD calculations happen on full passwords only, and have a `threshold` of 1/4th the length of the password.
* Dictionaries can be customized, and custom dictionaries can be added very easily.
    * Exclusion dictionaries can also be built and tailored per-user to prevent obvious issues like using their own email or name as their password
* Default dictionaries have excluded single character words due to many false positives
* Additional PasswordMatchers and Matches can be implemented and configured to run without re-compiling.
* Easy to configure how this library works through the ConfigurationBuilder.
    * You can set minimum entropy scores, locale, year patterns, custom leet tables, custom adjacency graphs, custom dictionaries, and custom password matchers.
* Support for generating passwords and passphrases.
    * Available in the console application as well as the library.
    * One use case is for generating a "forgot password" temporary pass

## Compatibility
Strict compatibility between nbvcxz and zxcvbn has not been a goal of this project. The additional features in nbvcxz 
which have improved accuracy are the main causes for differences with zxcvbn. There are some ways to configure nbvcxz for
better compatibility though, so we will go over those configuration parameters here.

1. Disable the Levenshtein Distance (LD) calculation. This feature was very helpful in my analysis on helping identify 
passwords which were only slightly different than dictionary words but were not caught with the original implementation. 
This feature will be sure to cause nbvcxz to produce different results than zxcvbn for a large number of passwords.
Use ConfigurationBuilder setDistanceCalc(Boolean distanceCalc)

2. Make sure both implementations are using the same dictionaries. There are additional leaked passwords in the 
nbvcxz dictionary compared to zxcvbn. There are also additional dictionaries included in nbvcxz that are not in zxcvbn and 
vice versa. Simply different choices on what lists were important to include by default. With nbvcxz you can easily 
change which dictionaries are used though, so it's easy to make the different implementations use the same dictionaries.
Use ConfigurationBuilder setDictionaries(List<Dictionary> dictionaries)

3. Disable separator match types. This is a new match type which zxcvbn has no equivalent. It helps with passphrase 
detection and accurately scoring them, but if we are going for compatibility we need to disable it.
Use ConfigurationBuilder setPasswordMatchers(List<PasswordMatcher> passwordMatchers)

4. The two libraries combine matches differently, which will produce different results for some passwords. 
Because every match contributes its entropy independently and the matches have to tile the password exactly, 
nbvcxz treats this as a shortest path over the positions in the password and solves it exactly.  zxcvbn scores 
a decomposition partly by how many matches it contains, so the two are not solving quite the same problem and 
will not always agree even when both find the best answer to their own.  There is no configuration parameter 
that makes this one line up.

## A Rant On Arbitrary Password Policies 
Lets think up an example scenario which I expect some of you may have run into way too often. 
We are a company `NewStartup!` and we are creating the next big web application. We 
want to ensure our users don't choose an easily guessable password, so we implement an arbitrary
policy which says a password must have:
  an __eight character minimum__ and contain __upper case__, __lower case__, __numbers__, and __special characters__
  
Now lets see how that policy applies to two passwords which are at opposite ends of the spectrum.

  Password #1: `Passw0rd!` - This password was chosen to get around an arbitrary policy 
  
  Password #2: `5fa83b7e1r39xfa8hmiz0` - This was randomly generated using lowercase alphanumeric 

  Password #1 meets all of the rules in the policy and passes with flying colors.
  Password #2 does not contain __upper case__, or __special characters__, and thus the policy fails this password.
  
Was password #1 actually more secure than password #2 by any metric?  That would be a hard argument to make.

In fact, password #1 is likely to be cracked quite quickly. `password` is one of the top passwords in all password 
lists an attacker is likely to try using a rule based dictionary attack.  If the attacker knows that our policy requires: 
__eight character minimum__, __upper case__, __lower case__, __numbers__, and __special characters__ 
they will then use rules like `toggle case`, `l33t substitution`, and `suffix/prefix special characters` 
to augment their dictionary list for the attack.

It's quite likely password #1 would fall to an attacker even in a rate limited online attack.

Password #2, while not allowed by our policy, is only susceptible to a brute force attack (if a secure hashing algorithm is used).

## How to use
`nbvcxz` can be used as a stand-alone console program, or import it as a library.

### Standalone
To use as a stand-alone program, just compile, and run it by calling:
`java -jar nbvcxz-1.5.1.jar`
![alt text](https://i.imgur.com/9c070FX.png)

### Library
`nbvcxz` can also be used as a library for password validation in java back-ends.
Below is a full example of the pieces you'd need to implement within your own application.
##### Configure and create object

###### All defaults
```java
// With all defaults...
Nbvcxz nbvcxz = new Nbvcxz();
```

###### Localization
Here we're creating a custom configuration which localizes all text to French
```java
// Create our configuration object and set the locale
Configuration configuration = new ConfigurationBuilder()
        .setLocale(Locale.forLanguageTag("fr"))
        .createConfiguration();
        
// Create our Nbvcxz object with the configuration we built
Nbvcxz nbvcxz = new Nbvcxz(configuration);
```

###### Custom configuration
Here we're creating a custom configuration with a custom exclusion dictionary and minimum entropy
```java
// Create a map of excluded words on a per-user basis using a hypothetical "User" object that contains this info
// getDefaultDictionaries() hands back the shared default list, so copy it before adding to it
List<Dictionary> dictionaryList = new ArrayList<>(ConfigurationBuilder.getDefaultDictionaries());
dictionaryList.add(new DictionaryBuilder()
        .setDictionaryName("exclude")
        .setExclusion(true)
        .addWord(user.getFirstName(), 0)
        .addWord(user.getLastName(), 0)
        .addWord(user.getEmail(), 0)
        .createDictionary());

// Create our configuration object and set our custom minimum
// entropy, and custom dictionary list
Configuration configuration = new ConfigurationBuilder()
        .setMinimumEntropy(40d)
        .setDictionaries(dictionaryList)
        .createConfiguration();
        
// Create our Nbvcxz object with the configuration we built
Nbvcxz nbvcxz = new Nbvcxz(configuration);
```

##### Estimate password strength

###### Simple
```java
// Estimate password 
Result result = nbvcxz.estimate(password);

return result.isMinimumEntropyMet();
```

###### Feedback
This part will need to be integrated into your specific front end, and really depends on your needs. 
Here are some of the possibilities:
```java

// Get formatted values for time to crack based on the values we 
// input in our configuration (we used default values in this example)
String timeToCrackOff = TimeEstimate.getTimeToCrackFormatted(result, "OFFLINE_BCRYPT_12");
String timeToCrackOn = TimeEstimate.getTimeToCrackFormatted(result, "ONLINE_THROTTLED");

// Check if the password met the minimum set within the configuration
if(result.isMinimumEntropyMet())
{
    // Start building success message
    StringBuilder successMessage = new StringBuilder();
    successMessage.append("Password has met the minimum strength requirements.");
    successMessage.append("<br>Time to crack - online: ").append(timeToCrackOn);
    successMessage.append("<br>Time to crack - offline: ").append(timeToCrackOff);    
    
    // Example "success message" that would be displayed to the user
    // This is obviously just a contrived example and would have to
    // be tailored to each front-end
    setSuccessMessage(successMessage.toString());
    return true;
}
else
{
    // Get the feedback for the result
    // This contains hints for the user on how to improve their password
    // It is localized based on locale set in configuration
    Feedback feedback = result.getFeedback();
    
    // Start building error message
    StringBuilder errorMessage = new StringBuilder();
    errorMessage.append("Password does not meet the minimum strength requirements.");
    errorMessage.append("<br>Time to crack - online: ").append(timeToCrackOn);
    errorMessage.append("<br>Time to crack - offline: ").append(timeToCrackOff);
    
    if(feedback != null)
    {
        if (feedback.getWarning() != null)
            errorMessage.append("<br>Warning: ").append(feedback.getWarning());
        for (String suggestion : feedback.getSuggestion())
        {
            errorMessage.append("<br>Suggestion: ").append(suggestion);
        }
    }
    // Example "error message" that would be displayed to the user
    // This is obviously just a contrived example and would have to
    // be tailored to each front-end
    setErrorMessage(errorMessage.toString());
    return false;
}
```

##### Generate passphrase/password
We have a passphrase/password generator as part of `nbvcxz` which very easy to use.

###### Passphrase
```java
// Generate a passphrase from the standard (eff_large) dictionary with 5 words with a "-" between the words
String pass1 = Generator.generatePassphrase("-", 5);

// Generate a passphrase from a custom dictionary with 5 words with a "-" between the words
String pass2 = Generator.generatePassphrase(new Dictionary(...), "-", 5);
```

###### Password
```java
// Generate a random password with alphanumeric characters that is 15 characters long
String pass = Generator.generateRandomPassword(Generator.CharacterTypes.ALPHANUMERIC, 15);
```

## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/GoSimpleLLC/nbvcxz/issues).

## License

MIT License

* http://www.opensource.org/licenses/mit-license.php

## Requires Java

* Java 1.7+ to run the library
* JDK 8 through 19 to build it from source

## A Note On Long Passwords

Match finding grows faster than linearly with password length, so `maxLength` is what bounds the cost of an
estimate and it is worth setting deliberately.  At the default of 256 the worst input we could construct
takes around 100ms.  Raising it is expensive: 1024 characters takes roughly 2 seconds unbounded, and 4096
takes over a minute.

`setEstimateTimeout(Long)` caps that regardless, defaulting to 500ms, but understand what it buys.  A timed
out estimate rejects the password rather than guessing at it, so on a heavily loaded server a budget which
is too tight turns into good passwords being refused.  Set `maxLength` to the length you actually accept,
and treat the timeout as a backstop rather than the primary control.

## Application using this library

- [Blacksmith TPM](https://blacksmithapplications.com/) - Formerly GoSimple TPM
- [Pazzword - Intelligent Password Evaluator](https://github.com/cyb3rko/pazzword)
- [KeePassDX - Open source password manager for Android](https://www.keepassdx.com/)

Anyone else using the library in their application, i'd love to hear and put a link up here.
