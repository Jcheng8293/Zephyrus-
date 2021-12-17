# Zephyrus-
Android App Project

November 5
Added a folder for card images

- Included a drawing for a possible design option to be used on the back of the tarot cards

- Three more variations are planned to be created for the backs of the cards

*All of the back designs are not set in stone and can be altered to better suit the overall design scheme of the app

November 4
Added a "Reverse" Button in CardListActivity that:

- Flips the images 180 degrees

- Click NORMAL cards will give NORMAL CardFacts

- Click REVERSE cards will give reverse CardFacts

- Flips image in CardFacts if cards are REVERSED

- Changes from NORMAL to REVERSE then clicked and vice versa

Properly spaced cards in CardListActivity

Changed string description to string[] description in order to store NORMAL and REVERSED descriptions

Big Note in TarotCard.java for some explanation of some code changes

*CalenderActivity seems to be broken even before this commit*

*IDK whats wrong with it, never thouched it*

Note:
     Added a copy of the method "readNewTarotCardByID" in TarotCards.java for CardListActivity for reversed cards 
     It is probably redundant 
     Maybe one of you guys can check it out 
     STUFF I CHANGED TO MAKE IT WORK 
     CardListActivity: Line 93. 
     TarotCard: Line 72 (Technically added) 
     CardFacts: Line 19. Line 39 
     WHY? 
     Adding 78 to cardID in CardListActivity (or anywhere) breaks it 
     Personally, I think adding a state variable to the method makes it easier 
     Instead of 0 = normal, +1 = reversed 
     