# Nbvcxz - Password strength estimator - [![Build Status](https://travis-ci.org/GoSimpleLLC/nbvcxz.svg?branch=master)]
`nbvcxz` is java library (and standalone console program) which is heavily inspired by the work in [zxcvbn](https://github.com/dropbox/zxcvbn).

Password strength estimation is a bit of an art and science.  Strength estimation is accomplished by running 
a password through different algorithms looking for matches in any part of the password on: `word lists` (with fuzzy matching), 
`common dates`, `common years`, `spacial patterns`, `repeating characters`. `repeating sets of characters`,
and `alphabetic sequences`.
 
Each of these represent ways an attacker may try to crack a password.  To be vigilant, we must adapt to
new methods in password cracking and implement new methods to identify passwords susceptible to 
each new method.

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

## Differentiating Features
* Internationalization support for all feedback, and console output.
* Better match generation algorithm which will find the absolute lowest entropy combination of the matches.
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

## Maven Central
```xml
<dependency>
    <groupId>me.gosimple</groupId>
    <artifactId>nbvcxz</artifactId>
    <version>1.3.0</version>
</dependency>
```

## How to use
`nbvcxz` can be used as a stand-alone console program, or import it as a library.

### Standalone
To use as a stand-alone program, just compile, and run it by calling:
`java -jar nbvcxz-1.3.0.jar`
![alt text](http://i.imgur.com/9c070FX.png)

### Library
`nbvcxz` can also be used as a library for password validation in java back-ends.
Below is a full example of the pieces you'd need to implement within your own application.
##### Configure and create object

###### All defaults
```java
// With all defaults...
Nbvcxz nbvcxz = new Nbvcxz();
```

###### Custom configuration
Here we're creating a custom configuration with a custom exclusion dictionary and minimum entropy
```java
// Create a map of excluded words on a per-user basis using a hypothetical "User" object that contains this info
int i = 0;
HashMap<String, Integer> excludeMap = new HashMap();
excludeMap.put(user.getFirstName(), i++);
excludeMap.put(user.getLastName(), i++);
excludeMap.put(user.getEmail(), i++);
// And more...

// Create a dictionary list containing all the default dictionaries
List<Dictionary> dictionaryList = ConfigurationBuilder.getDefaultDictionaries();

// Add our new exclusion dictionary to the list
dictionaryList.add(new Dictionary("exclude", excludeMap, true));

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
    Feedback = result.getFeedback();
    
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

### Passphrase
```java
// Generate a passphrase from the standard (eff_large) dictionary with 5 words with a "-" between the words
String pass1 = Generator.generatePassphrase("-", 5);

// Generate a passphrase from a custom dictionary with 5 words with a "-" between the words
String pass2 = Generator.generatePassphrase(new Dictionary(...), "-", 5);
```

### Password
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

* Java 1.8+

## Application using this library

- [GoSimple](http://gosimple.me/)