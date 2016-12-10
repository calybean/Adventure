// Joseph Cannon
// CS 3250
// 12/9/16
// I declare that the following source code was written solely by me, or provided on
// the course web site for this program. I understand that copying any source code,
// in whole or in part, constitutes cheating, and that I will receive a zero grade
// on this project if I am found in violation of this policy.

README:

A few notes about my program:

The project guidelines said that if you used keybindings instead of keylisteners you wouldn't have to deal with focus issues.
However, if your focus is on a text input box, the arrow keys are already mapped to something else, and unless you override the
keys for every "widget" in the app, it won't always have the focus needed. Hence the button at the top for using the arrow keys.

The project guidelines were also a little bit vague on how persistent the game save needed to be. I implemented it to be able to
save and load until you quit. After you quit and open a new instance of the game, it will not be able to load a game from a
previous instance.