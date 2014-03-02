# LoL Tool Kit

The LoL Toolkit is a small helper tool for League of Legends. It acts like a construction kit and is extendable with other tabs with different functionalities. A technical description about how to extend it can be found here:https://github.com/cf86/LoLToolKit/blob/master/TechnicalDescription.md

Per default it offers already a Castmanager to modifty the quick and self cast options of each spell and item without beeing ingame. It saves the settings for each champion and allows it to easily change these settings by just choosing a champion. Additionally it it offers an extra tab to add some note sheets for each champion. For example one sheet for jungle, one for another lane and so on. Each sheet can save its own skill order, used runepage, masterypage, which summoner spells are used and has additional space for some notes. The note editor offers some BB Codes to markup the text but also offers the possibility to use pure plain text without any markup.


## Some Features

* offers a fast update method using a prepacked zip file on GoogleDrive and a slow updater which parses the LoL website for all neccessary information.
* offers a CastManager which modifies instead of overwrites the LoL settings.
* offers a small champion note manager which allows to make some notes like skill order and some other notes using a a small editor.
* offers interfaces to add more tabs and more functionalities, for more information see the [technical readme](https://github.com/cf86/LoLToolKit/blob/master/TechnicalDescription.md).
* each internal used module is exchangable, so if Riot changes something later, just the corresponding module has to be changed.
* import and export function for all made settings


## Installation

An installation is not neccessary and no administrator rights are needed. Just a java runtime environment needs to be installed. 

**Windows**:
Just download the LoLToolKit.exe ([here](https://github.com/cf86/LoLToolKit/blob/master/LoLToolKit.exe?raw=true)) file and put in a folder of your choice (it is recommended that this folder is empty). It will work out of the box and nothing needs to be installed.

**Linux/Mac OS X**:
Just download the LoLToolKit.jar ([here](https://github.com/cf86/LoLToolKit/blob/master/LoLToolKit.jar?raw=true)) file and put in a folder of your choice (it is recommended that this folder is empty). You can start it out of the box using any java runtine environment.



## Castmanager

![Main Window](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/Window.png)

As probably recognizable by the name the cast manager manages if a spell should be normal, quick or selfcasted. Each champion has its own profile, so you just have to choose your current champ and click "choose" to override the LoL settings with the new settings. You can also decide if you want to have a different keybinding for items for each champs or use one global keybinding for items for all champs. 
The Castmanager does also support the new Interface introduced by Riot with before a few month for easy quick cast settings.

![Riot hotkeys](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/RiotHotkeys.png)

Additionally it does not simply override the `input.ini` and `game.cfg` file instead it parses the file and just overrides or adds the the neccessary parameters. All other parameters remain untouched and will be rewritten as they were.

You can also change the used prefix for normal-, quick- and selfcast in the settings menu. A description what each prefix means is available as a Tooltip if you mouse over the combobox.

![settings prefix](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/SettingsWindow.png)

## Note Tab

![Note tab](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/NoteWindow.png)

The note editor allows to create a few note sheets for each champion. Each note sheet could for example be for different lanes or whatever you like. Each sheet has its own skill order, own summoner spells, rune- and mastery page. Additionally it offers a a small BB Code editor to make some own notes.

![note editor](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/SheetWindow.png)


## Updater

![Updater](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/Updater.png)

All neccessay data like images and so on are stored locally. To get the newest data for example when a new champion gets released there are 2 ways to update it. The first way is the fast update method. This method uses a prepacked (right now from me) zip file from GoogleDrive and just writes it into the data directory. This way is much faster than the 2nd way but requires that I update the zip file in time. The application will tell you if a new version is available. If no new version is available but a new champion got released there is another way, the slow update. This way takes a little longer because it parses the LoL Website and uses its API to get all information. But the advantage is, that the information come directly from the website.

The updater will also check if a software update is available and will inform you.


## Usage

After starting the application the first time it will realise that no data can be found and asks you to update. Just click yes and choose a update method (fast update is recommended).

![first start](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/UpdateRequired.png)

After updating the application is nearly ready to use.
If you installed LoL at the default location it will be found instantly. If not go to Settings 

![lol settings](https://raw.github.com/cf86/LoLToolKit/master/Screenshots/SettingsPath.png)

and set the LoL path. When this is done everything should work fine now.


## Copyright

Copyright (c) 2014 Christian Feier. See licence.txt for details.
