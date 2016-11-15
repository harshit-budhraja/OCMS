# Minutes of the team meeting #1
### Date/Venue: 14th November, 2016 / 3B 3rd floor
### Attendees: Harshit, Akshay, Shobhit, Devesh, Shubham, Shaurya

## Discussions and Conclusions

The discussion opened with what we had done and applied till date, in the techologies we are supposed to use in this project. Firebase Database and Realtime monitoring, Firebase Cloud messaging have been successfully tested. The link to the sub-repository for the tested android app lies [here](https://github.com/harshitbudhraja/OCMS/tree/master/Testing%20Android%20and%20Firebase%20Frameworks/testfirebase "Test Firebase Android Project") and the sample apk to install [here](https://github.com/harshitbudhraja/OCMS/blob/master/Testing%20Android%20and%20Firebase%20Frameworks/testfirebase/sample-apk.apk "com.arachnisapps.testfirebase").

We decided the control flow of the app as described in the images attached below.
<img src="images/i1.jpg">
<img src="images/i2.jpg">
<img src="images/i3.jpg">
<img src="images/i4.jpg">
<img src="images/i5.jpg">

Also, a list of activities to be used and their names is made as follows:-<br>
1. **SplashActivity** - Will display a logo and a background image. All network operations to be done in an AsyncTask thread behind this.<br>
2. **NewsActivity** - HOME<br>
3. **GoogleActivity** - For g+ login<br>
4. **LoginActivity** - For executive login<br>
5. **SettingsActivity** - For general app-wide settings<br>
6. **ComplaintActivity** - For crime reporting form in public user domain<br>
7. **DepartmentInfo** - For displaying the department details(refer the project statement)<br>
8. **DeportComplaint** - For sending the reported crime to the executives<br>
9. **UpdateNews** - Executives updating the public statement i.e. the news feed of the app<br>
10. **CheckComplaint** - Lists the complaints received to the departments<br>
11. **CriminalRecords** - Lists the criminal Records<br>
<br>

Further, we need to create 4 different services in the app for these four technologies:-
1. Google Analytics<br>
2. Firebase Cloud Messaging<br>
3. Firebase realtime database<br>
4. Quick Response 2FA<br>
