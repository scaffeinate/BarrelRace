BarrelRace
==========
BarrelRace is a Simple Game for Android written in Java. The physics of the game, though very simple, are written from ground up without using any external libraries. The Game makes use of the Motion Sensors in the phone to control the game. The App's minSdkVersion is 14 and targetSdkVersion is 21.

**Game:**
The BarrelRace Game is a Rodeo event where there is a rider on a horse who enters a gate and circles the barrels (3 Barrels in this case). There is a clock which starts when the rider enters the gate and runs until the rider circles all the barrels. If the rider collides with the barrel the game ends. If the rider hits the fence(rectangular) then 5 seconds is added to the clock as penalty.

**Features:**
This game uses Motion sensors to control the horse(rider). The horse can be maneuvered left of right by tilting the device. The clock time is recorded when the game ends successfully where the user enters his/her name. The top 10 scores can be viewed under Highscores. The Game Settings include options to select different difficulty levels and also sound can be toggled on/off.

Screenshots:
------------

![home_screen](https://cloud.githubusercontent.com/assets/1825853/5447581/b2d8b618-8495-11e4-86c9-ff4199544a11.png)
![high_scores_screen](https://cloud.githubusercontent.com/assets/1825853/5447621/44da3ece-8496-11e4-8361-74194668df9f.png)
![settings_screen](https://cloud.githubusercontent.com/assets/1825853/5447623/44db4e54-8496-11e4-9fe2-0ca73b1b7666.png)

![success_screen](https://cloud.githubusercontent.com/assets/1825853/5447622/44dac722-8496-11e4-9610-0e404a5ac424.png)
![failure_screen](https://cloud.githubusercontent.com/assets/1825853/5447620/44d73b0c-8496-11e4-9d63-794d19ca15ea.png)


**Game Screen:**

![game_screen](https://cloud.githubusercontent.com/assets/1825853/5447771/a9f3d4f8-8498-11e4-9f0a-8a0ba45ea8c5.png)

Getting Started:
----------------
1. Clone or Fork this Repo (Or you can download the v1.0 [Source](https://github.com/sudharti/BarrelRace/archive/v1.0.zip))
2. Import Existing Android code into Eclipse
3. Connect your Device.(Since Emulators don't support Motion Sensors so well [http://stackoverflow.com/questions/10677128/sensors-in-android-emulator](http://stackoverflow.com/questions/10677128/sensors-in-android-emulator), it's better to run it on a Device)
4. Run the App

**APK:** If you want to directly run the apk on your phone without using Eclipse, you can find the signed apk [here](https://github.com/sudharti/BarrelRace/blob/master/release/BarrelRace%20v1.0.apk?raw=true)

Pull Requests:
-------------
1. Fork this repo
2. Make improvements
3. Send me a PR with your changes
4. Write down the changes you have done while creating the Pull Request

I will be happy to Review and Merge it!

Issues:
-------
If you have any issues with the working of it you can raise issues here [https://github.com/sudharti/BarrelRace/issues](https://github.com/sudharti/BarrelRace/issues)

License:
========
This project is licensed under the terms of [MIT License](http://opensource.org/licenses/MIT). You can view it here [LICENSE](https://github.com/sudharti/BarrelRace/blob/master/LICENSE)
