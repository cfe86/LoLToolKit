# LoL Tool Kit - Technical Description

A short non technical introduction can be found here: https://github.com/cf86/LoLToolKit
This document is more a technical introduction and explains how to modify this application.

The LoL Tool Kit is a small helper tool for League of Legends. It acts like a construction kit and is extendable with other tabs with different functionalities. A short instruction on how to extend it can be found afterwards.
Per default it offers already a Castmanager to modifty the quick and self cast options of each spell and item without beeing ingame. It saves the settings for each champion and allows it to easily change these settings by just choosing a champion. Additionally it it offers an extra tab to add some note sheets for each champion. For example one sheet for jungle, one for another lane and so on. Each sheet can save its own skill order, used runepage, masterypage, which summoner spells are used and has additional space for some notes. The note editor offers some BB Codes to markup the text but also offers the possibility to use pure plain text without any markup.


## Some Features

* Offers a fast update method using a prepacked zip file on GoogleDrive and a slow updater which parses the LoL website for all neccessary information.
* Each component is its own module e.g. the Datacollector, the config writer and so on. If one module is outdated it can easily be replaced by a newer version using the predefined interface and registering in the module manager.
* Due to [JMLS](https://github.com/cf86/JMLS) it is easy to add a new language or to modify an already existing language to the Toolkit. 
* It does not override the LoL specific files (input.ini and game.cfg) instead it it reads the whole file and just modifies the entries it needs to modify. All other entries will be left as they are and write back to the file.
* Architecture allows to extend the Toolkit with new tabs with more functionalities.

## Building and Requirements

In order to build a *.jar file the LoL Toolkit requires:

* [Maven](http://maven.apache.org/) - is used to resolve dependencies.
* [JDK 7+](http://www.oracle.com/technetwork/java/javase/downloads/index.html) - The Toolkit is developed with Oracle JDK 7 but should also work with OpenJDK. 
* [JMLS](https://github.com/cf86/JMLS) - is used to support more than one language.
* [JTattoo](http://www.jtattoo.net/) - is used to offer a few skins to choose from.
* [MigLayout](http://www.miglayout.com/) - is used as the layout manager for the GUI.

<br />
To build the LoL Tool Kit use the following command:

```shell
mvn clean package
```

The \*.jar file can be found in the **_target_** directory afterwards. 

A version for **windows** and **linux** is already compiled and can be found in the specific zip files.


## How to extend the Tool Kit

Here I will shortly explain how to extend the Tool Kit.


### Add a new language

The Tool Kit uses JMLS to support multi languages. Therefore you will just have to add a new language file and add the language to the `mls.conf`. For more information about the language file and `mls.conf` take a look [here](https://github.com/cf86/JMLS). 


### Change a module

The Toolkit has a modular architecture. Nearly every component can be extended or exchanged for another version. To allow this the modules are managed by different _managers_ which offer one instance for each task. The following _managers_ are available which use the following interfaces.
The `TabManager` has a special role and will be explained later.

**_CollectorManager_**: manages the data collectors to get all information about items, champions and so on.

* **IDataCollector** which is used to collect all neccassary data like champion information and so on. The fast (per default a GoogleDrive updater) and the slow (per default a website parser) upater implement this interface.


**ConfigurationManager**: manages the classes which manipulate the LoL files like `input.ini` and `game.cfg`.

* **IConfigCreator** which is used to to create the `input.ini` file which holds all information about the hotkeys. 
* **IRangeIndicatorChanger** which is used to create the `game.cfg` file which holds the information about the range indicator.

**_ConverterManager_**: converts the editor code to HTML code.

* **IBBtoHTMLConverter** which is used to convert the BB code for the note editor to HTML code to show in a `JEditorPane`. The default Converter supports bold, underline, italic, add item, numbered list, unnumbered list commands.

**_ParserManager_**: manages all parser and writer.

* **Parser** the interface all parser interfaces implement.
* **ChampionParser** parses all information about a champion like all hotkeys, spells and description and so on. The default parser is a XML parser.
* **ChampionNameParser** parses the name file to get the names of all champions. The default parser is a XML parser.
* **ChampionNoteParser** parses the notes for a specific champion. The default parser is a XML Parser.
* **ImportParser** parses the exported file and saves it into `ImportData` objects. The default parser is a XML parser.
* **ItemParser** parses all items from the item file. The default parser is a XML parser.
* **SummonerSpellParser** parses all summoner spells from the summoner spell file. The default parser is a XML parser.
* **Writer** offers methods to write the file each corresponding parser can parse.



To change one module just implement the corresponding interface which can be found in the `*.interfaces` package and exchange the old module with the new one in the _specific Manager_ which can be found in the `manager` package. 
Make sure to also modify the Writer to ensure that the created files can be parsed by your new module. To modify the writer just extend the current writer and change the method you have to change.

The paths to the files like the champion files, name files and so on are defined in the `Config` singleton.

### Add a new tab

To add a new tab you have to create a new `JPanel` first. To do so create a new class and extend it via the `view.interfaces.AbstractTab` class. A template for a new tab could look like this:
```java
public class ExampleTab extends AbstractTab {

	private MLS mls;

	public ExampleTab() {
		mls = new MLS("languagefiles/ExampleTab", Config.getInstance().getCurrentLanguage());
		mls.setToolTipDuration(-1);
	}

	@Override
	public void init() throws TabInitException {
		// init all GUI components
	}

	@Override
	public void changeLanguage(Locale lang) {
		this.mls.setLocale(lang);
		this.mls.translate();
	}

	public void setActionListener(ActionListener l) {
		// add the Actionlistener to the components
	}
}
```

After this you will need a controller to control the created tab. This _controller_ needs to extend `controller.AbstractController`. The `AbstractController` also has an attribute `mainWindow` (this is no instance from the main window, it is just an command interface) which has a method `setWindowEnabled(boolean en)` which can be called to enable (unfreeze) or disable (freeze) the main window. This may come handy if a sub window needs to be open and the main window should be frozen as long as this sub window is open. A template for a new _controller_ could look like this:
```java
public class ExampleController extends AbstractController {

	private ExampleTab window;
	
	public ExampleController() {
		// init model and so on
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// observer 
	}

	@Override
	public void init(AbstractTab tab) throws ControllerInitException {
		this.window = (ExampleTab) tab;
		this.window.setActionListener(this);
		
		// init more stuff
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("anActionCommand"))
			exampleButtonPressed();
	}
	
	private void exampleButtonPressed() {
		// this is called if exampleButton with Actioncommand "anActionCommand" is pressed.
	}
}
```

After this is done you will have to register your new tab in the `TabManager` found in the `manager` package. To register the tab just add a new register entry in the constructor. For example lets add the new _controller_ as the third tab:
```java
// Register Tabs here:
register("smartcastTab", new SmartcastPanel(), new SmartcastController(), new String[]{"champinfoTab", "Main", "exampleTab"});
register("champnoteTab", new ChampNotePanel(), new ChampionNoteController(), new String[]{});
register("exampleTab", new ExampleTab(), new ExampleController(), new String[]{"smartcastTab"});
```
The first String is the _identifier_ which will be used for this _controller_. This _identifier_ is also used in [JMLS](https://github.com/cf86/JMLS) in the `MainWindow` _languagefile_. The second parameter is an instance of the created tab and the third parameter is an instance of the corresponding _controller_. The fourth parameter contains the _identifiers_ of all other _controller_ this new _controller_ should be observed by. For example in the example above the `smartcastTab` will be observed by the `championnoteTab` and the `MainWindow` (short '_Main_'). Now lets say the new _controller_ wants to be informed when a new champion is picked in the `smartcastTab` then the new _controller_ would have to be added to the String Array of the `smartcastTab` like it is done in the example. In our example we also added the `smartcastTab` to as an observer to the `exampleTab`.

The `smartcastTab` will notify all of its observers when a new _champion_ will be chosen and will send the corresponding `Champion` object.
The `champnoteTab` will notify all of its observers if a new sheet is chosen and will send the corresponding`ChampionNote` object.

The name of the new tab is defined in the `MainWindow` _languagefile_ and has the given _identifier_. For more information on how to use the _languagefile_ take a look on the [JMLS](https://github.com/cf86/JMLS) site.

Per default you will just have to register your new Tab to the `TabManager`. The only exception is, if your tab needs some special informations for example when a window like the Settings or Updater window got closed. In those cases you would also have to add a few lines to the `MainController`.


## Configuration

Every configuration will be saved in `config.Config`. There the every config will be written too. If your tab needs to to save some configuration add it in the configuration map and add it to the writer too.
If this settings should be editable by the user, you have to extend the settings window.


## Updater

The updater is a small project in its own. For an update the `updater.jar` will be extracted from the ToolKit and started. Afterwards the ToolKit closes itself. The `updater.jar` makes a backup and downloads the newer version. After the download is complete the `updater.jar` starts the new version of the ToolKit and closes itself.
The sourcecode of the updater can be found in the `Updater-1.0.jar`.


## Copyright

Copyright (c) 2014 Christian Feier. See licence.txt for details. 
