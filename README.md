# Language learning App for Android devices
App still in development so bugs may occur<br>
The app is tested before each release by myself both on a physical and emulated device.

## Todo
- Allow updating of stored Known Words
- Option to see the target language word and answer in your native language
- Maybe add an option to have multiple answers for a word/sentence:  E.G. 안녕,[Goodbye, Hello]  making both valid answers

## Currently the App will allow you to:
- Add a new word
- Add multiple words from a file
- Learn the words to add
- Store any words you know if you get them correct
- Review the stored known words
- update existing words that are not located in the known words file

As a side note this does support sentences, but in its current state only supoorts a 1 - 1 answer system

## Requirements
- Requires Read permissions to create multiple words from a file
  - The file layout for this should be a .txt file in the format:<br>
    KnownLanguage,TargetLangauge<br>
    KnownLanguage,TargetLangauge<br>
    ... etc...  One pair per line
- Made to be compatible with android V9.0+ (pie)

## To check the android version of your device
1. Open the Settings App: On your Android device, locate and open the Settings app. This is usually represented by a gear icon
2. Go to "About Phone" or "System": Scroll down in the settings menu and tap About Phone (or System on some devices, then About Phone or About Device)
3. Look for "Android Version": In the About Phone section, find a section labeled Android Version. The version number will be listed below or next to it
