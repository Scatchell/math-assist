# math-assist

Mental math practice tool that should allow users to advance their math skills and enable them to do math in their head easily and quickly through practice.

## Main features

- Generate random equations for user to practice with and check their answers `functional yet? Yes`
- Store user statistics that can be displayed to show progress and current score/success rate `functional yet? No`
- Increase/Decrease difficulty level over time based on how often they get questions correct, and the time it takes a user to present a correct answer successfully `functional yet? No`
- Allow user to request certain types of equations to learn from (i.e. multiplication, division, etc. etc.) `functional yet? No`
- Offer mental math tips/tricks at varying difficulty levels `functional yet? No`
- Offer a "math-off" mode where two people can compete with the same set of equations to see who finished them more quickly/accurately `functional yet? No`


## Usage

Under construction, not ready for public usage yet

### Local start

First, make sure the databse is started

`mongod` (using default directory and port)

Then, build public files by compiling clojurescript

`lein cljsbuld auto`

Then, start server

`lein run`

Local instance of app will be available at:

http://localhost:8080/

## License

Copyright © 2019 Anthony Scatchell

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
