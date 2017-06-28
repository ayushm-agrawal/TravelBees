# TravelBees
Making Group Travel Experience Better for the People
============================================

<h3>How to run this project</h3>

1. Open Android Studio and launch the Android SDK manager from it (Tools | Android | SDK Manager)
1. Check that these two components are installed and updated to the latest version. Install or upgrade
   them if necessary.
   1. *Android SDK Platform Tools*
   2. *Android Support Library*
   2. *Google Play Services*
   3. *Google Repository*
1. Return to Android Studio and select *Import Project*
1. Select the **BasicSamples** directory
1. Select "Import from existing model - Gradle"

<h3>Modify IDs, compile and run</h3>

To set up a sample:

1. Change the application id in the build.gradle file to your own package name
   (the same one you registered in Developer Console!).  You will have to update
   the build.gradle file for each sample you want to run.  There is no need to
   edit the AndroidManifest.xml file.
1. Modify res/values/ids.xml and place your IDs there, as given by the
   Developer Console (create the leaderboards and achievements necessary for
   the sample, if any). In the Developer console, select a resource type
   (Achievements, Events, Leaderboards) and click "Get Resources".  Copy the
    contents from the console and replace the contents of res/values/ids.xml.
1. Compile and run.

